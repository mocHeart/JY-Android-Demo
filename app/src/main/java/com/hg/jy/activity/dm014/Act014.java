package com.hg.jy.activity.dm014;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;
import com.hg.jy.activity.utils.ViewUtil;

public class Act014 extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText et_phone;
    private EditText et_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act014);

        et_phone = findViewById(R.id.dm014_phone);
        et_pwd = findViewById(R.id.dm014_pwd);
        et_phone.setOnFocusChangeListener(this);
        et_pwd.setOnFocusChangeListener(this);

        et_phone.addTextChangedListener(new MyTextWatcher(et_phone, 11));
        et_pwd.addTextChangedListener(new MyTextWatcher(et_pwd, 6));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.dm014_pwd && hasFocus) {
            String phone = et_phone.getText().toString();
            // 手机号码不足11位
            if (phone.trim().isEmpty() || phone.length() < 11) {
                // 手机号码编辑框请求焦点，也就是把光标移回手机号码编辑框
                et_phone.requestFocus();
                Toast.makeText(this, "请输入11位手机号码", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 定义一个编辑框监听器，在输入文本达到指定长度时自动隐藏输入法
    private class MyTextWatcher implements TextWatcher {
        private EditText mView; // 声明一个编辑框对象
        private int maxLen; // 声明一个最大长度变量

        public MyTextWatcher(EditText v, int len) {
            super();
            mView = v;
            maxLen = len;
        }

        // 在编辑框的输入文本变化前触发
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        // 在编辑框的输入文本变化时触发
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        // 在编辑框的输入文本变化后触发
        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString(); // 获得已输入的文本字符串
            // 输入文本达到11位（如手机号码）或者达到6位（如登录密码）时关闭输入法
            if ((str.length() == 11 && maxLen == 11)
                    || (str.length() == 6 && maxLen == 6)) {
                // 隐藏输入法软键盘
                ViewUtil.hideOneInputMethod(Act014.this, mView);
            }
        }
    }
}