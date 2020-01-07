package com.example.android.quakereport;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class maps_activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static double latitude = -361;
    public static double longitude = -361;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_activity);

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



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions= new MarkerOptions().position(new LatLng(latLng.latitude,latLng.longitude)).title("Title");
                mMap.addMarker(markerOptions);
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                Intent intent = new Intent(getApplicationContext(),EarthquakeActivity.class);
                startActivity(intent);
            }
        });
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


    }
}