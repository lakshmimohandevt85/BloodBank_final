package com.sdsu.cs646.lakshmi.bloodbank.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sdsu.cs646.lakshmi.bloodbank.R;


    public class ViewMapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener
    {
        LocationManager locationManager;
        String provider;
        private GoogleMap map;
        String _latitude;
        String _longitude;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_map);
            Intent intent = getIntent();
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            setUpMapIfNeeded();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            provider = locationManager.getBestProvider(new Criteria(), false);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
            {

                return;
            }
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null)
            {
                onLocationChanged(location);
            }
        }
        @Override
        protected void onResume()
        {
            super.onResume();
            setUpMapIfNeeded();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) this);
        }
        private void setUpMapIfNeeded()
        {
            // Do a null check to confirm that we have not already instantiated the map.
            if (map == null)
            {
                // Try to obtain the map from the SupportMapFragment.
                map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                        .getMap();
                // Check if we were successful in obtaining the map.
                if (map != null)
                {
                    setUpMap();
                }
            }
        }
        private void setUpMap()
        {

        }

        @Override
        public void onLocationChanged(Location location)
        {
            Double lat = location.getLatitude();
            Double lng = location.getLongitude();

            map.clear();
            map.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Your location"));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

        @Override
        public void onProviderEnabled(String provider)
        {

        }

        @Override
        public void onProviderDisabled(String provider)
        {

        }

        @Override
        protected void onPause()
        {
            super.onPause();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED)
            {
                return;
            }
            locationManager.removeUpdates(this);

        }
        @Override
        public void onMapReady(GoogleMap googleMap)
        {
            map = googleMap;

            map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback()
            {
                @Override
                public void onMapLoaded()
                {
                    map.animateCamera( CameraUpdateFactory.zoomTo( 5.0f ) );

                }
            });

            // Add a marker for the donor location.
            LatLng pothholeLocation = new LatLng(Double.parseDouble("32.7157"), Double.parseDouble("117.1611"));

            map.addMarker(new MarkerOptions().position(pothholeLocation).title(getResources().getString(R.string.here)));

        }


    }
