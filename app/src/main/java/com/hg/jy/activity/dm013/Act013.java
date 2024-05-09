package com.hg.jy.activity.dm013;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;

@SuppressLint({"NonConstantResourceId", "UseSwitchCompatOrMaterialCode"})
public class Act013 extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    private TextView dm013_tv_result;
    private TextView dm013_tv_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act013);

        // 从布局文件中获取名叫ck_system的复选框
        CheckBox ck_system = findViewById(R.id.dm013_ck_system);
        // 给ck_system设置勾选监听器，一旦用户点击复选框，就触发监听器的onCheckedChanged方法
        ck_system.setOnCheckedChangeListener(this);

        Switch sw_status = findViewById(R.id.dm013_sw_status);
        dm013_tv_result = findViewById(R.id.dm013_tv_result);
        sw_status.setOnCheckedChangeListener(this);

        // 仿IOS的Switch
        CheckBox ck_status = findViewById(R.id.dm013_ck_status);
        ck_status.setOnCheckedChangeListener(this);

        // 单选按钮组
        RadioGroup rb_gender = findViewById(R.id.rg_gender);
        rb_gender.setOnCheckedChangeListener(this);
        dm013_tv_res = findViewById(R.id.dm013_tv_res);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String desc = "";
        switch (buttonView.getId()) {
            case R.id.dm013_sw_status:
                desc = String.format("Switch按钮的状态是%s", isChecked ? "开" : "关");
                dm013_tv_result.setText(desc);
                break;
            case R.id.dm013_ck_system:
                desc = String.format("您%s了这个CheckBox", isChecked ? "勾选" : "取消勾选");
                buttonView.setText(desc);
                break;
            case R.id.dm013_ck_status:
                desc = String.format("仿iOS开关的状态是%s", isChecked ? "开" : "关");
                dm013_tv_result.setText(desc);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_male:
                dm013_tv_res.setText("哇哦，你是个帅气的男孩");
                break;
            case R.id.rb_female:
                dm013_tv_res.setText("哇哦，你是个漂亮的女孩");
                break;
        }
    }
}