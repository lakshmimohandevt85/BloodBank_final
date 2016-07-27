package com.sdsu.cs646.lakshmi.bloodbank.map;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sdsu.cs646.lakshmi.bloodbank.R;


public class ViewLocation extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap map;
    String latitude = "";
    String longitude = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();

        /** Get the value from Intent **/
        latitude = ""+intent.getStringExtra("latitude");
        longitude = ""+intent.getStringExtra("longitude");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap;

        // Add a marker for the donors location.
        LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        map.addMarker(new MarkerOptions().position(latLng).title(getResources().getString(R.string.here)));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.getMaxZoomLevel();
    }
}
