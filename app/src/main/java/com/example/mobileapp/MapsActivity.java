package com.example.mobileapp;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.mobileapp.models.User;
import com.example.mobileapp.models.UserState;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.net.URL;
import java.util.Timer;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final LatLng DEFAULT_LOCATION = new LatLng(-37.814, 144.96332); // melbourne
    public User currentUser = new User("userA", "nameA", 3, "I'm ze 1st");
    public UserState currentState = new UserState("userA", -37.814, 144.96332,2);
    private Circle circle;
    private LocationManager locationManager;
    private Timer updateTimer = new Timer();

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

        // Add a marker in Sydney and move the camera
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_LOCATION));


        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);

        // check for permission
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            updateLastLocation();
            mMap.setMyLocationEnabled(true);
        } else {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
//        updateTimer.schedule(new TimerTask(){
//            @Override
//            public void run(){
//                updateInformation();
//            }
//        }, 3000);
        sampleMarker();
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
    public void addMarker(String id, BitmapDescriptor icon, LatLng loc){
        // avatat, latlng
        mMap.addMarker(new MarkerOptions()
                .position(loc)
                .icon(icon)
                .title(id));
    }


    public void onClick_btnMyLoc(android.view.View v) {
        updateLastLocation();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(currentState == null? DEFAULT_LOCATION: new LatLng(currentState.lat, currentState.lng)));
    }

    public void onClick_btnSv(android.view.View v) {
    }

    public void onClick_btnList(android.view.View v) {
        Intent intent = new Intent(MapsActivity.this, FriendListActivity.class);
        startActivity(intent);
    }

    public void onClick_btnMe(android.view.View v) {
        Intent intent = new Intent(MapsActivity.this, FriendProfileActivity.class);
        intent.putExtra("currentUser", currentUser);
        intent.putExtra("currentState", currentState);
        startActivity(intent);
    }

    private void updateLastLocation(){
        if (locationManager!=null && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location loc= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            loc = loc == null? locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) :loc;
            loc = loc == null? locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER) :loc;
            if (loc !=null)
            {
                currentState.lat = loc.getLatitude();
                currentState.lng =loc.getLongitude();
                return;
            }
            else
            {
                // no loc data
                Toast.makeText(this,"Your location could not be determined", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);

        }
    }

    private void sampleMarker(){

        addCircle();
        final Bitmap[] icon = new Bitmap[2];
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
        Matrix matrix = new Matrix();
        matrix.postScale(.95f, .95f);
        icon[1] = Bitmap.createBitmap(icon[0], 0, 0, icon[0].getWidth(),icon[0].getHeight(),matrix, true);
        // update current user's info
        // update all friends info
        Marker marker = mMap.addMarker(new MarkerOptions()
                .title("your friend")
                .icon(BitmapDescriptorFactory.fromBitmap(icon[0]))
                .position(DEFAULT_LOCATION));
        GroundOverlayOptions newarkMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromBitmap(icon[0]))
                .position(DEFAULT_LOCATION, 123);
        mMap.addGroundOverlay(newarkMap);
        beatMarker(marker, BitmapDescriptorFactory.fromBitmap(icon[0]), BitmapDescriptorFactory.fromBitmap(icon[1]));
    }

    static void beatMarker(final Marker marker, final BitmapDescriptor bigIco, final BitmapDescriptor smallIco){
        final Handler handler = new Handler();
        final boolean[] toBig = {true};
        handler.post(new Runnable() {
            long elapsed;
            @Override
            public void run() {
                if(toBig[0]){
                    marker.setIcon(bigIco);
                }else{
                    marker.setIcon(smallIco);
                }
                toBig[0] = !toBig[0];
                handler.postDelayed(this, 200);
            }
        });
    }
}
