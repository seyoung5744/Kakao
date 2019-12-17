package kr.ac.kw.kakao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

import kr.ac.kw.kakao.Class.User;
import kr.ac.kw.kakao.Class.UserArrayList;

public class ProfileEditActivity extends AppCompatActivity {
    String TAG = "WSY";
    private static final int REQUEST_CODE1 = 0;
    private static final int REQUEST_CODE2 = 1;

    ImageView profileImage;
    TextView myNameTextView;
    String name;
    Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        profileImage = findViewById(R.id.profile_image);
        myNameTextView = findViewById(R.id.myNameTextView);

        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        myNameTextView.setText(name);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.cancel: {
                finish();
                break;
            }
            case R.id.completion: { // 완료버튼
                // 서버에 변경된 이미지랑 이름 저장.
                Intent intent = new Intent();
                intent.putExtra("ChangeName", myNameTextView.getText().toString());
                Log.i(TAG, "ProfileEditActivity : " + myNameTextView.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
            case R.id.profile_setting: {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE1);
                break;
            }
            case R.id.name_setting: {
                Intent intent = new Intent(this, EditNameActivity.class);
                intent.putExtra("NAME",name);
                startActivityForResult(intent, REQUEST_CODE2);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE1)
        {
            if(resultCode == RESULT_OK)
            {
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    img = BitmapFactory.decodeStream(in);
                    in.close();
                    profileImage.setImageBitmap(img);
                }catch(Exception e)
                {

                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }

        if(requestCode == REQUEST_CODE2)
        {
            if(resultCode == RESULT_OK)
            {
                try{
                    Log.i(TAG, "이름 변경");

                    String name = data.getStringExtra("ChangeName");
                    Log.i(TAG, "ProfileEditActivity : " + name);
                    myNameTextView.setText(name);
                }catch(Exception e)
                {

                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "이름 변경 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}
