package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        registerButton = (Button) findViewById(R.id.Register_button);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                registerThread registerThread = new registerThread();
                registerThread.start();
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

    }

    class registerThread extends Thread {
        @Override
        public void run() {
            int i = 1;
            try {
                Thread.sleep(5000);

                switch (i){
                    case 1:
                        Runnable runnable_1 = new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SecondActivity.this, FirstActivity.class);
                                startActivity(intent);
                            }
                        };
                        uiHandler.post(runnable_1);
                    case 2:
                        Runnable runnable_2 = new Runnable() {
                            @Override
                            public void run() {
                                SecondActivity.this.progressBar.setVisibility(View.GONE);
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
