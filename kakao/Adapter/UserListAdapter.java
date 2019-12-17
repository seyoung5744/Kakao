package kr.ac.kw.kakao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.ac.kw.kakao.Activity.FfriendProfileActivity;
import kr.ac.kw.kakao.R;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    String TAG = "WSY";
    private ArrayList<String> mData;
    private ArrayList<Boolean> ownData;
    String name;
    Context context;
    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        ViewHolder(View itemView) {
            super(itemView) ;

            // 클릭한 아이템(친구 목록)의 프로필로 이름 전달
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                      //  mData.set(pos, "item clicked. pos=" + pos) ;
                        String name = mData.get(pos);
                        Log.i(TAG, " 선택한 이름 : " + name);

                        Intent intent = new Intent(context, FfriendProfileActivity.class);
                        intent.putExtra("Name",name);
                        Log.i(TAG, "onClick: " + name);
                        context.startActivity(intent);
                        notifyItemChanged(pos) ;
                    }
                }
            });
            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.nameTextView) ;
        }
    }

    private static UserListAdapter userListAdapter = null;
    public static UserListAdapter getInstance(Context context, ArrayList<String> list, ArrayList<Boolean> ownSelf){
        if(userListAdapter == null)
            userListAdapter = new UserListAdapter(context,list,ownSelf);

        return userListAdapter;
    }
    // 생성자에서 데이터 리스트 객체를 전달받음.
    private UserListAdapter(Context context, ArrayList<String> list, ArrayList<Boolean> ownSelf) {
        this.context = context;

        mData = new ArrayList<>();
        ownData = new ArrayList<>();

        mData = list ;
        ownData = ownSelf;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.people_recyclerview_item, parent, false) ;
        UserListAdapter.ViewHolder vh = new UserListAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(UserListAdapter.ViewHolder holder, int position) {
        name = mData.get(position) ;
        Boolean b = ownData.get(position);
        Log.i(TAG, "onBindViewHolder: " + position);
        Log.i(TAG, "onBindViewHolder: " + name);
        Log.i(TAG, "onBindViewHolder: " + b);

       holder.textView1.setText(name);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}
