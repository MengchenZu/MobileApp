package com.example.mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Friend> friendList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFriends();
        Collections.sort(friendList, new Comparator<Friend>() {
            @Override
            public int compare(Friend o1, Friend o2) {
                if (o1.getName().compareTo(o2.getName()) < 0) {
                    return -1;
                }
                else if (o1.getName().compareTo(o2.getName()) > 0){
                    return 1;
                }
                return 0;
            }
        });
        FriendAdapter adapter = new FriendAdapter(MainActivity.this, R.layout.friend_list, friendList);
        ListView listView = (ListView) findViewById(R.id.friend_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend friend = friendList.get(position);
                Intent intent = new Intent(MainActivity.this, FriendProfileActivity.class);
                intent.putExtra("selected_friend", friend);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_friend_list:
                Toast.makeText(this, "Please Wait...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_friend:
                Intent intent = new Intent(MainActivity.this, AddFriendActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    private void initFriends() {
        for (int i = 0; i < 5; i++) {
            Friend a = new Friend("A", R.drawable.profile);
            friendList.add(a);
            Friend b = new Friend("B", R.drawable.profile);
            friendList.add(b);
            Friend c = new Friend("C", R.drawable.profile);
            friendList.add(c);
            Friend d = new Friend("D", R.drawable.profile);
            friendList.add(d);
            Friend e = new Friend("E", R.drawable.profile);
            friendList.add(e);
            Friend f = new Friend("F", R.drawable.profile);
            friendList.add(f);
            Friend g = new Friend("G", R.drawable.profile);
            friendList.add(g);
            Friend h = new Friend("H", R.drawable.profile);
            friendList.add(h);
        }
    }
}
