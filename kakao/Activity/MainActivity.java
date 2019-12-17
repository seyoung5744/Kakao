package kr.ac.kw.kakao.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import kr.ac.kw.kakao.Fragment.ChattingFragment;
import kr.ac.kw.kakao.Fragment.PeopleFragment;
import kr.ac.kw.kakao.R;
import kr.ac.kw.kakao.Class.User;

public class MainActivity extends AppCompatActivity {
    String TAG = "WSY";
    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private PeopleFragment peopleFragment = new PeopleFragment();
    private ChattingFragment chattingFragment = new ChattingFragment();

    Toolbar tb;
    TabLayout tabs;
    TextView titleTextView;

    // User정보 얻어오기
    FirebaseFirestore firestore;
    User user;

    String loginEmail; // 로그인한 사람의 이메일

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//         MenuInflater menuInflater = menuInflater.inflate(R.menu.people_actionbar_menu, menu) ;
//        this.menu = menu;
//        return true;
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        boolean flag = intent.getBooleanExtra("Login", true);
        Log.i(TAG, "로딩 실행??? " + flag);
        if (flag) {
            Loading();
        }

        tb = findViewById(R.id.app_toolbar);
        setSupportActionBar(tb);

        titleTextView = findViewById(R.id.titleTextView);

        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_layout, peopleFragment).commitAllowingStateLoss();

        // 버튼 클릭시 사용되는 리스너를 구현합니다.

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView_main_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //  FragmentTransaction transaction = fragmentManager.beginTransaction();
                        Fragment frag = null;
                        // 어떤 메뉴 아이템이 터치되었는지 확인합니다.
                        switch (item.getItemId()) {

                            case R.id.menuitem_bottombar_people: {
                                frag = peopleFragment;
                                titleTextView.setText("친구");
                                //   transaction.replace(R.id.fragment_layout, frag).commitAllowingStateLoss();

                                break;
                            }
                            case R.id.menuitem_bottombar_chat: {
                                frag = chattingFragment;
                                titleTextView.setText("채팅");
                                break;
                            }

                        } // switch

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, frag).commitNow();
                        return true;
                    }
                });

    }

    @Override
    protected void onPause() {
        super.onPause();
//        SharedPreferences sharedPreferences = getSharedPreferences("sFile",MODE_PRIVATE);
//
//        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Boolean globalFlag = false; // 사용자가 입력한 저장할 데이터
//        editor.putBoolean("GLOBALFLAG",globalFlag); // key, value를 이용하여 저장하는 형태
//
//        editor.commit();
    }

    private void Loading() {
        Intent intent = new Intent(this, Loading.class);
        startActivity(intent);
        finish();
    }
}
