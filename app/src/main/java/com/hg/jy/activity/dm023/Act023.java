package com.hg.jy.activity.dm023;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;
import com.hg.jy.activity.utils.Constants;
import com.hg.jy.activity.utils.ToastUtil;


public class Act023 extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_appendix;
    private ActivityResultLauncher<Intent> mResultLauncher;
    private EditText et_phone;
    private EditText et_title;
    private EditText et_message;
    private Uri picUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act023);

        et_phone = findViewById(R.id.dm023_et_phone);
        et_title = findViewById(R.id.dm023_et_title);
        et_message = findViewById(R.id.dm023_et_message);
        iv_appendix = findViewById(R.id.dm023_iv_appendix);

        iv_appendix.setOnClickListener(this);
        findViewById(R.id.dm023_btn_send_mms).setOnClickListener(this);

        findViewById(R.id.dm023_btn_send_mms_provider).setOnClickListener(this);
        findViewById(R.id.dm023_btn_provider_apk).setOnClickListener(this);

        // 跳转到系统相册选择图片，并返回回调
        mResultLauncher = registerForActivityResult(new ActivityResultContracts
                .StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    // 获得选中图片的路径对象
                    // content://content://com.android.providers.media.documents/document/image%3A283
                    picUri = intent.getData();
                    if (picUri != null) {
                        // ImageView 显示刚刚选中的图片
                        iv_appendix.setImageURI(picUri);
                        Log.d(Constants.TAG, "picUri:" + picUri.toString());
                    }
                }
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dm023_iv_appendix:
                // 跳转到系统相册，选择图片，并返回
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // 设置内容类型为图片类型
                intent.setType("image/*");
                // 打开系统相册，并等待图片选择结果
                mResultLauncher.launch(intent);
                break;

            case R.id.dm023_btn_send_mms:
                // 发送带图片的彩信
                sendMms(et_phone.getText().toString(),
                        et_title.getText().toString(),
                        et_message.getText().toString());
                break;

            case R.id.dm023_btn_send_mms_provider:
                Intent it2 = new Intent(this, Act023ProviderMms.class);
                startActivity(it2);
                break;

            case R.id.dm023_btn_provider_apk:
                Intent it3 = new Intent(this, Act023ProviderApk.class);
                startActivity(it3);
                break;
        }
    }

    // 发送带图片的彩信
    private void sendMms(String phone, String title, String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Intent 的接受者将被准许读取Intent 携带的URI数据
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 彩信发送的目标号码
        intent.putExtra("address", phone);
        // 彩信的标题
        intent.putExtra("subject", title);
        // 彩信的内容
        intent.putExtra("sms_body", message);
        // 彩信的图片附件
        intent.putExtra(Intent.EXTRA_STREAM, picUri);
        // 彩信的附件为图片
        intent.setType("image/*");
        // 因为未指定要打开哪个页面，所以系统会在底部弹出选择窗口
        startActivity(intent);
        ToastUtil.show(this, "请在弹窗中选择短信或者信息应用");
    }
}