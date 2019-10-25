package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    private Handler uiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //修改成名字
        setTitle("???");
        initMsgs();
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
//                    Msg msg = new Msg(content, Msg.TYPE_SENT);
//                    msgList.add(msg);
//                    adapter.notifyItemInserted(msgList.size() - 1);
//                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
//                    inputText.setText("");
                    sendThread send = new sendThread();
                    send.start();
                }
            }
        });
    }

    private void initMsgs() {
        Msg msg1 = new Msg("Hello", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("ThankU", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("What's ur problem", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }

    class listenThread extends Thread {
        @Override
        public void run() {
            while(true) {
                //改成api
                String message = "";
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
                try {
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
            String content = inputText.getText().toString();
            //
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
    }



}
