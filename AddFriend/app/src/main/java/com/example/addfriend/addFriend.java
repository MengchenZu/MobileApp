package com.example.addfriend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


public class addFriend extends AppCompatActivity {

    private ListView friendList;
    private ArrayList<Map<String, Object>> requestData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        friendList = (ListView) findViewById(R.id.list_view);
        requestData = getData();
        MyAdapter adapter = new MyAdapter(this, requestData, R.layout.item_layout,
                new String[]{"image", "name", "info", "button_accept", "button_refuse"},
                new int[]{R.id.image, R.id.name, R.id.info, R.id.button_accept, R.id.button_refuse});
        friendList.setAdapter(adapter);
    }

    private void setAdapter(ArrayList<Map<String,Object>> newData) {
        friendList = (ListView) findViewById(R.id.list_view);
        MyAdapter adapter = new MyAdapter(this, newData, R.layout.item_layout,
                new String[]{"image", "name", "info", "button_accept", "button_refuse"},
                new int[]{R.id.image, R.id.name, R.id.info, R.id.button_accept, R.id.button_refuse});
        friendList.setAdapter(adapter);
    }

    private ArrayList<Map<String,Object>> getData() {
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
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
        return data;
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

                    Toast.makeText(getApplicationContext(), "You have added "+ name, Toast.LENGTH_SHORT).show();
                    requestData.remove(i);
                    setAdapter(requestData);
                }
            });

            final Button refuseButton = (Button) view.findViewById(R.id.button_refuse);
            refuseButton.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Request denied.", Toast.LENGTH_SHORT).show();
                    requestData.remove(i);
                    setAdapter(requestData);
                }
            });
            return view;
        }
    }
}
