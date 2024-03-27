package com.hg.jy.activity.dm007;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hg.jy.R;

@SuppressLint({"DefaultLocale", "NonConstantResourceId", "SetTextI18n"})
public class ActivityDm007 extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private int cnt;
    private TextView tvRet;
    private Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm007);

        tvRet = findViewById(R.id.dm007_tv_ret);

        Button clickBtn = findViewById(R.id.dm007_btn2);
        clickBtn.setOnClickListener(new MyOnClickListener());

        Button clickBtn2 = findViewById(R.id.dm007_btn3);
        clickBtn2.setOnClickListener(this);

        Button clickBtn3 = findViewById(R.id.dm007_btn4);
        clickBtn3.setOnLongClickListener(this);


        findViewById(R.id.dm007_btn_enable).setOnClickListener(this);
        findViewById(R.id.dm007_btn_disable).setOnClickListener(this);
        testBtn = findViewById(R.id.dm007_btn_test);
        testBtn.setOnClickListener(this);
    }


    public void doClick(View view) {
        tvRet.setText(String.format("您点击了BTN001按钮: %d次", cnt++));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dm007_btn2:
                tvRet.setText("您点击了BTN2按钮: " + cnt++ + "次");
                break;
            case R.id.dm007_btn3:
                tvRet.setText("您点击了BTN3按钮: " + cnt++ + "次");
                break;
            case R.id.dm007_btn_enable:
                testBtn.setTextColor(Color.BLACK); // 设置按钮的文字颜色
                testBtn.setEnabled(true);          // 启用当前控件
                break;
            case R.id.dm007_btn_disable:
                testBtn.setTextColor(Color.GRAY); // 设置按钮的文字颜色
                testBtn.setEnabled(false);        // 禁用当前控件
                break;
            case R.id.dm007_btn_test:
                tvRet.setText("您点击了测试按钮: " + cnt++ + "次");
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.dm007_btn4:
                tvRet.setText("您长按了按钮: " + cnt++ + "次");
                return true;
        }
        return false;
    }


    // 定义一个点击监听器，它实现了接口View.OnClickListener
    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            tvRet.setText(String.format("您点击了按钮: %d次", cnt++));
        }
    }
}