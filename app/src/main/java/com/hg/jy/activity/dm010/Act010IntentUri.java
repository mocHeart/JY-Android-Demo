package com.hg.jy.activity.dm010;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint({"NonConstantResourceId", "SimpleDateFormat"})
public class Act010IntentUri extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "LJY>>";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act010_intent_uri);

        findViewById(R.id.dm010_dial).setOnClickListener(this);
        findViewById(R.id.dm010_sms).setOnClickListener(this);
        findViewById(R.id.dm010_my).setOnClickListener(this);
        findViewById(R.id.sendToNextAct).setOnClickListener(this);

        tv = findViewById(R.id.dm010_tv_show2);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        String photoNo = "12345";
        switch (v.getId()) {
            case R.id.dm010_dial:
                intent.setAction(Intent.ACTION_DIAL);
                Uri uri = Uri.parse("tel:" + photoNo);
                intent.setData(uri);
                startActivity(intent);
                break;
            case R.id.dm010_sms:
                intent.setAction(Intent.ACTION_SENDTO);
                Uri uri2 = Uri.parse("smsto:" + photoNo);
                intent.setData(uri2);
                startActivity(intent);
                break;
            case R.id.dm010_my:
                intent.setAction("android.intent.action.NING");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent);
                break;
            case R.id.sendToNextAct:
                Log.i(TAG, "sendToNextAct...");
                // 创建一个意图对象，准备跳到指定的活动页面
                intent = new Intent(this, Dm010NextActivity.class);
                // 创建一个新包裹
                Bundle bundle = new Bundle();
                // 往包裹存入名为request_time的字符串
                bundle.putString("request_time", new SimpleDateFormat("HH:mm:ss").format(new Date()));
                // 往包裹存入名为request_content的字符串
                bundle.putString("request_content", ((TextView) findViewById(R.id.intent_cnt)).getText().toString());
                // 把快递包裹塞给意图
                intent.putExtras(bundle);

                // 跳转到意图指定的活动页面（不期望返回数据）
                // startActivity(intent);

                // 期望接收下个页面的返回数据，第二个参数为本次请求代码
                startActivityForResult(intent, 0);
                break;
        }
    }

    /**
     * 从下一个页面携带参数返回当前页面时触发。其中requestCode为请求代码，resultCode为结果代码，intent为下一个页面返回的意图对象
     * @param requestCode The integer request code originally supplied to startActivityForResult(),
     *                    allowing you to identify who this result came from.
     * @param resultCode The integer result code returned by the child activity through its setResult().
     * @param intent An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // 意图非空，且请求代码为之前传的0，结果代码也为成功
        if (intent != null && requestCode == 0 && resultCode == Activity.RESULT_OK) {
            Bundle bundle = intent.getExtras(); // 从返回的意图中获取快递包裹
            // 从包裹中取出名叫response_time的字符串
            assert bundle != null;
            String response_time = bundle.getString("response_time");
            // 从包裹中取出名叫response_content的字符串
            String response_content = bundle.getString("response_content");
            String desc = String.format("收到返回消息：\n应答时间为：%s\n应答内容为：%s",
                    response_time, response_content);
            tv.setText(desc); // 把返回消息的详情显示在文本视图上
        }
    }
}