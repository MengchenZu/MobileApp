package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;

public class SecondActivity extends AppCompatActivity {

    private Handler uiHandler = new Handler();
    private Button registerButton;
    private TextView loginText;
    private TextView errorText;
    private ProgressBar progressBar;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText confirmEdit;
    private EditText nicknameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        registerButton = (Button) findViewById(R.id.Register_button);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String password = passwordEdit.getText().toString();
                String confirm = confirmEdit.getText().toString();
                if(password.equals(confirm)) {
                    progressBar.setVisibility(View.VISIBLE);
                    registerThread registerThread = new registerThread();
                    registerThread.start();
                }
                else {
                    errorText.setText("Please check your password.");
                }

            }
        });

        loginText = (TextView) findViewById(R.id.Login_text);
        loginText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, FirstActivity.class);
                startActivity(intent);
            }
        });

        errorText = (TextView) findViewById(R.id.error_text);

        usernameEdit = (EditText) findViewById(R.id.username_edit);

        passwordEdit = (EditText) findViewById(R.id.password_edit);

        confirmEdit = (EditText) findViewById(R.id.confirm_edit);

        nicknameEdit = (EditText) findViewById(R.id.nickname_edit);

    }

    class registerThread extends Thread {
        @Override
        public void run() {
            boolean registerStatus = false;

            try {

                String id = usernameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String nickname = nicknameEdit.getText().toString();
                String info = "Hello!";
                int profileImage = 1;
                //要修改
                Thread.sleep(5000);

                if(registerStatus) {
                    Runnable runnable_1 = new Runnable() {
                        @Override
                        public void run() {
                            //改成跳转到地图界面
                            Intent intent = new Intent(SecondActivity.this, FirstActivity.class);
                            startActivity(intent);
                        }
                    };
                    uiHandler.post(runnable_1);
                }
                else {
                    Runnable runnable_2 = new Runnable() {
                        @Override
                        public void run() {
                            SecondActivity.this.progressBar.setVisibility(View.INVISIBLE);
                            SecondActivity.this.errorText.setText("Sorry, this username had" +
                                        " been occupied. Try another one. ");
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
