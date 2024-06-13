package com.hg.jy.activity.dm027;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.hg.jy.R;
import com.hg.jy.activity.dm027.adapter.ImagePagerAdapter;
import com.hg.jy.activity.sz002.entity.GoodsInfo;
import com.hg.jy.activity.utils.ToastUtil;

import java.util.ArrayList;

public class Act027PagerTab extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ArrayList<GoodsInfo> mGoodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act027_pager_tab);

        initPagerStrip();
        initViewPager();
    }

    // 初始化翻页标签栏
    private void initPagerStrip() {
        PagerTabStrip pts_tab = findViewById(R.id.dm027_2_pts_tab);
        // 设置翻页标签栏的文本大小
        pts_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        pts_tab.setTextColor(Color.BLACK);
    }

    // 初始化翻页视图
    private void initViewPager() {
        ViewPager vp_content = findViewById(R.id.dm027_2_vp_content);
        mGoodsList = GoodsInfo.getDefaultList();
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, mGoodsList);
        vp_content.setAdapter(adapter);
        // 给翻页视图添加页面变更监听器
        vp_content.addOnPageChangeListener(this);
        vp_content.setCurrentItem(3);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        ToastUtil.show(this, "您翻到的手机品牌是：" + mGoodsList.get(position).name);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}