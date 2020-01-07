package com.example.android.quakereport;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

public class Prediction_Activity extends FragmentActivity implements OnMapReadyCallback {

    List<Pair<Integer,Integer>> Final = new ArrayList<>();
    double lat,lon;
    private GoogleMap mMap;
    private ArrayList<Earthquake> Quakes;
    private ArrayList<Earthquake> ToShowQuakes;
    private HashMap<Pair<Integer,Integer>,Integer> Sorted;

    public static HashMap<Pair<Integer,Integer>, Integer> sortByValue(HashMap<Pair<Integer,Integer>, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Pair<Integer,Integer>, Integer> > list =
                new LinkedList<Map.Entry<Pair<Integer,Integer>, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Pair<Integer,Integer>, Integer> >() {
            public int compare(Map.Entry<Pair<Integer,Integer>, Integer> o1,
                               Map.Entry<Pair<Integer,Integer>, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Pair<Integer,Integer>, Integer> temp = new LinkedHashMap<Pair<Integer,Integer>, Integer>();
        for (Map.Entry<Pair<Integer,Integer>, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_activity);
        Quakes = EarthquakeActivity.FinalQuakes;
        ToShowQuakes = new ArrayList<Earthquake>();
        int coun = 0;

        HashMap<Pair<Integer,Integer>,Integer> hashMap = new HashMap<Pair<Integer,Integer>,Integer>();
        try {
            for (int i = 0; i < Quakes.size(); i++) {
                Integer lon = Integer.valueOf((int) (Quakes.get(i).getLongitude() + 180) / 2);
                Integer lat = Integer.valueOf((int) (Quakes.get(i).getLatitude() + 90) / 2);
                try {
                    coun = (hashMap.get(new Pair<Integer, Integer>(lat, lon))).intValue();
                    coun += 1;
                    hashMap.put(new Pair<Integer, Integer>(lat, lon), new Integer(coun));
                } catch (NullPointerException e) {
                    hashMap.put(new Pair<Integer, Integer>(lat, lon), new Integer(1));
                }


            }
        }catch (Exception e){
            Toast.makeText(Prediction_Activity.this,"Data not loaded",Toast.LENGTH_SHORT).show();
        }

        Sorted = Prediction_Activity.sortByValue(hashMap);
        List<Pair<Integer,Integer>> Keysrt = new ArrayList<Pair<Integer, Integer>>(Sorted.keySet());
        List<Integer> Valuesrt = new ArrayList<>(Sorted.values());
        for(int i = 0 ; i < Keysrt.size();i++){
            Log.i("Sort",Keysrt.get(i)+ " ----> " +Valuesrt.get(i) );
        }
        for(int i = 0 ; i < Min(30,Keysrt.size());i++){
            Final.add(Keysrt.get(i));
        }
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),EarthquakeActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    private int Min(int a,int b){
        return a<b?a:b;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for(int i = 0 ; i < Min(30,Final.size()) ; i ++) {
            lat = (((Final.get(i).first)*2)-90);
            lon = (((Final.get(i).second)*2)-180);
            final BigDecimal divfactor = new BigDecimal((Sorted.get(Final.get(0)))+(0.1*(Sorted.get(Final.get(0)))));
            final BigDecimal num = new BigDecimal((Sorted.get(Final.get(i))));
            final BigDecimal prob = num.divide(divfactor,5,RoundingMode.HALF_UP);
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(lat, lon))
                    .radius(200000)
                    .strokeColor(R.color.Reed)
                    .fillColor(R.color.Reed)
            );
            Log.e("Crd",lat+ " ," + lon);
            /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    //mMap.clear();
                    Integer latemp = Integer.valueOf((int) (latLng.latitude+90)/2);
                    Integer lotemp = Integer.valueOf((int)(latLng.longitude+180)/2);
                    double min = 999999;
                    int index = 0;
                    double diff = 0.0;
                    List<Pair<Integer,Integer>> Keysrt = new ArrayList<Pair<Integer, Integer>>(Sorted.keySet());
                    for(int i = 0; i<Keysrt.size();i++){
                        int la = Keysrt.get(i).first;
                        int lo = Keysrt.get(i).second;
                        diff = Math.pow(abs(la-latemp),2)/Math.pow(abs(lo-lotemp),2);
                        if(diff < min){
                            min = diff;
                            index = i;
                        }
                    }
                    Integer lat1 = latemp;
                    Integer lon1 = lotemp;
                    Log.i("Prob","Index is --> " +index);
                    latemp = Keysrt.get(index).first;
                    lotemp = Keysrt.get(index).second;

                    double difference = getDistanceFromLatLonInKm(lat1,lon1,latemp,lotemp);
                    Log.i("Position","Lat -> "+ abs(lat1-latemp)+" Long -> " +abs(lon1-lotemp));
                    Pair<Integer,Integer> Temp = new Pair<Integer, Integer>(latemp,lotemp);
                    Log.i("Prob",Sorted.get(Temp) + "/" +Sorted.get(Final.get(0)) + "--- diff -> " + diff+"In Kms --> "+difference);
                    BigDecimal num = new BigDecimal((Sorted.get(Temp)));
                    //BigDecimal num2 = new BigDecimal(divfactor);
                    BigDecimal prob = (num.divide(divfactor,5, RoundingMode.HALF_UP)).divide(new BigDecimal(difference),5,RoundingMode.HALF_UP);
                    try {
                        //double temp = ((Sorted.get(Temp)) / divfactor);
                        //Toast.makeText(getApplicationContext(), "Probability : " + prob, Toast.LENGTH_SHORT).show();
                        Log.i("Prob","("+(Sorted.get(Temp))+" / ("+divfactor+ ")) --- prob --> " + prob);
                    }catch (Exception e){

                    }
                }
            });*/
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat,lon)).title("Probability : " + prob);
            mMap.addMarker(markerOptions);

        }
        try {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng((Final.get(0).first*2)-90,(Final.get(0).second*2)-180), 6);
            mMap.animateCamera(cameraUpdate);
        }
        catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),"Loading Not Done",Toast.LENGTH_SHORT).show();
        }
        catch (IndexOutOfBoundsException e){
            Toast.makeText(getApplicationContext(),"Empty Map",Toast.LENGTH_SHORT).show();
        }
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


    }

    double getDistanceFromLatLonInKm(Integer lat1,Integer lon1,Integer lat2,Integer lon2) {
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        return d;
    }

    double deg2rad(Integer deg) {
        return deg * (Math.PI/180);
    }

}