package com.hg.jy.activity.sz002;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hg.jy.MyApplication;
import com.hg.jy.R;
import com.hg.jy.activity.sz002.database.ShoppingDBHelper;
import com.hg.jy.activity.sz002.entity.GoodsInfo;
import com.hg.jy.activity.utils.ToastUtil;

import java.util.List;

public class Asz002ShoppingChannel extends AppCompatActivity implements View.OnClickListener {

    // 声明一个商品数据库的帮助器对象
    private ShoppingDBHelper mDBHelper;
    private TextView tv_count;
    private GridLayout gl_channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asz002_shopping_channel);

        TextView tv_title = findViewById(R.id.sz002_tv_title);
        tv_title.setText("手机商场");

        tv_count = findViewById(R.id.sz002_tv_count);
        gl_channel = findViewById(R.id.sz002_gl_channel);
        findViewById(R.id.sz002_iv_back).setOnClickListener(this);
        findViewById(R.id.sz002_iv_cart).setOnClickListener(this);

        mDBHelper = ShoppingDBHelper.getInstance(this);

        // 从数据库查询出商品信息，并展示
        showGoods();
    }

    private void showGoods() {
        // 商品条目是一个线性布局，设置布局的宽度为屏幕的一半
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                screenWidth / 2,LinearLayout.LayoutParams.WRAP_CONTENT);
        // 查询商品数据库中的所有商品记录
        List<GoodsInfo> list = mDBHelper.queryAllGoodsInfo();

        // 移除下面的所有子视图
        gl_channel.removeAllViews();

        for (GoodsInfo info : list) {
            // 获取布局文件item_goods.xml的根视图
            View view = LayoutInflater.from(this).inflate(R.layout.sz002_item_goods, null);
            ImageView iv_thumb = view.findViewById(R.id.sz002_iv_thumb);
            TextView tv_name = view.findViewById(R.id.sz002_tv_name);
            TextView tv_price = view.findViewById(R.id.sz002_tv_price);
            Button btn_add = view.findViewById(R.id.sz002_btn_add);

            // 给控件设置值
            iv_thumb.setImageURI(Uri.parse(info.picPath));
            tv_name.setText(info.name);
            tv_price.setText(String.valueOf((int) info.price));

            // 添加到购物车
            btn_add.setOnClickListener(v -> {
                addToCart(info.id, info.name);
            });

            // 点击商品图片，跳转到商品详情页面
            iv_thumb.setOnClickListener(v -> {
                Intent intent = new Intent(Asz002ShoppingChannel.this, Asz002ShoppingDetail.class);
                intent.putExtra("goods_id", info.id);
                startActivity(intent);
            });

            // 把商品视图添加到网格布局
            gl_channel.addView(view, params);
        }
    }

    // 把指定编号的商品添加到购物车
    private void addToCart(int goodsId, String goodsName) {
        // 购物车商品数量+1
        int count = ++MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));
        mDBHelper.insertCartInfo(goodsId);
        ToastUtil.show(this, "已添加一部" + goodsName + "到购物车");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 查询购物车商品总数，并展示
        showCartInfoTotal();
    }

    // 查询购物车商品总数，并展示
    private void showCartInfoTotal() {
        int count = mDBHelper.countCartInfo();
        MyApplication.getInstance().goodsCount = count;
        tv_count.setText(String.valueOf(count));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sz002_iv_back:
                // 点击了返回图标，关闭当前页面
                finish();
                break;

            case R.id.sz002_iv_cart:
                // 点击了购物车图标
                // 从商场页面跳到购物车页面
                Intent intent = new Intent(this, Asz002.class);
                // 设置启动标志，避免多次返回同一页面的
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}