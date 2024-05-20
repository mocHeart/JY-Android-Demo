package com.hg.jy.activity.dm020.server.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hg.jy.activity.dm020.server.database.UserDBHelper;
import com.hg.jy.activity.utils.Constants;

public class UserInfoProvider extends ContentProvider {

    private UserDBHelper dbHelper;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int USERS = 1;
    private static final int USER = 2;

    private SQLiteDatabase rDb;
    private SQLiteDatabase wDb;

    static {
        // 往Uri匹配器中添加指定的数据路径
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "/user", USERS);
        URI_MATCHER.addURI(UserInfoContent.AUTHORITIES, "/user/#", USER);
    }

    public UserInfoProvider() {
    }

    @Override
    public boolean onCreate() {
        Log.d(Constants.TAG, "UserInfoProvider onCreate");
        dbHelper = UserDBHelper.getInstance(getContext());
        rDb = dbHelper.getReadableDatabase();
        wDb = dbHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(Constants.TAG, "UserInfoProvider insert");
        // 匹配到了用户信息表
        if (URI_MATCHER.match(uri) == USERS) {
            Log.i(Constants.TAG, "db hashCode: " + wDb.hashCode());
            // 向指定的表插入数据，返回记录的行号
            long rowId = wDb.insert(UserDBHelper.TABLE_NAME, null, values);
            if (rowId > 0) { // 判断插入是否执行成功
                // 如果添加成功，就利用新记录的行号生成新的地址
                Uri newUri = ContentUris.withAppendedId(UserInfoContent.CONTENT_URI, rowId);
                // 通知监听器，数据已经改变
                getContext().getContentResolver().notifyChange(newUri, null);
            }
        }
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            // 删除多行
            case USERS:
                count = wDb.delete(UserDBHelper.TABLE_NAME, selection, selectionArgs);
                break;
            //删除单行
            case USER:
                String id = uri.getLastPathSegment();
                count = wDb.delete(UserDBHelper.TABLE_NAME, "_id=?", new String[]{id});
                break;
        }
        return count;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(Constants.TAG, "UserInfoProvider query");
        Cursor cursor = null;
        if (URI_MATCHER.match(uri) == USERS) {
            cursor = rDb.query(UserDBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            cursor.setNotificationUri(getContext().getContentResolver(), uri); // 设置内容解析器的监听
        }
        return cursor;
    }

    // 获取Uri支持的数据类型，暂未实现
    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // 更新数据，暂未实现
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}