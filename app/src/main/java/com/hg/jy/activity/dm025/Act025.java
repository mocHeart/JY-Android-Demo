package com.hg.jy.activity.dm025;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;
import com.hg.jy.activity.dm025.adapter.PlanetBaseAdapter;
import com.hg.jy.activity.dm025.entity.Planet;
import com.hg.jy.activity.utils.ToastUtil;
import com.hg.jy.activity.utils.ViewUtil;

import java.util.List;

@SuppressLint({"UseCompatLoadingForDrawables", "DefaultLocale"})
public class Act025 extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener,
        CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemLongClickListener {

    private List<Planet> planetList;

    private CheckBox ck_diviver;
    private CheckBox ck_selector;
    private ListView lv_planet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act025);

        // 获取默认的行星列表，即水星、金星、地球、火星、木星、土星
        planetList = Planet.getDefaultList();
        // 构建一个行星列表的适配器
        PlanetBaseAdapter baseAdapter = new PlanetBaseAdapter(this, planetList);

        Spinner sp_planet = findViewById(R.id.dm025_sp_planet);
        sp_planet.setAdapter(baseAdapter);
        sp_planet.setSelection(0);
        sp_planet.setOnItemSelectedListener(this);




        // 列表视图 ListView
        planetList = Planet.getDefaultList();
        PlanetBaseAdapter baseAdapter2 = new PlanetBaseAdapter(this, planetList);

        lv_planet = findViewById(R.id.dm025_lv_planet);
        lv_planet.setAdapter(baseAdapter2);
        lv_planet.setOnItemClickListener(this);     // 设置列表视图的点击监听器
        lv_planet.setOnItemLongClickListener(this); // 设置列表视图的长按监听器

        ck_diviver = findViewById(R.id.dm025_ck_divider);
        ck_diviver.setOnCheckedChangeListener(this);
        ck_selector = findViewById(R.id.dm025_ck_selector);
        ck_selector.setOnCheckedChangeListener(this);


        Act025 that = this;
        // 跳转展示焦点抢占
        findViewById(R.id.dm025_tv_it).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(that, Act025_focus.class);
                startActivity(it);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "您选择的是" + planetList.get(position).name);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        ToastUtil.show(this, "您没有选择任何东西哦！");
    }

    // 处理列表项的点击事件, 由接口OnItemClickListener触发
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String desc = String.format("您点击了第%d个行星，它的名字是%s", position + 1,
                planetList.get(position).name);
        ToastUtil.show(this, desc);
    }

    // 处理列表项的长按事件, 由接口OnItemLongClickListener触发
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String desc = String.format("您长按了第%d个行星，它的名字是%s", position + 1,
                planetList.get(position).name);
        ToastUtil.show(this, desc);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.dm025_ck_divider:
                // 显示分隔线
                if (ck_diviver.isChecked()) {
                    // 从资源文件获得图形对象
                    Drawable drawable = getResources().getDrawable(R.color.red, getTheme());
                    lv_planet.setDivider(drawable);
                    // 设置列表视图的分隔线高度
                    lv_planet.setDividerHeight(ViewUtil.dip2px(this, 1));
                } else {
                    lv_planet.setDivider(null);
                    lv_planet.setDividerHeight(0);
                }
                break;

            case R.id.dm025_ck_selector:
                // 显示按压背景
                if (ck_selector.isChecked()) {
                    // 设置列表项的按压状态图形
                    lv_planet.setSelector(R.drawable.dm025_list_selector);
                } else {
                    Drawable drawable = getResources().getDrawable(R.color.transparent, getTheme());
                    lv_planet.setSelector(drawable);
                }
                break;
        }
    }

}