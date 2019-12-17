package kr.ac.kw.kakao.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import kr.ac.kw.kakao.R;

public class LoginActivity extends AppCompatActivity {
    String TAG = "WSY";
    private int PICK_CONTACT_REQUEST = 1;
    ArrayList<String> emails;

    TextView joinTextView, emailEditText, passwordEditText;

    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.emailEditText); // 이메일 입력
        passwordEditText = findViewById(R.id.passwordEditText); // 비밀번호 입력
        joinTextView = findViewById(R.id.joinTextView);


        firestore = FirebaseFirestore.getInstance();

        // 가입자들의 이메일들 얻어옴. => 로그인할 때 중복 확인하기 위해
        Intent intent = getIntent();
        emails = intent.getStringArrayListExtra("Emails");

        Log.i(TAG, "가입자들의 이메일 갯수: " + emails.size());

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.joinTextView: {
                Intent intent = new Intent(this, JoinMember.class);
                intent.putExtra("Emails", emails);
                startActivityForResult(intent, PICK_CONTACT_REQUEST);
                break;
            }
            case R.id.loginButton: {
                // 로그인 확인.
                CollectionReference rr = firestore.collection("users");
                Log.i(TAG, emailEditText.getText().toString());
                rr.whereEqualTo("Email", emailEditText.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        String inputPassword = passwordEditText.getText().toString();
                                        Log.i(TAG, "onComplete:zzzzzzzzz " + passwordEditText.getText().toString());

                                        Log.i(TAG, "onComplete: " + document.getData().values()); // 값들의 모음 ex)  [won997, seyoung5744@naver.com, 원세영]

                                        // 로그인한 이메일
                                        Object [] a = document.getData().values().toArray();
                                        Log.i(TAG, "onComplete: " + a[1]);
                                        if(document.getData().values().contains(inputPassword)) {// 값들의 모음 ex)
                                            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                                            intent.putExtra("LoginEmail", (String)a[1]);
                                            intent.putExtra("Login",false);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        })
                        .addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                Log.i(TAG, "zzzzzzzzzzzzzzzzzzzzzzzzzzz");
                            }
                        });

                Log.i(TAG, "onClick: eeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
        } // switch
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // 회원가입을 성공하면 로그인 화면의 회원가입 문구 사라지게 하기.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                joinTextView.setVisibility(View.GONE);
            }
        }
    }
}
