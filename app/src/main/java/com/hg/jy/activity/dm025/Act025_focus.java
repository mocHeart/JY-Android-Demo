package com.hg.jy.activity.dm025;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;
import com.hg.jy.activity.dm025.adapter.PlanetListWithButtonAdapter;
import com.hg.jy.activity.dm025.entity.Planet;
import com.hg.jy.activity.utils.ToastUtil;

import java.util.List;

public class Act025_focus extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<Planet> planetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act025_focus);


        ListView lv_planet = findViewById(R.id.dm025_2_lv_planet);
        planetList = Planet.getDefaultList();
        PlanetListWithButtonAdapter adapter = new PlanetListWithButtonAdapter(this, planetList);
        lv_planet.setAdapter(adapter);
        lv_planet.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "条目被点击了，" + planetList.get(position).name);
    }
}