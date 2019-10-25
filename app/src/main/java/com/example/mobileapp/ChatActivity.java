package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileapp.models.Msg;
import com.example.mobileapp.models.MsgAdapter;
import com.example.mobileapp.models.User;
import com.example.mobileapp.models.UserData;
import com.example.mobileapp.utilities.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    private Handler uiHandler = new Handler();

    private User friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent previous_intent = getIntent();
        friend = (User) previous_intent.getSerializableExtra("friend");
        setTitle(friend.getName());
        //initMsgs();
        listenThread listen = new listenThread();
        listen.start();
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if(!"".equals(content)) {
                    sendThread send = new sendThread();
                    send.start();
                }
            }
        });
    }

    class listenThread extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    JSONObject get_feedback = new JSONObject(ApiHelper.getMessages(UserData.getInstance().getSessionKey()));
                    boolean get_state = get_feedback.getBoolean("success");
                    if (get_state) {
                        JSONArray messages = get_feedback.getJSONArray("messages");
                        for (int i = 0; i < messages.length(); i++) {
                            JSONObject m = messages.getJSONObject(i);
                            if (m.getString("loginId").equals(friend.getLoginId())) {
                                String message = m.getString("message");
                                if (!"".equals(message)) {
                                    Msg msg = new Msg(message, Msg.TYPE_RECEIVED);
                                    msgList.add(msg);
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyItemInserted(msgList.size() - 1);
                                            msgRecyclerView.scrollToPosition(msgList.size() - 1);
                                        }
                                    };
                                    uiHandler.post(runnable);
                                }
                            }
                        }
                    }
                    Thread.sleep(1000);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class sendThread extends Thread {
        @Override
        public void run() {
            try {
                String content = inputText.getText().toString();
                JSONObject send_feedback = new JSONObject(ApiHelper.sendMessage(UserData.getInstance().getSessionKey(), friend.getLoginId(), content));
                boolean send_state = send_feedback.getBoolean("success");
                if (send_state) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyItemInserted(msgList.size() - 1);
                            msgRecyclerView.scrollToPosition(msgList.size() - 1);
                            inputText.setText("");
                        }
                    };
                    uiHandler.post(runnable);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error, please try again.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
