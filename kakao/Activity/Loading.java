package kr.ac.kw.kakao.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import kr.ac.kw.kakao.Class.HowManyJoin;
import kr.ac.kw.kakao.R;

public class Loading extends AppCompatActivity {
    String TAG = "WSY";
    FirebaseFirestore firestore;

    ArrayList<String> emails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        firestore = FirebaseFirestore.getInstance();

        // 가입자 수 얻어오기
        firestore.collection("userCount").document("Count")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData().get("count"));
                                HowManyJoin howManyJoin = HowManyJoin.getInstance();
                                howManyJoin.setCount((int)(long)document.getData().get("count"));
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        // 컬렉션 모든 문서 가져오기
        firestore.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.i(TAG, ""+document.getData().get("Email"));

                                // 가입할 때 사용할 이메일
                                emails.add(document.getData().get("Email").toString()); // 전체 이메일 추가
                            }
                            startLoading(emails);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



    }



    private void startLoading(ArrayList emails) {
        final ArrayList allEmails = emails;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // 로그인 화면으로
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("Emails",allEmails);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
