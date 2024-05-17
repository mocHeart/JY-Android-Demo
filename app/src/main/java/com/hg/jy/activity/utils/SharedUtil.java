package com.hg.jy.activity.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtil {

    private static SharedUtil mUtil;

    private SharedPreferences preferences;

    public static SharedUtil getInstance(Context ctx, String name) {
        if (mUtil == null) {
            mUtil = new SharedUtil();
            mUtil.preferences = ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
        }
        return mUtil;
    }

    public void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean readBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

}
