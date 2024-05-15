package com.hg.jy.activity.dm017;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;

public class Act017 extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_database;
    private String mDatabaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act017);

        tv_database = findViewById(R.id.dm017_tv_database);
        findViewById(R.id.dm017_btn_database_create).setOnClickListener(this);
        findViewById(R.id.dm017_btn_database_delete).setOnClickListener(this);
        findViewById(R.id.dm017_btn_intent).setOnClickListener(this);

        // 生成一个测试数据库的完整路径
        mDatabaseName = getFilesDir() + "/test.db";
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        String desc = null;
        switch (v.getId()) {
            case R.id.dm017_btn_database_create:
                // 创建或打开数据库。数据库如果不存在就创建它，如果存在就打开它
                SQLiteDatabase db = openOrCreateDatabase(mDatabaseName, MODE_PRIVATE, null);
                desc = String.format("数据库%s创建%s", db.getPath(), "成功");
                tv_database.setText(desc);
                break;

            case R.id.dm017_btn_database_delete:
                // 删除数据库
                boolean result = deleteDatabase(mDatabaseName);
                desc = String.format("数据库%s删除%s", mDatabaseName, result?"成功":"失败");
                tv_database.setText(desc);
                break;

            case R.id.dm017_btn_intent:
                Intent intent = new Intent(this, Act017Sqlite.class);
                startActivity(intent);
                break;
        }
    }
}