package com.hg.jy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hg.jy.activity.dm003.ActivityDm003;
import com.hg.jy.activity.dm004.ActivityDm004;
import com.hg.jy.activity.dm005.ActivityDm005;
import com.hg.jy.activity.dm006.ActivityDm006;
import com.hg.jy.activity.dm007.ActivityDm007;
import com.hg.jy.activity.dm008.ActivityDm008;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button dm003Btn = findViewById(R.id.dm003);
        Button dm004Btn = findViewById(R.id.dm004);
        Button dm005Btn = findViewById(R.id.dm005);
        Button dm006Btn = findViewById(R.id.dm006);
        Button dm007Btn = findViewById(R.id.dm007);
        Button dm008Btn = findViewById(R.id.dm008);
        dm003Btn.setOnClickListener(this);
        dm004Btn.setOnClickListener(this);
        dm005Btn.setOnClickListener(this);
        dm006Btn.setOnClickListener(this);
        dm007Btn.setOnClickListener(this);
        dm008Btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dm003:
                startActivity(new Intent(this, ActivityDm003.class));
                break;
            case R.id.dm004:
                startActivity(new Intent(this, ActivityDm004.class));
                break;
            case R.id.dm005:
                startActivity(new Intent(this, ActivityDm005.class));
                break;
            case R.id.dm006:
                startActivity(new Intent(this, ActivityDm006.class));
                break;
            case R.id.dm007:
                startActivity(new Intent(this, ActivityDm007.class));
                break;
            case R.id.dm008:
                startActivity(new Intent(this, ActivityDm008.class));
                break;
        }
    }
}