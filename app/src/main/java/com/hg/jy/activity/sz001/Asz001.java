package com.hg.jy.activity.sz001;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;
import com.hg.jy.activity.utils.ViewUtil;

import java.util.Random;

public class Asz001 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private TextView tv_password;
    private EditText et_password;
    private Button btn_forget;
    private CheckBox ck_remember;
    private EditText et_phone;
    private RadioButton rb_password;
    private RadioButton rb_verifycode;
    private ActivityResultLauncher<Intent> register;
    private Button btn_login;
    private String mPassword = "111111";
    private String mVerifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asz001);

        RadioGroup rb_login = findViewById(R.id.sz001_rg_login);
        tv_password = findViewById(R.id.sz001_tv_password);
        et_phone = findViewById(R.id.sz001_et_phone);
        et_password = findViewById(R.id.sz001_et_password);
        btn_forget = findViewById(R.id.sz001_btn_forget);
        ck_remember = findViewById(R.id.sz001_ck_remember);
        rb_password = findViewById(R.id.sz001_rb_password);
        rb_verifycode = findViewById(R.id.sz001_rb_verifycode);
        btn_login = findViewById(R.id.sz001_btn_login);

        // 给rg_login(单选按钮组RadioGroup)设置单选监听器
        rb_login.setOnCheckedChangeListener(this);

        // 给et_phone添加文本变更监听器
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        // 给et_password添加文本变更监听器
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));
        btn_forget.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
                    // 用户密码已改为新密码，故更新密码变量
                    mPassword = intent.getStringExtra("new_password");
                }
            }
        });

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            // 选择了密码登录
            case R.id.sz001_rb_password:
                tv_password.setText(getString(R.string.login_password));
                et_password.setHint(getString(R.string.input_password));
                btn_forget.setText(getString(R.string.forget_password));
                ck_remember.setVisibility(View.VISIBLE);
                break;
            // 选择了验证码登录
            case R.id.sz001_rb_verifycode:
                tv_password.setText(getString(R.string.verifycode));
                et_password.setHint(getString(R.string.input_verifycode));
                btn_forget.setText(getString(R.string.get_verifycode));
                ck_remember.setVisibility(View.GONE);
                break;
        }
    }

    @SuppressLint({"DefaultLocale", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        if (phone.length() < 11) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()) {
            case R.id.sz001_btn_forget:
                // 选择了密码方式校验，此时要跳到找回密码页面
                if (rb_password.isChecked()) {
                    // 以下携带手机号码跳转到找回密码页面
                    Intent intent = new Intent(this, Asz001LoginForget.class);
                    intent.putExtra("phone", phone);
                    register.launch(intent);
                } else if (rb_verifycode.isChecked()) {
                    // 生成六位随机数字的验证码
                    mVerifyCode = String.format("%06d", new Random().nextInt(999999));
                    // 以下弹出提醒对话框，提示用户记住六位验证码数字
                    AlertDialog.Builder buider = new AlertDialog.Builder(this);
                    buider.setTitle("请记住验证码");
                    buider.setMessage("手机号" + phone + ",本次验证码是" + mVerifyCode + ",请输入验证码");
                    buider.setPositiveButton("好的", null);
                    AlertDialog dialog = buider.create();
                    dialog.show();
                }
                break;
            case R.id.sz001_btn_login:
                // 密码方式校验
                if (rb_password.isChecked()) {
                    if (!mPassword.equals(et_password.getText().toString())) {
                        Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 提示用户登录成功
                    loginSuccess();
                } else if (rb_verifycode.isChecked()) {
                    // 验证码方式校验
                    if (!mVerifyCode.equals(et_password.getText().toString())) {
                        Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // 提示用户登录成功
                    loginSuccess();
                }
                break;
        }
    }


    // 校验通过，登录成功
    private void loginSuccess() {
        String desc = String.format("您的手机号码是%s，恭喜你通过登录验证，点击“确定”按钮返回上个页面",
                et_phone.getText().toString());
        // 以下弹出提醒对话框，提示用户登录成功
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确定返回", (dialog, which) -> {
            finish();  // 结束当前的活动页面
        });
        builder.setNegativeButton("我再看看", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // 定义一个编辑框监听器，在输入文本达到指定长度时自动隐藏输入法
    private class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int mMaxLen;
        public HideTextWatcher(EditText v, int maxLength) {
            this.mView = v;
            this.mMaxLen = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() == mMaxLen) {
                // 隐藏输入法软键盘
                ViewUtil.hideOneInputMethod(Asz001.this, mView);
            }
        }
    }
}
