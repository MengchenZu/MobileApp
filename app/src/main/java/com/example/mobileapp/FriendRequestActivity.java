package com.example.mobileapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;

import com.example.mobileapp.models.FriendRequest;
import com.example.mobileapp.models.User;
import com.example.mobileapp.models.UserData;
import com.example.mobileapp.utilities.ApiHelper;

import org.json.JSONObject;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


public class FriendRequestActivity extends AppCompatActivity {

    private ListView friendList;
    private ArrayList<Map<String, Object>> requestData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        friendList = (ListView) findViewById(R.id.request_list);
        requestData = getData();
        MyAdapter adapter = new MyAdapter(this, requestData, R.layout.request_layout,
                new String[]{"image", "name", "info", "button_accept", "button_refuse"},
                new int[]{R.id.image, R.id.name, R.id.info, R.id.button_accept, R.id.button_refuse});
        friendList.setAdapter(adapter);
    }

    private void setAdapter(ArrayList<Map<String,Object>> newData) {
        friendList = (ListView) findViewById(R.id.request_list);
        MyAdapter adapter = new MyAdapter(this, newData, R.layout.request_layout,
                new String[]{"image", "name", "info", "button_accept", "button_refuse"},
                new int[]{R.id.image, R.id.name, R.id.info, R.id.button_accept, R.id.button_refuse});
        friendList.setAdapter(adapter);
    }

    private ArrayList<Map<String,Object>> getData() {
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        List<FriendRequest> requests = UserData.getInstance().getRequests();
        for (int i = 0; i < requests.size(); i++) {
            FriendRequest request = requests.get(i);
            User user = request.getUser();
            String message = request.getMessage();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", user.getLoginId());
            map.put("image", user.getAvatar());
            map.put("name", user.getName());
            map.put("info", message);
            map.put("button_accept", "Accept");
            map.put("button_refuse", "Refuse");
            data.add(map);
        }
        return data;
        /**ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        map = new HashMap<String, Object>();
        map.put("image", R.drawable.avatar_male);
        map.put("name", "Karl");
        map.put("info", "Hi, I am Karl.");
        map.put("button_accept", "Accept");
        map.put("button_refuse", "Refuse");
        data.add(map);
        map = new HashMap<String, Object>();
        map.put("image", R.drawable.avatar_female);
        map.put("name", "Alice");
        map.put("info", "Hey mate!");
        map.put("button_accept", "Accept");
        map.put("button_refuse", "Refuse");
        data.add(map);
        return data;*/
    }

    class MyAdapter extends SimpleAdapter {
        Context context;

        public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            this.context = context;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {

            View view = super.getView(i, convertView, viewGroup);

            final Button agreeButton = (Button) view.findViewById(R.id.button_accept);
            agreeButton.setTag(i);
            final String name = requestData.get(i).get("name").toString();
            agreeButton.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Map<String, Object> map = requestData.get(i);
                        String id = (String) map.get("id");
                        JSONObject process_feedback = new JSONObject(ApiHelper.processRequest(UserData.getInstance().getSessionKey(), id, true));
                        Boolean process_state = process_feedback.getBoolean("success");
                        if (process_state) {
                            Toast.makeText(getApplicationContext(), "You have added " + name, Toast.LENGTH_SHORT).show();
                            requestData.remove(i);
                            setAdapter(requestData);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Error, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button refuseButton = (Button) view.findViewById(R.id.button_refuse);
            refuseButton.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Map<String, Object> map = requestData.get(i);
                        String id = (String) map.get("id");
                        JSONObject process_feedback = new JSONObject(ApiHelper.processRequest(UserData.getInstance().getSessionKey(), id, false));
                        Boolean process_state = process_feedback.getBoolean("success");
                        if (process_state) {
                            Toast.makeText(getApplicationContext(), "Request denied.", Toast.LENGTH_SHORT).show();
                            requestData.remove(i);
                            setAdapter(requestData);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Error, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return view;
        }
    }
}