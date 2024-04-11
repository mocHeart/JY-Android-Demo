package com.hg.jy.activity.dm009;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;

public class PageOneActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_one);

        findViewById(R.id.dm009_btn_act_next).setOnClickListener(this);
        findViewById(R.id.dm009_btn_act_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dm009_btn_act_next) {
            // 从当前页面跳到指定的新页面
            // 创建一个意图对象，准备跳到指定的活动页面
            Intent intent = new Intent(this, PageTwoActivity.class);
            // 当栈中存在待跳转的活动实例时，则重新创建该活动的实例，并清除原实例上方的所有实例
            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 设置启动标志
            startActivity(intent); // 跳转到意图指定的活动页面
        }
        if (v.getId() == R.id.dm009_btn_act_login) {
            Intent intent = new Intent(this, Act009LoginInput.class);
            startActivity(intent);
        }
    }

}