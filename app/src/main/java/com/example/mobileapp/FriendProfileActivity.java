package com.example.mobileapp;

import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mobileapp.models.User;
import com.example.mobileapp.models.UserData;
import com.example.mobileapp.models.UserState;

public class FriendProfileActivity extends AppCompatActivity {

    private User friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        Intent previous_intent = getIntent();
        friend = (User) previous_intent.getSerializableExtra("selected_friend");
        ImageView profile_image = (ImageView) findViewById(R.id.friend_profile_avatar);
        profile_image.setImageResource(friend.getAvatar());
        TextView profile_id = (TextView) findViewById(R.id.friend_profile_id);
        String id_text = "ID: " + friend.loginId;
        profile_id.setText(id_text);
        TextView profile_username = (TextView) findViewById(R.id.friend_profile_username);
        String username_text = "Username: " + friend.name;
        profile_username.setText(username_text);
        TextView profile_info = (TextView) findViewById(R.id.friend_profile_info);
        String info_text = "About: " + friend.info;
        profile_info.setText(info_text);

        Button show_location = (Button) findViewById(R.id.friend_profile_button);
        show_location.setText(getResources().getText(R.string.show_location));
        show_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = friend.getLoginId();
                UserState friend_state = UserData.getInstance().getState(id);
                double lat = friend_state.lat;
                double lng = friend_state.lng;
                Intent intent = new Intent();
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                setResult(777, intent);
                finish();
            }
        });

        Button chat = (Button) findViewById(R.id.chat_button);
        chat.setText("Chat");
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendProfileActivity.this, ChatActivity.class);
                intent.putExtra("friend", friend);
                startActivity(intent);
            }
        });
    }
}
