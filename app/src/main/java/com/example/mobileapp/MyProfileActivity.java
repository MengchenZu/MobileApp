package com.example.mobileapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.models.User;
import com.example.mobileapp.models.UserState;
import com.example.mobileapp.utilities.ApiHelper;

import org.json.JSONObject;

public class MyProfileActivity extends AppCompatActivity {

    private User user;
    private UserState userState;
    private String sessionkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        Intent previous_intent = getIntent();
        sessionkey = previous_intent.getStringExtra("sessionkey");
        try {
            JSONObject self = (new JSONObject(ApiHelper.self(sessionkey))).getJSONObject("self");
            user = User.fromJsonObj(self);
        } catch(Exception e) {
            e.printStackTrace();
        }
        userState = (UserState) previous_intent.getSerializableExtra("currentState");
        ImageView profile_image = (ImageView) findViewById(R.id.my_profile_avatar);
        profile_image.setImageResource(user.getAvatar());
        TextView profile_id = (TextView) findViewById(R.id.my_profile_id);
        String id_text = "ID: " + user.getLoginId();
        profile_id.setText(id_text);
        TextView profile_username = (TextView) findViewById(R.id.my_profile_username);
        String username_text = "Username: " + user.getName();
        profile_username.setText(username_text);
        TextView profile_info = (TextView) findViewById(R.id.my_profile_info);
        String info_text = "About: " + user.getInfo();
        profile_info.setText(info_text);
        Button edit_profile = (Button) findViewById(R.id.edit_profile_button);
        edit_profile.setText(getResources().getText(R.string.edit_profile));
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("currentUser", user);
                intent.putExtra("sessionkey", sessionkey);
                startActivityForResult(intent, 1);
            }
        });

        Button logout = (Button) findViewById(R.id.logout);
        logout.setText(getResources().getText(R.string.logout));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject logout_feedback = new JSONObject(ApiHelper.logout(sessionkey));
                    Boolean logout_state = logout_feedback.getBoolean("success");
                    if (logout_state) {
                        Intent result = new Intent();
                        Intent new_login = new Intent(MyProfileActivity.this, LoginActivity.class);
                        result.putExtra("logout", "successful");
                        setResult(222, result);
                        finish();
                        startActivity(new_login);
                    }
                    else {
                        Toast.makeText(MyProfileActivity.this, "Log out failed, please try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Spinner state_menu = (Spinner) findViewById(R.id.state_menu);
        state_menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] states = getResources().getStringArray(R.array.state_menu);
                userState.state = position;
                System.out.println(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 111){
                user = (User) data.getSerializableExtra("update");
                TextView profile_username = (TextView) findViewById(R.id.my_profile_username);
                String username_text = "Username: " + user.getName();
                profile_username.setText(username_text);
                TextView profile_info = (TextView) findViewById(R.id.my_profile_info);
                String info_text = "About: " + user.getInfo();
                profile_info.setText(info_text);
                Toast.makeText(this, "Successfully Update", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
