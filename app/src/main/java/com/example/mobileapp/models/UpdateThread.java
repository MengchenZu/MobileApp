package com.example.mobileapp.models;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.mobileapp.MapsActivity;
import com.example.mobileapp.utilities.ApiHelper;

import java.net.URL;
import java.util.List;


public class UpdateThread extends AsyncTask<URL, Integer, Integer> {
    private LocationManager locationManager;
    public User currentUser;
    public UserState currentState = new UserState("userA", -37.814, 144.96332,2, true);
    private String sessionkey;
    private boolean showingText;
    private MapsActivity MapsActivity;

    // Do the long-running work in here
    protected Integer doInBackground(URL... urls) {
        System.out.println("Mengchen");
        updateLastLocation();
        UserData.getInstance().updateFriends(null);
        UserData.getInstance().updateFriendStates(null);
        UserData.getInstance().updateMessages(null);
        UserData.getInstance().updateRequest(null);
        return 0;
    }

    // This is called each time you call publishProgress()
    protected void onProgressUpdate(Integer... progress) {
    }

    // This is called when doInBackground() is finished
    protected void onPostExecute(Long result) {
    }

    private void updateLastLocation(){
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setBearingRequired(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        if (locationManager!=null && ActivityCompat.checkSelfPermission(MapsActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            List<String> providers = locationManager.getProviders(criteria,true);
            Location loc = null;
            for(String provider: providers){
                loc = loc == null? locationManager.getLastKnownLocation(provider) :loc;
            }
//            Location loc= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            loc = loc == null? locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) :loc;
//            loc = loc == null? locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER) :loc;
//            loc = loc == null? locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER) :loc;
            if (loc !=null)
            {
                currentState.lat = loc.getLatitude();
                currentState.lng =loc.getLongitude();
                System.out.println("Mengchen");
                ApiHelper.updateState(sessionkey, currentState.lat, currentState.lng, currentState.state);
                return;
            }
            else
            {
                // no loc data
                if(!showingText) {
                    showingText = true;
                }
                return;
            }
        } else {
            // Permission is not granted
            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},0);

        }
    }
}
