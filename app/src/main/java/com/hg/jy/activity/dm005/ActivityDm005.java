package com.hg.jy.activity.dm005;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hg.jy.R;

@SuppressLint("NonConstantResourceId")
public class ActivityDm005 extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm005);

        Button btn1 = findViewById(R.id.dm005_btn1);
        Button btn2 = findViewById(R.id.dm005_btn2);
        Button btn3 = findViewById(R.id.dm005_btn3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);


        TextView codeTv = findViewById(R.id.dm005_tv_code);
        ViewGroup.LayoutParams codeTvLayoutParams = codeTv.getLayoutParams();
        codeTvLayoutParams.width = dip2px(this, 100);
        codeTv.setLayoutParams(codeTvLayoutParams);
    }

    public static int dip2px(Context context, float dpValue) {
        // 获取当前手机的像素密度（1个dp对应几个px）
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f); // 四舍五入取整
    }

    @Override
    public void onClick(View v) {
        LinearLayout lt;
        switch (v.getId()) {
            case R.id.dm005_btn1:
                lt = findViewById(R.id.dm005_1);
                if (lt.getVisibility() == View.VISIBLE) {
                    lt.setVisibility(View.GONE);
                } else {
                    lt.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.dm005_btn2:
                 lt = findViewById(R.id.dm005_2);
                if (lt.getVisibility() == View.VISIBLE) {
                    lt.setVisibility(View.GONE);
                } else {
                    lt.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.dm005_btn3:
                lt = findViewById(R.id.dm005_3);
                if (lt.getVisibility() == View.VISIBLE) {
                    lt.setVisibility(View.GONE);
                } else {
                    lt.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}