package com.hg.jy.activity.sz002;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.MyApplication;
import com.hg.jy.R;
import com.hg.jy.activity.sz002.database.ShoppingDBHelper;
import com.hg.jy.activity.sz002.entity.GoodsInfo;
import com.hg.jy.activity.utils.Constants;
import com.hg.jy.activity.utils.ToastUtil;

public class Asz002ShoppingDetail extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private TextView tv_count;
    private TextView tv_goods_price;
    private TextView tv_goods_desc;
    private ImageView iv_goods_pic;
    private ShoppingDBHelper mDBHelper;
    private int mGoodsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asz002_shopping_detail);

        // 获得数据库帮助器的实例
        mDBHelper = ShoppingDBHelper.getInstance(this);

        tv_title = findViewById(R.id.sz002_tv_title);
        tv_count = findViewById(R.id.sz002_tv_count);
        tv_goods_price = findViewById(R.id.sz002_tv_goods_price);
        tv_goods_desc = findViewById(R.id.sz002_tv_goods_desc);
        iv_goods_pic = findViewById(R.id.sz002_iv_goods_pic);
        findViewById(R.id.sz002_iv_back).setOnClickListener(this);
        findViewById(R.id.sz002_iv_cart).setOnClickListener(this);
        findViewById(R.id.sz002_btn_add_cart).setOnClickListener(this);

        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDetail();
    }

    private void showDetail() {
        // 获取上一个页面传来的商品编号
        mGoodsId = getIntent().getIntExtra("goods_id", 0);
        if (mGoodsId > 0) {
            // 根据商品编号查询商品数据库中的商品记录
            GoodsInfo info = mDBHelper.queryGoodsInfoById(mGoodsId);
            tv_title.setText(info.name);
            tv_goods_desc.setText(info.description);
            tv_goods_price.setText(String.valueOf((int) info.price));
            iv_goods_pic.setImageURI(Uri.parse(info.picPath));
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sz002_iv_back:
                finish();
                break;
            case R.id.sz002_iv_cart:
                Intent intent = new Intent(this, Asz002.class);
                startActivity(intent);
                break;
            case R.id.sz002_btn_add_cart:
                addToCart(mGoodsId);
                break;
        }
    }

    private void addToCart(int goodsId) {
        // 购物车商品数量+1
        MyApplication.getInstance().goodsCount += 1;
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        mDBHelper.insertCartInfo(goodsId);
        ToastUtil.show(this, "成功添加至购物车");
    }
}