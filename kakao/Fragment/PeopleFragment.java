package kr.ac.kw.kakao.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ac.kw.kakao.Class.MyName;
import kr.ac.kw.kakao.MyProfileActivity;
import kr.ac.kw.kakao.R;
import kr.ac.kw.kakao.Adapter.UserListAdapter;
import kr.ac.kw.kakao.Class.User;
import kr.ac.kw.kakao.Class.UserArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PeopleFragment extends Fragment {
    String TAG = "WSY";
    private static final int REQUEST_CODE = 0;
    TextView ownSelfTextView, friendCount;

    // 내 프로필 클릭 때 쓰일 widget
    LinearLayout myLinear;
    String myName;

    Context context;

    // adapter에 전달할 목록
    ArrayList<String> userName = new ArrayList<>();
    ArrayList<Boolean> userOwn = new ArrayList<>();

    UserListAdapter adapter;
    boolean flag  = true;
    MyName myNameClass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate 여기서부터 시작??");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, "on Resume 여기서부터 시작??");
        myNameClass = MyName.getInstance();
        // 친구 목록 출력
        ArrayList<User> userList = UserArrayList.userArrayList;


        Log.i(TAG, "onCreateView: " + userList.size());

//        if(flag)
//        {

            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                Log.i(TAG, "PeopleFragment 이름 : " + user.getName());
                Log.i(TAG, "PeopleFragment 자기 자신 ? " + user.isOwnSelf());

                if (user.isOwnSelf()) {// true이면, 자기 자신이면
                    myName = user.getName();
                    myNameClass.setName(myName); // 채팅방 만들때 쓸 내 이름 저장.
                    ownSelfTextView.setText(myName); // 처음 실행될 떄 이름 설정
                } else {
                    if(flag) {
                        // 자신은 바로 설정하고 나머지 리스트는 ArrayList에 담기. 그러고 UserListAdapter에 전달
                        userName.add(user.getName());
                        userOwn.add(user.isOwnSelf());
                    }
                }
            }
            flag = false;
//        }
        Log.i(TAG, "NAME 개수 : " + userName.size());
        Log.i(TAG, "아이템 크기 : " + adapter.getItemCount());
        Log.i(TAG, "크기크기크기: " + UserArrayList.userArrayList.size());
        friendCount.setText(userName.size() + "");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people, container, false);
        context = view.getContext();

        ownSelfTextView = view.findViewById(R.id.ownSelf);
        friendCount = view.findViewById(R.id.friendCountTextView);
        myLinear = view.findViewById(R.id.gotoProfile);


        ownSelfTextView.setText(myName); // 앱이 일시 중시 했을 때 이름 설정

        // 자신 프로필 변경하러 가기
        myLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myName = ownSelfTextView.getText().toString();
                Intent intent = new Intent(context, MyProfileActivity.class);
                intent.putExtra("MyName",myName);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });





        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        // 리사이클러뷰에 UserListAdapter 객체 지정.
        adapter = UserListAdapter.getInstance(context, userName, userOwn);
        recyclerView.setAdapter(adapter);

        Log.i(TAG, "================================================================================================================");
        return view;
    }


}
