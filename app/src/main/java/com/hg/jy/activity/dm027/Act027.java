package com.hg.jy.activity.dm027;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.hg.jy.R;
import com.hg.jy.activity.dm027.adapter.ImagePagerAdapter;
import com.hg.jy.activity.sz002.entity.GoodsInfo;
import com.hg.jy.activity.utils.Constants;
import com.hg.jy.activity.utils.ToastUtil;

import java.util.ArrayList;

@SuppressLint("NonConstantResourceId")
public class Act027 extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ArrayList<GoodsInfo> mGoodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act027);
        findViewById(R.id.dm027_toPagerTabStrip).setOnClickListener(this);
        findViewById(R.id.dm027_toLaunchSimple).setOnClickListener(this);

        ViewPager vp_content = findViewById(R.id.dm027_vp_content);
        mGoodsList = GoodsInfo.getDefaultList();
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, mGoodsList);
        vp_content.setAdapter(adapter);
        // 设置翻页视图显示第一页
        vp_content.setCurrentItem(0);
        // 给翻页视图添加页面变更监听器
        //vp_content.addOnPageChangeListener(this);
        vp_content.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ToastUtil.show(Act027.this, "您翻到的手机品牌是：" + mGoodsList.get(position).name);
            }
        });
    }

    // 翻页状态改变时触发
    // state取值说明为：0表示静止，1表示正在滑动，2表示滑动完毕
    // 在翻页过程中，状态值变化依次为：正在滑动→滑动完毕→静止
    @Override
    public void onPageScrollStateChanged(int state) {
        Log.i(Constants.TAG, "state: " + state);
    }

    // 在翻页过程中触发
    // 该方法的三个参数取值说明为 ：
    //   第一个参数表示当前页面的序号
    //   第二个参数表示页面偏移的百分比，取值为0到1
    //   第三个参数表示页面的偏移距离
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.i(Constants.TAG, "position:" + position + " positionOffset:"
                + positionOffset + " positionOffsetPixels:" + positionOffsetPixels);
    }

    // 在翻页结束后触发
    // position表示当前滑到了哪一个页面
    @Override
    public void onPageSelected(int position) {
        ToastUtil.show(this, "您翻到的手机品牌是：" + mGoodsList.get(position).name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dm027_toPagerTabStrip:
                Intent intent = new Intent(this, Act027PagerTab.class);
                startActivity(intent);
                break;
            case R.id.dm027_toLaunchSimple:
                Intent intent2 = new Intent(this, Act027LaunchSimple.class);
                startActivity(intent2);
                break;
        }
    }
}