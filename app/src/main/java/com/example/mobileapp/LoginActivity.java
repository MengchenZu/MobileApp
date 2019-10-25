package com.example.mobileapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

import com.example.mobileapp.models.UserData;
import com.example.mobileapp.utilities.ApiHelper;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Handler uiHandler = new Handler();
    private Button loginButton;
    private TextView registerText;
    private TextView errorText;
    private ProgressBar progressBar;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private String sessionkey;
    private JSONObject login_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                loginThread loginThread = new loginThread();
                loginThread.start();

            }
        });

        registerText = (TextView) findViewById(R.id.register_text);
        registerText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        errorText = (TextView) findViewById(R.id.error_text);
        errorText.setVisibility(View.INVISIBLE);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);


        usernameEdit = (EditText) findViewById(R.id.Username_edit);

        passwordEdit = (EditText) findViewById(R.id.Password_edit);

    }

    class loginThread extends Thread {
        @Override
        public void run() {
            String username = usernameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            boolean loginStatus = false;
            try {
                login_feedback = new JSONObject(ApiHelper.login(username, password));
                loginStatus = login_feedback.getBoolean("success");

                if(loginStatus) {
                    sessionkey = login_feedback.getString("sessionkey");
                    Runnable runnable_1 = new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                            intent.putExtra("sessionkey", sessionkey);
                            startActivity(intent);
                            finish();
                        }
                    };
                    uiHandler.post(runnable_1);
                }
                else {
                    Runnable runnable_2 = new Runnable() {
                        @Override
                        public void run() {
                            LoginActivity.this.progressBar.setVisibility(View.INVISIBLE);
                            LoginActivity.this.errorText.setText("Please check your username and password.");
                            LoginActivity.this.errorText.setVisibility(View.VISIBLE);
                        }
                    };
                    uiHandler.post(runnable_2);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}