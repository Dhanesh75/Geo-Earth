package com.example.android.quakereport;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class Map_display extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<Earthquake> Quakes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_activity);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Quakes = EarthquakeActivity.FinalQuakes;
    }


    public int Min(int a,int b){
        return a < b ?a:b;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            for (int i = 0; i < Min(300,Quakes.size()); i++) {
                String primaryLocation;
                String locationOffset;
                String LOCATION_SEPARATOR = "of";
                String originalLocation = Quakes.get(i).getLocation();
                if (originalLocation.contains(LOCATION_SEPARATOR)) {

                    String[] parts = originalLocation.split(LOCATION_SEPARATOR);

                    locationOffset = parts[0] + LOCATION_SEPARATOR;

                    primaryLocation = parts[1];
                } else {

                    primaryLocation = originalLocation;
                }
                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(Quakes.get(i).getLatitude(), Quakes.get(i).getLongitude())).title(primaryLocation+", "+Quakes.get(i).getMagnitude());
                mMap.addMarker(markerOptions);

            }
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),"Loading Not Finished",Toast.LENGTH_SHORT).show();
        }

        try {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Quakes.get(0).getLatitude(), Quakes.get(0).getLongitude()), 2);
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

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,EarthquakeActivity.class);

        startActivity(intent);
        this.finish();
        super.onBackPressed();
    }
}