package com.hanyang.groupmemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hanyang.groupmemo.databinding.ActivityReadBinding;
import com.hanyang.groupmemo.db.FBDBHelper;
import com.hanyang.groupmemo.db.MemoItem;
import com.hanyang.groupmemo.helper.PreferenceHelper;

public class ReadActivity extends AppCompatActivity {

    ActivityReadBinding b;
    String memoKey;
    boolean editMode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_read);
        // 편집, 저장 버튼 이벤트
        b.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b.llview.getVisibility() == View.VISIBLE) {
                    b.btnSubmit.setText("저장");

                    b.llview.setVisibility(View.GONE);
                    b.lledit.setVisibility(View.VISIBLE);

                    b.etContext.setText(b.tvContext.getText());
                    b.etTitle.setText(b.tvTitle.getText());
                } else {
                    updateMemo(memoKey, b.etTitle.getText().toString(), b.etContext.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        memoKey = getIntent().getExtras().getString("memoKey");
        loadMemo(memoKey);

        // TODO: 키 값으로 데이터 가져오기
        //  데이터 읽기 및 변경
        //  편집모드, 읽기모드 전환
    }

    void updateMemo(String memoKey, final String title, final String context){
        String key = PreferenceHelper.getKey(ReadActivity.this);
        DatabaseReference memos = FBDBHelper.getDatabaseReference(key).child(memoKey);
        memos.setValue(new MemoItem(memos.getKey(), title, context, System.currentTimeMillis()));

    }

    void loadMemo(String memoKey){
        FBDBHelper.getDatabaseReference(PreferenceHelper.getKey(this)).child(memoKey)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        MemoItem item = dataSnapshot.getValue(MemoItem.class);
                        b.tvContext.setText(item.getContext());
                        b.tvTitle.setText(item.getTitle());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}