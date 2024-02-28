package com.hg.jy.activity.dm003;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.hg.jy.R;

public class ActivityDm003 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 当前的页面布局采用的是res/layout/activity_dm003.xml
        setContentView(R.layout.activity_dm003);

        // 获取名叫tv_hello的TextView控件，注意添加导包语句import
        TextView tv_hello = findViewById(R.id.tv_hello);
        // 设置TextView控件的文字内容
        tv_hello.setText("你好，世界");
    }
}