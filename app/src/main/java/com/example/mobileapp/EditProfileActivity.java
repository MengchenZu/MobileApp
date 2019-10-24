package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileapp.models.User;
import com.example.mobileapp.utilities.ApiHelper;

import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {

    private EditText edit_username;
    private EditText edit_info;
    private User user;
    private String sessionkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent previous_intent = getIntent();
        //user = (User) previous_intent.getSerializableExtra("currentUser");
        sessionkey = previous_intent.getStringExtra("sessionkey");

        edit_username = (EditText) findViewById(R.id.edit_profile_username);
        edit_info = (EditText) findViewById(R.id.edit_profile_info);
        Button confirm = (Button) findViewById(R.id.confirm_profile_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_username = edit_username.getText().toString();
                String new_info = edit_info.getText().toString();
                if (new_username.length() == 0) {
                    Toast.makeText(EditProfileActivity.this, "Username can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if (new_info.length() == 0) {
                    Toast.makeText(EditProfileActivity.this, "About can't be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    user.setName(new_username);
                    user.setInfo(new_info);
                    try {
                        JSONObject update_feedback = new JSONObject(ApiHelper.updateProfile(sessionkey, user.getName(), user.getAvatar(), user.getInfo()));
                        if (update_feedback.getBoolean("success")) {
                            Intent intent = new Intent();
                            intent.putExtra("update", user);
                            setResult(111, intent);
                            finish();
                        }
                        else {
                            Toast.makeText(EditProfileActivity.this, "Update failed, please try again", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
