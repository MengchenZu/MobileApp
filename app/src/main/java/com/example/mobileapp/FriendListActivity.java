package com.example.mobileapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.mobileapp.models.User;
import com.example.mobileapp.models.UserData;
import com.example.mobileapp.utilities.ApiHelper;
import com.example.mobileapp.utilities.UserAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendListActivity extends AppCompatActivity {

    private List<User> friendList = new ArrayList<>();
    private String sessionkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        Intent previous_intent = getIntent();
        sessionkey = previous_intent.getStringExtra("sessionkey");

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
        UserAdapter adapter = new UserAdapter(FriendListActivity.this, R.layout.friend_list, friendList);
        ListView listView = (ListView) findViewById(R.id.friend_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User friend = friendList.get(position);
                Intent intent = new Intent(FriendListActivity.this, FriendProfileActivity.class);
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
                Intent intent = new Intent(FriendListActivity.this, AddFriendActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initFriends() {
        try {
            Collection<User> friends = UserData.getInstance().getFriends();
            //JSONObject friends_feedback = new JSONObject(ApiHelper.friends(sessionkey));
            //JSONArray friends = friends_feedback.getJSONArray("friends");
            friendList.addAll(friends);
            /**for (int i = 0; i < friends.length(); i++) {
                JSONObject friend = friends.getJSONObject(i);
                User user = User.fromJsonObj(friend);
                //User user = new User(friend.getString("loginId"), friend.getString("name"), R.drawable.profile, friend.getString("info"));
                friendList.add(user);
            }*/
        } catch(Exception e) {
            e.printStackTrace();
        }

        /**for (int i = 0; i < 5; i++) {
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
        }*/
    }
}
