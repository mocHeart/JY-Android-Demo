package com.hg.jy.activity.dm024;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;
import com.hg.jy.activity.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act024 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // 定义下拉列表需要显示的文本数组
    private final static String[] starArray = {"水星", "金星", "地球", "火星", "木星", "土星"};

    // 定义下拉列表需要显示的行星图标数组
    private static final int[] iconArray = {
            R.drawable.shuixing, R.drawable.jinxing, R.drawable.diqiu,
            R.drawable.huoxing, R.drawable.muxing, R.drawable.tuxing
    };


    private Spinner sp_dropdown;
    private Spinner sp_dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act024);

        sp_dropdown = findViewById(R.id.dm024_sp_dropdown);
        sp_dialog = findViewById(R.id.dm024_sp_dialog);


        // 声明一个下拉列表的数组适配器
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(this, R.layout.act024_item_select, starArray);
        sp_dropdown.setAdapter(dropdownAdapter);
        // 设置下拉框默认显示第一项
        sp_dropdown.setSelection(0);
        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp_dropdown.setOnItemSelectedListener(this);



        // 声明一个下拉列表的数组适配器
        ArrayAdapter<String> dialogAdapter = new ArrayAdapter<>(this, R.layout.act024_item_select, starArray);
        // 设置下拉框的标题
        sp_dialog.setPrompt("请选择行星");
        sp_dialog.setAdapter(dialogAdapter);
        sp_dialog.setSelection(0);
        sp_dialog.setOnItemSelectedListener(this);



        // 声明一个映射对象的列表，用于保存行星的图标与名称配对信息
        List<Map<String, Object>> list = new ArrayList<>();
        // iconArray是行星的图标数组，starArray是行星的名称数组
        for (int i = 0; i < iconArray.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("icon", iconArray[i]);
            item.put("name", starArray[i]);
            list.add(item);
        }

        // 声明一个下拉列表的简单适配器，其中指定了图标与文本两组数据
        SimpleAdapter iconAdapter = new SimpleAdapter(this, list,
                R.layout.act024_item_simple,
                new String[]{"icon", "name"},
                new int[]{R.id.dm024_iv_icon, R.id.dm024_tv_name});

        Spinner sp_icon = findViewById(R.id.dm024_sp_icon);
        sp_icon.setAdapter(iconAdapter);
        sp_icon.setSelection(0);
        sp_icon.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "您选择的是" + starArray[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        ToastUtil.show(this, "您没有选择任何东西！");
    }
}