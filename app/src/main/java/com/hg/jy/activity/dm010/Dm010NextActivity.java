package com.hg.jy.activity.dm010;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("MissingInflatedId")
public class Dm010NextActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm010_next);
        // 从布局文件中获取名为tv_receive的文本视图
        TextView tvShow = findViewById(R.id.dm010_tv_show);

        // 从上一个页面传来的意图中获取快递包裹
        Bundle bundle = getIntent().getExtras();
        // 从包裹中取出名为request_time的字符串
        assert bundle != null;
        String request_time = bundle.getString("request_time");
        // 从包裹中取出名为request_content的字符串
        String request_content = bundle.getString("request_content");

        String desc = String.format("收到请求消息：\n请求时间为%s\n请求内容为%s",
                request_time, request_content);

        // 把请求消息的详情显示在文本视图上
        tvShow.setText(desc);

        // 点击按钮时回传给上一个页面
        findViewById(R.id.dm010_backPage).setOnClickListener(this);

    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(); // 创建一个新意图
        Bundle bundle = new Bundle(); // 创建一个新包裹
        bundle.putString("response_time", new SimpleDateFormat("HH:mm:ss").format(new Date()));
        bundle.putString("response_content", "返回的内容...");
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}