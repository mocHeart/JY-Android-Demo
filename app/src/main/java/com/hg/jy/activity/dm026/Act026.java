package com.hg.jy.activity.dm026;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;
import com.hg.jy.activity.dm025.entity.Planet;
import com.hg.jy.activity.dm026.adapter.PlanetGridAdapter;
import com.hg.jy.activity.utils.ToastUtil;
import com.hg.jy.activity.utils.ViewUtil;

import java.util.List;

public class Act026 extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private final static String[] stretchMode = {
            "不显示分隔线",
            "显示水平和垂直分隔线",
            "显示四周分隔线",
            "不拉伸（NO_STRETCH）",
            "拉伸列宽(COLUMN_WIDTH)",
            "列间空隙(STRETCH_SPACING)",
            "左右空隙(SPACING_UNIFORM)",
    };
    private Spinner modeDropdown;

    private GridView gv_planet;
    private List<Planet> planetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act026);

        // 下拉列表选择拉伸模式
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<>(this, R.layout.act024_item_select, stretchMode);
        modeDropdown = findViewById(R.id.dm026_stretch_mode);
        modeDropdown.setAdapter(dropdownAdapter);
        modeDropdown.setSelection(0);
        modeDropdown.setOnItemSelectedListener(this);

        // 网格视图设置
        gv_planet = findViewById(R.id.dm026_gv_planet);
        planetList = Planet.getDefaultList();
        PlanetGridAdapter adapter = new PlanetGridAdapter(this, planetList);
        gv_planet.setAdapter(adapter);
        gv_planet.setOnItemClickListener(this);

        gv_planet.setBackgroundColor(Color.CYAN); // 设置背景颜色
        gv_planet.setHorizontalSpacing(ViewUtil.dip2px(this, 2)); // 设置列表项在水平方向的间距
        gv_planet.setVerticalSpacing(ViewUtil.dip2px(this, 2));   // 设置列表项在垂直方向的间距
        gv_planet.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);              // 设置拉伸模式
        gv_planet.setColumnWidth(ViewUtil.dip2px(this, 120)); // 设置每列宽度为120dp
        gv_planet.setPadding(0, 0, 0, 0);              // 设置网格视图的四周间距
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "您选择了：" + planetList.get(position).name);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                ToastUtil.show(this, "不显示分隔线");
                gv_planet.setBackgroundColor(Color.WHITE);
                gv_planet.setHorizontalSpacing(0);
                gv_planet.setVerticalSpacing(0);
                break;
            case 1:
                ToastUtil.show(this, "显示水平和垂直分隔线");
                gv_planet.setBackgroundColor(Color.RED);
                gv_planet.setHorizontalSpacing(ViewUtil.dip2px(this, 1));
                gv_planet.setVerticalSpacing(ViewUtil.dip2px(this, 2));
                break;
            case 2:
                ToastUtil.show(this, "显示四周分隔线");
                gv_planet.setBackgroundColor(Color.GREEN);
                int dividerPad = ViewUtil.dip2px(this, 5);
                gv_planet.setPadding(dividerPad, dividerPad, dividerPad, dividerPad);
                break;
            case 3:
                ToastUtil.show(this, "不拉伸（NO_STRETCH）");
                gv_planet.setStretchMode(GridView.NO_STRETCH);
                break;
            case 4:
                ToastUtil.show(this, "拉伸列宽(COLUMN_WIDTH)");
                gv_planet.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
                break;
            case 5:
                ToastUtil.show(this, "列间空隙(STRETCH_SPACING)");
                gv_planet.setStretchMode(GridView.STRETCH_SPACING);
                break;
            case 6:
                ToastUtil.show(this, "左右空隙(SPACING_UNIFORM)");
                gv_planet.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}