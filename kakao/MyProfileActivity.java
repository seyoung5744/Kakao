package kr.ac.kw.kakao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.ac.kw.kakao.Class.User;
import kr.ac.kw.kakao.Class.UserArrayList;

public class MyProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    String TAG = "WSY";
    String name;
    TextView myNameTextView;

    UserArrayList userArrayList;
    User user;

    // 프로필 편집
    LinearLayout profileEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        myNameTextView = findViewById(R.id.myNameTextView);

        Intent intent = getIntent();
        name = intent.getStringExtra("MyName");
        Log.i(TAG, "MyProfileActivity 이름 : " + name);
        myNameTextView.setText(name);
    }

    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.close_button: {
                finish();
                break;
            }

            case R.id.profile_edit:{
                Intent intent = new Intent(this, ProfileEditActivity.class);
                intent.putExtra("NAME",name);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                try{
                    // 정상적으로 이름 변경됨.
                    name = data.getStringExtra("ChangeName");
                    myNameTextView.setText(name);

                    ArrayList<User> userList = UserArrayList.userArrayList;
                    Log.i(TAG, userList.size() + "");

                    for(int i = 0; i < userList.size(); i++) {
                        User user = userList.get(i);
                        Log.i(TAG, "이름 : " + user.getName());
                    }
                    for (int i = 0; i < userList.size(); i++) {
                        User user = userList.get(i);

                        if(user.isOwnSelf()) {// true이면, 자기 자신이면
                            Log.i(TAG, "이름 변경 전 : " + user.getName());
                            user.setName(name);
                            Log.i(TAG,"이름 변경 후 : " + user.getName());
                           // UserArrayList.userArrayList.add(i,user);
                        }else {

                        }
                    } // for
                    for(int i = 0; i < userList.size(); i++) {
                        User user = userList.get(i);
                        Log.i(TAG, "이름 : " + user.getName());
                    }
                    Log.i(TAG, "크기 : " + UserArrayList.userArrayList.size());
                   // UserArrayList.userArrayList.add();

                }catch(Exception e)
                {

                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}
