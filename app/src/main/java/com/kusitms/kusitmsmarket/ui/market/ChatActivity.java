package com.kusitms.kusitmsmarket.ui.market;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.adapter.ChatAdapter;
import com.kusitms.kusitmsmarket.model.ChatData;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private LinearLayoutManager mLayoutManger;

    private DatabaseReference myRef;

    // 이름 정해주기
    String nick = "nick1";

    // 변수 설정
    TextView etChat;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 연동
        etChat = findViewById(R.id.et_chat);
        btnSend = findViewById(R.id.btn_chat_send);

        mRecyclerView = findViewById(R.id.rv_chat);
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManger = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManger);

        List<ChatData> chatList = new ArrayList<>();


        mAdapter = new ChatAdapter();
        mAdapter.setChatDataList(chatList);
        mAdapter.setMyNickName(nick);
        mRecyclerView.setAdapter(mAdapter);



        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        // 전송 버튼을 클릭하면,
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = etChat.getText().toString();

                if(msg != null) {
                    ChatData chatData = new ChatData();
                    chatData.setNickname(nick);
                    chatData.setMsg(msg);

                    chatList.add(chatData);
                    mAdapter.notifyDataSetChanged();

                    myRef.push().setValue(chatData);
                }

            }
        });


//        ChatData chatData = new ChatData();
//        chatData.setNickname(nick);
//        chatData.setMsg("hi");

//        myRef.setValue(chatData);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatData chat = snapshot.getValue(ChatData.class);
                ((ChatAdapter) mAdapter).addChat(chat);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}