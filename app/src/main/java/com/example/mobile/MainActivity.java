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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<User> friendList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFriends();
        Collections.sort(friendList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (o1.getName().compareTo(o2.getName()) < 0) {
                    return -1;
                }
                else if (o1.getName().compareTo(o2.getName()) > 0){
                    return 1;
                }
                return 0;
            }
        });
        UserAdapter adapter = new UserAdapter(MainActivity.this, R.layout.friend_list, friendList);
        ListView listView = (ListView) findViewById(R.id.friend_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User friend = friendList.get(position);
                Intent intent = new Intent(MainActivity.this, FriendProfileActivity.class);
                intent.putExtra("selected_friend", friend);
                startActivity(intent);
            }
        });

        ImageView addFriend = (ImageView) findViewById(R.id.add_friend_button);
        addFriend.setImageResource(R.drawable.plus_sign);
        addFriend.setClickable(true);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddFriendActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initFriends() {
        for (int i = 0; i < 5; i++) {
            User a = new User("A", R.drawable.profile);
            friendList.add(a);
            User b = new User("B", R.drawable.profile);
            friendList.add(b);
            User c = new User("C", R.drawable.profile);
            friendList.add(c);
            User d = new User("D", R.drawable.profile);
            friendList.add(d);
            User e = new User("E", R.drawable.profile);
            friendList.add(e);
            User f = new User("F", R.drawable.profile);
            friendList.add(f);
            User g = new User("G", R.drawable.profile);
            friendList.add(g);
            User h = new User("H", R.drawable.profile);
            friendList.add(h);
        }
    }
}
