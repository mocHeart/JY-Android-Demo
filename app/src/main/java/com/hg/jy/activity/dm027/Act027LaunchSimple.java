package com.hg.jy.activity.dm027;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.hg.jy.R;
import com.hg.jy.activity.dm027.adapter.LaunchSimpleAdapter;

public class Act027LaunchSimple extends AppCompatActivity {

    // 声明引导页面的图片数组
    private final int[] launchImageArray = {
            R.drawable.guide_bg1, R.drawable.guide_bg2,
            R.drawable.guide_bg3, R.drawable.guide_bg4 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act027_launch_simple);

        ViewPager vp_launch = findViewById(R.id.dm027_vp_launch);
        LaunchSimpleAdapter adapter = new LaunchSimpleAdapter(this, launchImageArray);
        vp_launch.setAdapter(adapter);
    }
}