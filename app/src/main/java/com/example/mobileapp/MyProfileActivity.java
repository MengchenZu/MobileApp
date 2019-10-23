package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileapp.models.User;
import com.example.mobileapp.models.UserState;

public class MyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Intent previous_intent = getIntent();
        User user = (User) previous_intent.getSerializableExtra("currentUser");
        UserState userState = (UserState) previous_intent.getSerializableExtra("currentState");

        ImageView profile_image = (ImageView) findViewById(R.id.friend_profile_avatar);
        profile_image.setImageResource(user.getAvatar());
        TextView profile_id = (TextView) findViewById(R.id.friend_profile_id);
        String id_text = "ID: " + user.loginId;
        profile_id.setText(id_text);
        TextView profile_username = (TextView) findViewById(R.id.friend_profile_username);
        String username_text = "Username: " + user.name;
        profile_username.setText(username_text);
        TextView profile_info = (TextView) findViewById(R.id.friend_profile_info);
        String info_text = "About: " + user.info;
        profile_info.setText(info_text);
        Button show_location = (Button) findViewById(R.id.friend_profile_button);
        show_location.setText(getResources().getText(R.string.show_location));
    }
}
