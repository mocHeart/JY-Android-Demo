package com.hg.jy.activity.dm017;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;
import com.hg.jy.activity.dm017.database.UserDBHelper;
import com.hg.jy.activity.dm017.enity.User;
import com.hg.jy.activity.utils.ToastUtil;

import java.util.List;

public class Act017Sqlite extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;
    private TextView tv_showStr;
    private UserDBHelper mHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act017_sqlite);

        et_name = findViewById(R.id.dm017_et_name);
        et_age = findViewById(R.id.dm017_et_age);
        et_height = findViewById(R.id.dm017_et_height);
        et_weight = findViewById(R.id.dm017_et_weight);
        ck_married = findViewById(R.id.dm017_ck_married);
        tv_showStr = findViewById(R.id.dm017_show_str);

        findViewById(R.id.dm017_btn_save).setOnClickListener(this);
        findViewById(R.id.dm017_btn_delete).setOnClickListener(this);
        findViewById(R.id.dm017_btn_update).setOnClickListener(this);
        findViewById(R.id.dm017_btn_query).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助器的实例
        mHelper = UserDBHelper.getInstance(this);
        // 打开数据库帮助器的读写连接
        mHelper.openWriteLink();
        mHelper.openReadLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 关闭数据库连接
        mHelper.closeLink();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();
        User user = null;
        switch (v.getId()) {
            case R.id.dm017_btn_save:
                // 以下声明一个用户信息对象，并填写它的各字段值
                user = new User(name,
                        Integer.parseInt(age),
                        Long.parseLong(height),
                        Float.parseFloat(weight),
                        ck_married.isChecked());
                if (mHelper.insert(user) > 0) {
                    ToastUtil.show(this, "添加成功");
                }
                break;
            case R.id.dm017_btn_delete:
                if (mHelper.deleteByName(name) > 0) {
                    ToastUtil.show(this, "删除成功");
                }
                break;
            case R.id.dm017_btn_update:
                user = new User(name,
                        Integer.parseInt(age),
                        Long.parseLong(height),
                        Float.parseFloat(weight),
                        ck_married.isChecked());
                if (mHelper.update(user) > 0) {
                    ToastUtil.show(this, "修改成功");
                }
                break;
            case R.id.dm017_btn_query:
                StringBuilder sb = new StringBuilder();
                List<User> list = mHelper.queryAll();
                //List<User> list = mHelper.queryByName(name);
                for (User u : list) {
                    Log.d("ning", u.toString());
                    sb.append(u).append("\n");
                }
                tv_showStr.setText(sb.toString());
                break;
        }
    }
}