package com.hanyang.groupmemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hanyang.groupmemo.databinding.ActivityCreateBinding;
import com.hanyang.groupmemo.db.FBDBHelper;
import com.hanyang.groupmemo.db.MemoItem;
import com.hanyang.groupmemo.helper.PreferenceHelper;

public class CreateActivity extends AppCompatActivity {

    private ActivityCreateBinding b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_create);

        /** 저장 버튼 액션 구현 **/
        b.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = b.etTitle.getText().toString();
                String context = b.etContext.getText().toString();

                if(TextUtils.isEmpty(title)) {
                    Toast.makeText(getApplicationContext(), "제목을 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(TextUtils.isEmpty(context)) {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }

                insertItem(title, context);
                //TODO: 메인화면으로 이동하기
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


    void insertItem(String title, String context) { // firebase에 데이터 입력하기
        //TODO: 프리퍼런스로 가져오기
        String key = PreferenceHelper.getKey(CreateActivity.this);
        DatabaseReference memos = FBDBHelper.getDatabaseReference(key).push();
        memos.setValue(new MemoItem(memos.getKey(), title, context, System.currentTimeMillis()));
    }
}
