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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                loginThread registerThread = new loginThread();
                registerThread.start();

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

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


        EditText usernameEdit = (EditText) findViewById(R.id.Username_edit);

        EditText passwordEdit = (EditText) findViewById(R.id.Password_edit);

    }

    class loginThread extends Thread {
        
    }

}
