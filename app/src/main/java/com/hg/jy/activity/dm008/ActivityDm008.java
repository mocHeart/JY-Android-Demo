package com.hg.jy.activity.dm008;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.hg.jy.R;

public class ActivityDm008 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dm008);

        ImageView imgView1 = findViewById(R.id.dm008_img1);
        imgView1.setImageResource(R.drawable.syu_logo);
        // 将缩放类型设置为“保持宽高比例，缩放图片使其位于视图中间”
        imgView1.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }
}