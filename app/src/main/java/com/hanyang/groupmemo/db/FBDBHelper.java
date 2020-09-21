package com.hanyang.groupmemo.db;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/** Firebase에서 가져오기 **/
public class FBDBHelper {
    private static final String DB_NAME = "groupMemo";

    public static DatabaseReference getDatabaseReference() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        return db.getReference(DB_NAME);
    }

    public static DatabaseReference getDatabaseReference(String id) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        return db.getReference(DB_NAME).child(id);
    }

}
