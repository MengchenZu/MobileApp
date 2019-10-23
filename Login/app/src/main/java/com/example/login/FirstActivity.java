package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

public class FirstActivity extends AppCompatActivity {

    private Handler uiHandler = new Handler();
    private Button loginButton;
    private TextView registerText;
    private TextView errorText;
    private ProgressBar progressBar;
    private EditText usernameEdit;
    private EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

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
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
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
            Boolean loginStatus = false;
            try {
                //要修改
                Thread.sleep(5000);

                if(loginStatus) {
                    Runnable runnable_1 = new Runnable() {
                        @Override
                        public void run() {
                            //改成跳转到地图界面
                            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                            startActivity(intent);
                        }
                    };
                    uiHandler.post(runnable_1);
                }
                else {
                    Runnable runnable_2 = new Runnable() {
                        @Override
                        public void run() {
                            FirstActivity.this.progressBar.setVisibility(View.INVISIBLE);
                            FirstActivity.this.errorText.setText("Please check your username and password.");
                            FirstActivity.this.errorText.setVisibility(View.VISIBLE);
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
