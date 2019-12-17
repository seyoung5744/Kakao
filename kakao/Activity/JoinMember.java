package kr.ac.kw.kakao.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.ac.kw.kakao.Class.HowManyJoin;
import kr.ac.kw.kakao.R;


public class JoinMember extends AppCompatActivity {
    String TAG = "WSY";

    String email, name, password, confirmPassword;
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore firestore;

    Map<String, String> joinData;

    HowManyJoin howManyJoin;
    int userCount;
    ArrayList<String> emails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_member);
        firestore = FirebaseFirestore.getInstance();
        howManyJoin = HowManyJoin.getInstance();
        emails = getIntent().getStringArrayListExtra("Emails");
        // init();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.joinButon: {
                email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
                name = ((EditText)findViewById(R.id.nameEditText)).getText().toString();
                password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();
                confirmPassword = ((EditText) findViewById(R.id.confirmPasswordEditText)).getText().toString();

                if (!email.equals("") && !password.equals("") && !confirmPassword.equals("")) { // 공백 검사
                    if (!password.equals(confirmPassword)) { // 비밀번호 불일치 검사
                        Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                    } else { // 비밀번호가 일치할 때
                        joinData = new HashMap<>();
//                        if(isEmailValid(email)) {
//                            joinData.put("Email", email);
//                        }else {
//                            Toast.makeText(this, "이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
//                        }
                        Log.i(TAG, "onClick: ");

                        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                        {
                            Toast.makeText(this,"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
                            //dialog.dismiss();
                            return;
                        }
                        for(String e : emails){
                            if(email.equals(e)){
                                Toast.makeText(this, "이미 가입한 이메일입니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }else{
                                Toast.makeText(this, "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        Log.i(TAG, "onClick: 통과?");
                        joinData.put("Email", email);
                        joinData.put("Password", password);
                        joinData.put("Name",name);

                        Log.i(TAG, joinData.get("Email") + ", " + joinData.get("Password"));

                        userCount = howManyJoin.getCount();
                        firestore.collection("users").document("user" + userCount++)
                                .set(joinData)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(JoinMember.this, "가입 성공!", Toast.LENGTH_SHORT).show();
                                        Map<String, Object> count = new HashMap<>();
                                        count.put("count",userCount);
                                        firestore.collection("userCount").document("Count")
                                                .set(count)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(JoinMember.this, "가입자가 또 늘었어용!!ㅎㅎ", Toast.LENGTH_SHORT).show();
                                                        Intent result = new Intent("com.example.RESULT_ACTION");
                                                        setResult(Activity.RESULT_OK, result);
                                                        finish();
                                                    }
                                                });
                                    }
                                });


                    } // if-else
                } // if
            }
        }
    } // onClick

}
