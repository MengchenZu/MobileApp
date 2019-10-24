package com.example.mobileapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileapp.models.User;
import com.example.mobileapp.models.UserData;
import com.example.mobileapp.utilities.ApiHelper;

import org.json.JSONObject;

public class AddFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        EditText search_id = (EditText) findViewById(R.id.add_friend_account);
        Button add_submit = (Button) findViewById(R.id.add_submit);
        add_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String id = search_id.getText().toString();
                    JSONObject search_feedback = new JSONObject(ApiHelper.search(UserData.getInstance().getSessionKey(), id));
                    Boolean search_state = search_feedback.getBoolean("success");
                    if (search_state) {
                        if (search_feedback.getJSONArray("result").length() != 0) {
                            JSONObject return_user = search_feedback.getJSONArray("result").getJSONObject(0);
                            User user = User.fromJsonObj(return_user);
                            Intent intent = new Intent(AddFriendActivity.this, SearchProfileActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "This ID does not exist.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "This ID does not exist.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
