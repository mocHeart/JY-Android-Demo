package com.hg.jy.activity.dm006;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.hg.jy.R;

@SuppressLint("NonConstantResourceId")
public class ActivityDm006 extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm006);

        Button btn1 = findViewById(R.id.dm006_btn1);
        Button btn2 = findViewById(R.id.dm006_btn2);
        Button btn3 = findViewById(R.id.dm006_btn3);
        Button btn4 = findViewById(R.id.dm006_btn4);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dm006_btn1:
                LinearLayout lt = findViewById(R.id.dm006_1);
                if (lt.getVisibility() == View.VISIBLE) {
                    lt.setVisibility(View.GONE);
                } else {
                    lt.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.dm006_btn2:
                RelativeLayout rt = findViewById(R.id.dm006_2);
                if (rt.getVisibility() == View.VISIBLE) {
                    rt.setVisibility(View.GONE);
                } else {
                    rt.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.dm006_btn3:
                GridLayout gt = findViewById(R.id.dm006_3);
                if (gt.getVisibility() == View.VISIBLE) {
                    gt.setVisibility(View.GONE);
                } else {
                    gt.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.dm006_btn4:
                HorizontalScrollView ht = findViewById(R.id.dm006_4);
                if (ht.getVisibility() == View.VISIBLE) {
                    ht.setVisibility(View.GONE);
                } else {
                    ht.setVisibility(View.VISIBLE);
                }
                ScrollView st = findViewById(R.id.dm006_5);
                if (st.getVisibility() == View.VISIBLE) {
                    st.setVisibility(View.GONE);
                } else {
                    st.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}