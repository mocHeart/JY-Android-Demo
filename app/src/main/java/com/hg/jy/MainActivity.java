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
import com.hg.jy.activity.dm015.Act015;
import com.hg.jy.activity.dm016.Act016;
import com.hg.jy.activity.dm017.Act017;
import com.hg.jy.activity.dm018.Act018;
import com.hg.jy.activity.dm019.Act019;
import com.hg.jy.activity.dm020.client.Act020;
import com.hg.jy.activity.dm021.Act021;
import com.hg.jy.activity.dm022.Act022;
import com.hg.jy.activity.dm023.Act023;
import com.hg.jy.activity.sz001.Asz001;
import com.hg.jy.activity.sz002.Asz002;

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
        Button dm015Btn = findViewById(R.id.dm015);
        Button dm016Btn = findViewById(R.id.dm016);
        Button dm017Btn = findViewById(R.id.dm017);
        Button dm018Btn = findViewById(R.id.dm018);
        Button dm019Btn = findViewById(R.id.dm019);
        Button dm020Btn = findViewById(R.id.dm020);
        Button dm021Btn = findViewById(R.id.dm021);
        Button dm022Btn = findViewById(R.id.dm022);
        Button dm023Btn = findViewById(R.id.dm023);
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
        dm015Btn.setOnClickListener(this);
        dm016Btn.setOnClickListener(this);
        dm017Btn.setOnClickListener(this);
        dm018Btn.setOnClickListener(this);
        dm019Btn.setOnClickListener(this);
        dm020Btn.setOnClickListener(this);
        dm021Btn.setOnClickListener(this);
        dm022Btn.setOnClickListener(this);
        dm023Btn.setOnClickListener(this);

        Button sz001Btn = findViewById(R.id.sz001);
        Button sz002Btn = findViewById(R.id.sz002);
        sz001Btn.setOnClickListener(this);
        sz002Btn.setOnClickListener(this);

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
            case R.id.dm015:
                startActivity(new Intent(this, Act015.class));
                break;
            case R.id.dm016:
                startActivity(new Intent(this, Act016.class));
                break;
            case R.id.dm017:
                startActivity(new Intent(this, Act017.class));
                break;
            case R.id.dm018:
                startActivity(new Intent(this, Act018.class));
                break;
            case R.id.dm019:
                startActivity(new Intent(this, Act019.class));
                break;
            case R.id.dm020:
                startActivity(new Intent(this, Act020.class));
                break;
            case R.id.dm021:
                startActivity(new Intent(this, Act021.class));
                break;
            case R.id.dm022:
                startActivity(new Intent(this, Act022.class));
                break;
            case R.id.dm023:
                startActivity(new Intent(this, Act023.class));
                break;

            case R.id.sz001:
                startActivity(new Intent(this, Asz001.class));
                break;
            case R.id.sz002:
                startActivity(new Intent(this, Asz002.class));
                break;
        }
    }
}