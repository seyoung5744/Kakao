package kr.ac.kw.kakao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditNameActivity extends AppCompatActivity {
    String TAG = "WSY";

    EditText nameEditText;
    TextView textCount;
    String name;
    CircleImageView textErase;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        nameEditText = findViewById(R.id.nameEditText);
        textCount = findViewById(R.id.textCount);
        textErase = findViewById(R.id.textErase);

        // 이름 받아와서 설정
        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        nameEditText.setText(name);
        nameEditText.setSelection(nameEditText.length());

        count = nameEditText.getText().length();
        textCount.setText("" + count);

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(nameEditText.getText().toString().equals("")){
                    textErase.setVisibility(View.INVISIBLE);
                }else {
                    textErase.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 글자 개수 count
                count = nameEditText.getText().length();
                textCount.setText("" + count);
                if(nameEditText.getText().toString().equals("")){
                    textErase.setVisibility(View.INVISIBLE);
                }else {
                    textErase.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(nameEditText.getText().toString().equals("")){
                    textErase.setVisibility(View.INVISIBLE);
                }else {
                    textErase.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textErase: {
                nameEditText.setText("");
                break;
            }
            case R.id.cancel:
            {
                finish();
                break;
            }
            case R.id.completion:
            {
                Intent intent = new Intent();
                intent.putExtra("ChangeName", nameEditText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
        }
    }
}
