package com.hanyang.groupmemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hanyang.groupmemo.databinding.ActivityMainBinding;
import com.hanyang.groupmemo.db.FBDBHelper;
import com.hanyang.groupmemo.db.MemoItem;
import com.hanyang.groupmemo.helper.MemoAdapter;
import com.hanyang.groupmemo.helper.PreferenceHelper;

import java.util.ArrayList;
import java.util.Calendar;

//import androidx.appcompat.app.AlertDialog;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private ArrayList<MemoItem> mDataList = new ArrayList<>();
    private MemoAdapter memoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_main);

        /** 학년/반 수정 버튼 이벤트 **/
        b.fabClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAlertDialog();
            }
        });

        /** 추가 버튼 이벤트 **/
        b.fabNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivity(intent);
            }
        });

        /** Adpataer 지정 **/
        memoAdapter = new MemoAdapter(mDataList, this);
        b.rv.setAdapter(memoAdapter);

        if (PreferenceHelper.isAsk(this)) {
            Log.d("HYCC", PreferenceHelper.isAsk(this) + "");
            // 팝업창에서 데이터 입력 받아 Preference에 넣기
            callAlertDialog();
        }

        loadData();
    }

    /** AlertDialog **/
    void callAlertDialog() {
        AlertDialog dlg = getAlertDialog();
        dlg.show();
    }

    /** AlertDialog 만들기**/
    AlertDialog getAlertDialog() {
        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) vi.inflate(R.layout.item_dialog, null);

        return new AlertDialog.Builder(MainActivity.this)
                .setTitle("학년/반")
                .setIcon(R.drawable.ic_baseline_class_24)
                .setCancelable(false)
                .setView(layout)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 메시지 가져오기
                        EditText et = (EditText) layout.findViewById(R.id.etInfo);

                        // 연도 받아오기
                        Calendar cal = Calendar.getInstance();
                        String year = String.format("%02d", cal.get(Calendar.YEAR));

                        String key = year + "_" + et.getText().toString();
                        Log.d("HYCC", key);
                        Toast.makeText(MainActivity.this, key, Toast.LENGTH_LONG).show(); //Test
                        PreferenceHelper.setKey(MainActivity.this, key);

                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }).create();
    }

    /* Firebase에서 데이터 가져오기 */
    void loadData() {
        FBDBHelper.getDatabaseReference(PreferenceHelper.getKey(this)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDataList.clear(); // 기존 ArrayList 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MemoItem item = snapshot.getValue(MemoItem.class);
                    mDataList.add(item);
                }

                memoAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // nop
            }
        });
    }


}