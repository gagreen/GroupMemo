package com.hanyang.groupmemo.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class PreferenceHelper {
    public static final String PREFERENCES_NAME = "student_info";   // SharedPreferences 이름
    private static final String PREFERENCES_KEY = "id";             // 데이터 식별자 역할

    public static SharedPreferences getPreferences(Context ctx) {
        return ctx.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static String getKey(Context ctx) {
        return getPreferences(ctx).getString(PREFERENCES_KEY, "00_00");
    }


    /** 안에 데이터가 있다면 true, 없다면 false **/
    public static boolean isAsk(Context ctx) {
        SharedPreferences pref = getPreferences(ctx);
        if(pref == null){
            return true;
        }
        return TextUtils.isEmpty(pref.getString(PREFERENCES_KEY, null));
    }

    public static void setKey(Context ctx, String key) {
        SharedPreferences.Editor editor = getPreferences(ctx).edit();
        editor.putString(PREFERENCES_KEY, key);
        editor.apply();
    }
}
