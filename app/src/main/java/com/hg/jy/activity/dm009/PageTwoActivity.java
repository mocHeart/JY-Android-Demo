package com.hg.jy.activity.dm009;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;

public class PageTwoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_two);
        Log.i("DM008", "==>创建活动 onCreate~~~");

        findViewById(R.id.dm008_btn_act_finish).setOnClickListener(this);
        findViewById(R.id.dm008_btn_act_two).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("DM008", "==>开启活动 onStart~~~");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("DM008", "==>重启活动 onRestart~~~");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("DM008", "==>重用已有活动 onNewIntent~~~");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("DM008", "==>恢复活动 onResume~~~");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("DM008", "==>暂停活动 onPause~~~");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("DM008", "==>销毁活动 onDestroy~~~");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dm008_btn_act_finish) {
            // 结束当前的活动页面
            // finish();

            // 创建一个意图对象，准备跳到指定的活动页面
            Intent intent = new Intent(this, PageOneActivity.class);
            // 当栈中存在待跳转的活动实例时，则重新创建该活动的实例，并清除原实例上方的所有实例
            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 设置启动标志
            startActivity(intent); // 跳转到意图指定的活动页面
        }

        if (v.getId() == R.id.dm008_btn_act_two) {
            Intent intent = new Intent(this, PageTwoActivity.class);
            startActivity(intent);
        }

    }
}