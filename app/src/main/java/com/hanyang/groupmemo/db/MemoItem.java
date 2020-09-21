package com.hanyang.groupmemo.db;

import com.google.firebase.database.IgnoreExtraProperties;

/** Database 모델에 맞는 클래스 생성 **/
@IgnoreExtraProperties
public class MemoItem {
    public String memoKey;
    public String title;
    public String context;
    public long timestamp;

    /* Default Constructor for Firebase */
    public MemoItem() {}

    public MemoItem(String memoKey, String title, String context, long timestamp) {
        this.memoKey = memoKey;
        this.title = title;
        this.context = context;
        this.timestamp = timestamp;
    }
    public String getMemoKey() {return memoKey;}

    public String getTitle() {
        return title;
    }

    public String getContext() {
        return context;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
