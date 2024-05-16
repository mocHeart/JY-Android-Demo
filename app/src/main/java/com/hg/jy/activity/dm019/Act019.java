package com.hg.jy.activity.dm019;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.hg.jy.MyApplication;
import com.hg.jy.R;

public class Act019 extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act019);

        et_name = findViewById(R.id.dm019_et_name);
        et_age = findViewById(R.id.dm019_et_age);
        et_height = findViewById(R.id.dm019_et_height);
        et_weight = findViewById(R.id.dm019_et_weight);
        ck_married = findViewById(R.id.dm019_ck_married);
        findViewById(R.id.dm019_btn_save).setOnClickListener(this);
        findViewById(R.id.dm019_btn_room).setOnClickListener(this);

        app = MyApplication.getInstance();
        reload();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dm019_btn_save:
                String name = et_name.getText().toString();
                String age = et_age.getText().toString();
                String height = et_height.getText().toString();
                String weight = et_weight.getText().toString();

                app.infoMap.put("name", name);
                app.infoMap.put("age", age);
                app.infoMap.put("height", height);
                app.infoMap.put("weight", weight);
                app.infoMap.put("married", ck_married.isChecked() ? "是" : "否");
                break;

            case R.id.dm019_btn_room:
                Intent intent = new Intent(this, Act19Room.class);
                startActivity(intent);
        }
    }

    private void reload() {
        String name = app.infoMap.get("name");
        if (name == null) {
            return;
        }
        String age = app.infoMap.get("age");
        String height = app.infoMap.get("height");
        String weight = app.infoMap.get("weight");
        String married = app.infoMap.get("married");
        et_name.setText(name);
        et_age.setText(age);
        et_height.setText(height);
        et_weight.setText(weight);
        ck_married.setChecked("是".equals(married));
    }
}