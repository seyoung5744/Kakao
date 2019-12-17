package kr.ac.kw.kakao.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import kr.ac.kw.kakao.Chatting.ChattingActivity;
import kr.ac.kw.kakao.Class.MyName;
import kr.ac.kw.kakao.R;

public class FfriendProfileActivity extends AppCompatActivity {
    String TAG = "WSY";
    TextView nameTextView;
    String userName;
    MyName myNameClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        nameTextView = findViewById(R.id.userNameTextView);
        myNameClass = MyName.getInstance();

        Intent intent = getIntent();
        userName = intent.getStringExtra("Name");
        Log.i(TAG, "이름 : " + userName); // 상대방 이름
        nameTextView.setText(userName);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.closeButton:
            {
                finish();
                break;
            }
            case R.id.startChatButton:
            {
                Intent intent = new Intent(this, ChattingActivity.class);
                intent.putExtra("chatName", userName); // 상대방이름
                intent.putExtra("userName", myNameClass.getName());
                Log.i(TAG, "내 이름 : " + myNameClass.getName());
                startActivity(intent);
                finish();
            }
        }
    }
}
