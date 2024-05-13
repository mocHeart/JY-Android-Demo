package com.hg.jy.activity.dm016;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;

public class Act016 extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private TextView et_show;
    private CheckBox ck_married;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act016);

        et_name = findViewById(R.id.dm016_et_name);
        et_age = findViewById(R.id.dm016_et_age);
        et_height = findViewById(R.id.dm016_et_height);
        et_weight = findViewById(R.id.dm016_et_weight);
        ck_married = findViewById(R.id.dm016_ck_married);
        et_show = findViewById(R.id.dm016_read_show);

        findViewById(R.id.dm016_btn_save).setOnClickListener(this);
        findViewById(R.id.dm016_btn_read).setOnClickListener(this);
        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        reload();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dm016_btn_save:
                String name = et_name.getText().toString();
                String age = et_age.getText().toString();
                String height = et_height.getText().toString();
                String weight = et_weight.getText().toString();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name", name);
                editor.putInt("age", Integer.parseInt(age));
                editor.putFloat("height", Float.parseFloat(height));
                editor.putFloat("weight", Float.parseFloat(weight));
                editor.putBoolean("married", ck_married.isChecked());
                editor.apply();
                break;
            case R.id.dm016_btn_read:
                StringBuffer sb = new StringBuffer();
                sb.append("共享参数保存的信息如下：");
                sb.append("\nname的取值为：");
                sb.append(preferences.getString("name", "Mr Lee"));
                sb.append("\nage的取值为：");
                sb.append(preferences.getInt("age", 30));
                sb.append("\nmarried的取值为：");
                sb.append(preferences.getBoolean("married", true));
                sb.append("\nweight的取值为：");
                sb.append(preferences.getFloat("weight", 0));
                et_show.setText(sb.toString());
        }
    }

    private void reload() {
        String name = preferences.getString("name", null);
        if (name != null) {
            et_name.setText(name);
        }

        int age = preferences.getInt("age", 0);
        if (age != 0) {
            et_age.setText(String.valueOf(age));
        }

        float height = preferences.getFloat("height", 0f);
        if (height != 0f) {
            et_height.setText(String.valueOf(height));
        }

        float weight = preferences.getFloat("weight", 0f);
        if (weight != 0f) {
            et_weight.setText(String.valueOf(weight));
        }

        boolean married = preferences.getBoolean("married", false);
        ck_married.setChecked(married);
    }
}