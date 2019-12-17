package kr.ac.kw.kakao.Chatting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.ac.kw.kakao.Class.MyName;
import kr.ac.kw.kakao.Class.User;
import kr.ac.kw.kakao.R;

public class ChattingActivity extends AppCompatActivity {
    String TAG = "WSY";

    private String CHAT_NAME;
    private String USER_NAME;

    private ListView chat_list_view;
    private EditText chat_edit;
    private Button chat_send;
    private TextView userName;
    private CircleImageView sendButton;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        Log.i(TAG, "채팅 액티비티 실행??");
        // 위젯 ID 참조
        chat_list_view = findViewById(R.id.chat_view);
        chat_edit = findViewById(R.id.chat_edit);
        sendButton = findViewById(R.id.sendButton);
        userName = findViewById(R.id.userName);
//        chat_send = (Button) findViewById(R.id.chat_sent);
//
        // 로그인 화면에서 받아온 채팅방 이름, 유저 이름 저장
        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatName"); // 상대방 이름
        USER_NAME = intent.getStringExtra("userName");

        // 채팅 방 입장
        openChat(CHAT_NAME,USER_NAME); // 상대방 이름이로 채팅방 오픈

        userName.setText(CHAT_NAME);

        // 채팅 입력 칸에서의 아이콘 변화
        chat_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 글자 개수 count
                if(chat_edit.getText().toString().equals("")){
                    sendButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_hashtag,null));
                    sendButton.setCircleBackgroundColor(getResources().getColor(R.color.gray,null));
                }else {
                    sendButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_uparrow,null));
                    sendButton.setCircleBackgroundColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.sendButton: // 보내기 버튼 눌렀을 시.
            {
                if (chat_edit.getText().toString().equals(""))
                    return;

                ChatDTO chat = new ChatDTO(USER_NAME, chat_edit.getText().toString()); //ChatDTO를 이용하여 데이터를 묶는다.
                databaseReference.child("chat").child(USER_NAME).child(CHAT_NAME).push().setValue(chat); // 데이터 푸쉬
                chat_edit.setText(""); //입력창 초기화
                break;
            }

            case R.id.close_button:
            {
                finish();
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void addMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.add(chatDTO.getUserName() + " : " + chatDTO.getMessage());
    }

    private void removeMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.remove(chatDTO.getUserName() + " : " + chatDTO.getMessage());
    }


    private void openChat(String chatName, String userName) {

        // 리스트 어댑터 생성 및 세팅
        final ArrayAdapter<String> adapter  = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, android.R.id.text1);
        chat_list_view.setAdapter(adapter);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리

        // 자기자신 데이터 부분
        databaseReference.child("chat").child(userName).child(chatName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addMessage(dataSnapshot, adapter);
                Log.e("LOG", "s:"+s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeMessage(dataSnapshot, adapter);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //상대방 데이터 부분
        databaseReference.child("chat").child(chatName).child(userName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addMessage(dataSnapshot, adapter);
                Log.e("LOG", "s:"+s);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
