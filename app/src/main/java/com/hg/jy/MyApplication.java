package com.hg.jy;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import com.hg.jy.activity.dm019.database.BookDatabase;

import java.util.HashMap;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private static MyApplication mApp;

    // 声明一个公共的信息映射对象，可当作全局变量使用
    public HashMap<String, String> infoMap = new HashMap<>();

    // 声明一个书籍数据库对象
    private BookDatabase bookDatabase;

    public static MyApplication getInstance() {
        return mApp;
    }

    //在App启动时调用
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Log.d("ning", "MyApplication onCreate");

        // 构建书籍数据库的实例
        bookDatabase = Room.databaseBuilder(this, BookDatabase.class, "book")
                // 允许迁移数据库（发生数据库变更时，Room默认删除原数据库再创建新数据库，
                //              如此一来原来的记录会丢失，故而要改为迁移方式以便保存原有记录）
                .addMigrations()
                // 允许在主线程中操作数据库（Room默认不能在主线程中操作数据库）
                .allowMainThreadQueries()
                .build();

    }

    // 获取书籍数据库的实例
    public BookDatabase getBookDB() {
        return bookDatabase;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate");
    }
}
