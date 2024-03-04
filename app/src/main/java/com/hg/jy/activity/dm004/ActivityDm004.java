package com.hg.jy.activity.dm004;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.hg.jy.R;

public class ActivityDm004 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm004);

        // 设置为透明的绿色，透明就是看不到
        TextView txt03 = findViewById(R.id.tv_txt03);
        txt03.setTextColor(0x00FF00);

        // 设置为不透明的绿色，即正常的绿色
        TextView txt04 = findViewById(R.id.tv_txt04);
        txt04.setTextColor(0xFF00FF00);

        // 使用系统预设的颜色
        TextView txt05 = findViewById(R.id.tv_txt05);
        txt05.setTextColor(Color.RED);

        // 设置背景颜色
        TextView txt08 = findViewById(R.id.tv_txt08);
        txt08.setBackgroundColor(Color.RED);

        // 设置背景颜色
        TextView txt09 = findViewById(R.id.tv_txt09);
        txt09.setBackgroundResource(R.color.blue);

    }
}