package com.hg.jy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hg.jy.activity.dm014.Act014;
import com.hg.jy.activity.dm003.ActivityDm003;
import com.hg.jy.activity.dm004.ActivityDm004;
import com.hg.jy.activity.dm005.ActivityDm005;
import com.hg.jy.activity.dm006.ActivityDm006;
import com.hg.jy.activity.dm007.ActivityDm007;
import com.hg.jy.activity.dm008.ActivityDm008;
import com.hg.jy.activity.dm009.PageOneActivity;
import com.hg.jy.activity.dm010.Act010IntentUri;
import com.hg.jy.activity.dm011.Act011;
import com.hg.jy.activity.dm012.Act012;
import com.hg.jy.activity.dm013.Act013;

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
        Button dm009Btn = findViewById(R.id.dm009);
        Button dm010Btn = findViewById(R.id.dm010);
        Button dm011Btn = findViewById(R.id.dm011);
        Button dm012Btn = findViewById(R.id.dm012);
        Button dm013Btn = findViewById(R.id.dm013);
        Button dm014Btn = findViewById(R.id.dm014);
        dm003Btn.setOnClickListener(this);
        dm004Btn.setOnClickListener(this);
        dm005Btn.setOnClickListener(this);
        dm006Btn.setOnClickListener(this);
        dm007Btn.setOnClickListener(this);
        dm008Btn.setOnClickListener(this);
        dm009Btn.setOnClickListener(this);
        dm010Btn.setOnClickListener(this);
        dm011Btn.setOnClickListener(this);
        dm012Btn.setOnClickListener(this);
        dm013Btn.setOnClickListener(this);
        dm014Btn.setOnClickListener(this);

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
            case R.id.dm009:
                startActivity(new Intent(this, PageOneActivity.class));
                break;
            case R.id.dm010:
                startActivity(new Intent(this, Act010IntentUri.class));
                break;
            case R.id.dm011:
                startActivity(new Intent(this, Act011.class));
                break;
            case R.id.dm012:
                startActivity(new Intent(this, Act012.class));
                break;
            case R.id.dm013:
                startActivity(new Intent(this, Act013.class));
                break;
            case R.id.dm014:
                startActivity(new Intent(this, Act014.class));
                break;
        }
    }
}