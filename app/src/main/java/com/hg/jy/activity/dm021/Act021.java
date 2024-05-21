package com.hg.jy.activity.dm021;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;
import com.hg.jy.activity.utils.Constants;
import com.hg.jy.activity.utils.PermissionUtil;
import com.hg.jy.activity.utils.ToastUtil;

public class Act021 extends AppCompatActivity implements View.OnClickListener {

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_CONTACTS,    // 读取联系人
            Manifest.permission.WRITE_CONTACTS,   // 写入联系人
            Manifest.permission.SEND_SMS,         // 发送短信
            Manifest.permission.READ_SMS          // 读取短信
    };

    private static final int REQUEST_CODE_ALL = 1;
    private static final int REQUEST_CODE_CONTACTS = 2;
    private static final int REQUEST_CODE_SMS = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act021);

        findViewById(R.id.dm021_permission01).setOnClickListener(this);
        findViewById(R.id.dm021_permission02).setOnClickListener(this);

        // 打开页面立即申请权限
        PermissionUtil.checkPermission(this, PERMISSIONS, REQUEST_CODE_ALL);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dm021_permission01:
                PermissionUtil.checkPermission(this,new String[] {
                        PERMISSIONS[0], PERMISSIONS[1] }, REQUEST_CODE_CONTACTS);
                break;
            case R.id.dm021_permission02:
                PermissionUtil.checkPermission(this, new String[] {
                        PERMISSIONS[2], PERMISSIONS[3] }, REQUEST_CODE_SMS);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ALL:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d(Constants.TAG, "所有权限获取成功");
                } else {
                    // 部分权限获取失败
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            // 判断是什么权限没有获取成功
                            switch (permissions[i]) {
                                case Manifest.permission.READ_CONTACTS:
                                case Manifest.permission.WRITE_CONTACTS:
                                    ToastUtil.show(this, "获取通讯录读写权限失败！");
                                    jumpToSettings();
                                    return;
                                case Manifest.permission.SEND_SMS:
                                case Manifest.permission.READ_SMS:
                                    ToastUtil.show(this, "获取收发短信权限失败！");
                                    jumpToSettings();
                                    return;
                            }
                        }
                    }
                }
                break;
            case REQUEST_CODE_CONTACTS:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d(Constants.TAG, "通讯录权限获取成功");
                } else {
                    ToastUtil.show(this, "获取通讯录读写权限失败！");
                    jumpToSettings();
                }
                break;
            case REQUEST_CODE_SMS:
                if (PermissionUtil.checkGrant(grantResults)) {
                    Log.d("ning", "收发短信权限获取成功");
                } else {
                    ToastUtil.show(this, "获取收发短信权限失败！");
                    jumpToSettings();
                }
                break;
        }
    }

    // 跳转到应用设置界面
    private void jumpToSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}