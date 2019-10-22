package com.example.mobile;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        Intent previous_intent = getIntent();
        User friend = (User) previous_intent.getSerializableExtra("selected_friend");
        ImageView profile_image = (ImageView) findViewById(R.id.friend_profile_image);
        profile_image.setImageResource(friend.getAvatar());
        TextView profile_name = (TextView) findViewById(R.id.friend_profile_name);
        profile_name.setText(friend.getName());
        Button show_location = (Button) findViewById(R.id.friend_profile_button);
        show_location.setText(getResources().getText(R.string.show_location));
    }
}
