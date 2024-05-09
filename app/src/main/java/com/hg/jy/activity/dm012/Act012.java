package com.hg.jy.activity.dm012;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;

public class Act012 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act012);

        TextView dmsp1 = findViewById(R.id.dm012_shape1);
        dmsp1.setBackgroundResource(R.drawable.shape_rect_gold);

        TextView dmsp2 = findViewById(R.id.dm012_shape2);
        dmsp2.setBackgroundResource(R.drawable.shape_oval_rose);

    }
}