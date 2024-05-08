package com.hg.jy.activity.dm011;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;

@SuppressLint({"SetTextI18n", "MissingInflatedId"})
public class Act011 extends AppCompatActivity implements View.OnClickListener {

    private TextView tvshow;
    private TextView metashow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act011);

        tvshow = findViewById(R.id.dm011_showStr);
        tvshow.setText("来自字符串资源：今天的天气是" + R.string.weather_str);

        metashow = findViewById(R.id.dm011_showMeta);
        findViewById(R.id.dm011_btn1).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dm011_btn1) {
            try {
                // 获取应用包管理器
                PackageManager pm = getPackageManager();
                // 从应用包管理器中获取当前的活动信息
                ActivityInfo act = pm.getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
                // 获取活动附加的元数据信息
                Bundle bundle = act.metaData;
                // 从包裹中取出名叫weather的字符串
                String value = bundle.getString("weather");
                // 在文本视图上显示文字
                metashow.setText("来自元数据信息：今天的天气是" + value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}