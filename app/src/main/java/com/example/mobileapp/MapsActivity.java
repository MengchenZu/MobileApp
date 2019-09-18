package com.example.mobileapp;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {    private GoogleMap mMap;
    private LatLng DEFAULT_LOCATION = new LatLng(-37.814, 144.96332);
    private Circle circle;
    LocationManager locationManager;
    LatLng lastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // melbourne: -37.814, 144.96332
//        mMap.addMarker(new MarkerOptions().position(DEFAULT_LOCATION).title("Melbourne"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_LOCATION));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);

        // check for permission
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            lastLocation = getCurrentLocation();
            mMap.setMyLocationEnabled(true);
        } else {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
        addCircle();
        final Bitmap[] icon = new Bitmap[1];
        Thread th = new Thread(new Runnable() {
            public void run() {
                icon[0] = DownloadImg();
            }
        });
        th.start();
        try{
            th.join();
        }catch (Exception e){

        }

        addMarker(BitmapDescriptorFactory.fromBitmap(icon[0]),DEFAULT_LOCATION);



    }

    public Bitmap DownloadImg(){
        try{
            String img = "https://cdn0.iconfinder.com/data/icons/material-circle-apps/512/icon-android-material-design-512.png";
            URL url = new URL(img);
            InputStream is = url.openStream();
            return android.graphics.BitmapFactory.decodeStream(is);

        }catch(Exception e){
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (permissions.length == 1 &&
                    permissions[0].equals("android.permission.ACCESS_FINE_LOCATION") &&
                    grantResults[0] == 0) {     // PackageManager.PERMISSION_GRANTED
                mMap.setMyLocationEnabled(true);
            } else {
                // Permission was denied. Display an error message.
                Log.i("","Permission is not granted");
            }
        }
    }

    public void addCircle(){
        circle = mMap.addCircle(new CircleOptions()
                .center(DEFAULT_LOCATION)
                .radius(100)
                .strokeWidth(10)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(128, 255, 0, 0))
                .clickable(true));

        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                // Flip the r, g and b components of the circle's
                // stroke color.
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
                circle.setRadius(circle.getRadius()==100?200:100);
            }
        });
    }
    public void addMarker(BitmapDescriptor icon, LatLng loc){
        // avatat, latlng
        mMap.addMarker(new MarkerOptions()
                .position(loc)
                .icon(icon)
                .title("your friend")
                );
    }

    public void onClick_btnMyLoc(android.view.View v) {
        LatLng currentLocation = getCurrentLocation();
        if(lastLocation!=null){
            mMap.animateCamera(CameraUpdateFactory.newLatLng(currentLocation == null? lastLocation:currentLocation));
            lastLocation = currentLocation == null?lastLocation:currentLocation;
        }else{
            lastLocation =currentLocation;
        }

        //Check Permissions again
    }

    private LatLng getCurrentLocation(){
        //LocationServices.getFusedLocationProviderClient(this);
        if (locationManager!=null && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location loc= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            loc = loc == null? locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) :loc;
            loc = loc == null? locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER) :loc;
            if (loc !=null)
            {
                return new LatLng(loc.getLatitude(),loc.getLongitude());
            }
            else
            {
                // no loc data
                Toast.makeText(this,"Your location could not be determined", Toast.LENGTH_SHORT).show();
                return null;
            }
        } else {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
            return null;

        }
    }

}
