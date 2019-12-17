package kr.ac.kw.kakao.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import kr.ac.kw.kakao.R;
import kr.ac.kw.kakao.Class.User;
import kr.ac.kw.kakao.Class.UserArrayList;

public class WelcomeActivity extends AppCompatActivity {
    String TAG = "WSY";

    FirebaseFirestore firestore;
    User user;

    String loginEmail; // 로그인한 사람의 이메일

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        loginEmail = intent.getStringExtra("LoginEmail");
        Log.i(TAG, "로그인한 이메일~~~~~~~~: " + loginEmail);

        firestore = FirebaseFirestore.getInstance();

        // 새로 가입한 사람이 있을 경우 그 사람도 포함해서 리스트를 가져옴.
        firestore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "Main : " + document.getId() + " => " + document.getData());
                                Log.i(TAG, "Main :  " + document.getData().get("Email"));
                                String email = document.getData().get("Email").toString();
                                String name = document.getData().get("Name").toString();
                                user = new User();
                                Log.i(TAG, "자기 자신인가 ? " + email.equals(loginEmail));
                                if (email.equals(loginEmail)) {
                                    user.setUser(email, name); // user 각 개개인의 클래스가 생성
                                    user.setOwnSelf(true);
                                } else {
                                    user.setUser(email, name); // user 각 개개인의 클래스가 생성
                                    user.setOwnSelf(false);
                                }
                                UserArrayList.userArrayList.add(user);
                            }

                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            //  intent.putExtra("LoginEmail", (String)a[1]);
                            intent.putExtra("Login", false);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
