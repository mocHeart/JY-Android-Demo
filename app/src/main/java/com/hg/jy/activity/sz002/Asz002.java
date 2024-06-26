package com.hg.jy.activity.sz002;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hg.jy.MyApplication;
import com.hg.jy.R;
import com.hg.jy.activity.sz002.database.ShoppingDBHelper;
import com.hg.jy.activity.sz002.entity.CartInfo;
import com.hg.jy.activity.sz002.entity.GoodsInfo;
import com.hg.jy.activity.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Asz002 extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_count;
    private LinearLayout ll_cart;
    private ShoppingDBHelper mDBHelper;

    // 声明一个购物车中的商品信息列表
    private List<CartInfo> mCartList;
    // 声明一个根据商品编号查找商品信息的映射，把商品信息缓存起来，这样不用每一次都去查询数据库
    private Map<Integer, GoodsInfo> mGoodsMap = new HashMap<>();
    private TextView tv_total_price;
    private LinearLayout ll_empty;
    private LinearLayout ll_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asz002);

        TextView tv_title = findViewById(R.id.sz002_tv_title);
        tv_title.setText("购物车");
        ll_cart = findViewById(R.id.sz002_ll_cart);
        tv_total_price = findViewById(R.id.sz002_tv_total_price);

        tv_count = findViewById(R.id.sz002_tv_count);
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));

        findViewById(R.id.sz002_iv_back).setOnClickListener(this);
        findViewById(R.id.sz002_btn_shopping_channel).setOnClickListener(this);
        findViewById(R.id.sz002_btn_clear).setOnClickListener(this);
        findViewById(R.id.sz002_btn_settle).setOnClickListener(this);
        ll_empty = findViewById(R.id.sz002_ll_empty);
        ll_content = findViewById(R.id.sz002_ll_content);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助器的实例
        mDBHelper = ShoppingDBHelper.getInstance(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // 展示购物车中的商品列表
    private void showCart() {
        // 显示购物车商品数量
        showCount();
        // 移除下面的所有子视图
        ll_cart.removeAllViews();
        // 查询购物车数据库中所有的商品记录
        mCartList = mDBHelper.queryAllCartInfo();
        if (mCartList.isEmpty()) {
            return;
        }

        for (CartInfo info : mCartList) {
            // 根据商品编号查询商品数据库中的商品记录
            GoodsInfo goods = mDBHelper.queryGoodsInfoById(info.goodsId);
            mGoodsMap.put(info.goodsId, goods);

            // 获取布局文件item_cart.xml的根视图
            View view = LayoutInflater.from(this).inflate(R.layout.sz002_item_cart, null);
            ImageView iv_thumb = view.findViewById(R.id.sz002_iv_thumb);
            TextView tv_name = view.findViewById(R.id.sz002_itv_name);
            TextView tv_desc = view.findViewById(R.id.sz002_itv_desc);
            TextView tv_count = view.findViewById(R.id.sz002_itv_count);
            TextView tv_price = view.findViewById(R.id.sz002_itv_price);
            TextView tv_sum = view.findViewById(R.id.sz002_itv_sum);

            iv_thumb.setImageURI(Uri.parse(goods.picPath));
            tv_name.setText(goods.name);
            tv_desc.setText(goods.description);
            tv_count.setText(String.valueOf(info.count));
            tv_price.setText(String.valueOf((int) goods.price));
            tv_sum.setText(String.valueOf((int) (info.count * goods.price))); // 设置商品总价

            // 给商品行添加长按事件：长按商品行就删除该商品
            view.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(Asz002.this);
                builder.setMessage("是否从购物车删除" + goods.name + "？");
                builder.setPositiveButton("是", (dialog, which) -> {
                    // 移除当前视图
                    ll_cart.removeView(v);
                    // 删除该商品
                    deleteGoods(info);
                });
                builder.setNegativeButton("否", null);
                builder.create().show();
                return true;
            });

            // 给商品行添加点击事件。点击商品行跳到商品的详情页
            view.setOnClickListener(v -> {
                Intent intent = new Intent(Asz002.this, Asz002ShoppingDetail.class);
                intent.putExtra("goods_id", goods.id);
                startActivity(intent);
            });

            // 往购物车列表添加该商品行
            ll_cart.addView(view);
        }

        // 重新计算购物车中的商品总金额
        refreshTotalPrice();
    }

    private void deleteGoods(CartInfo info) {
        MyApplication.getInstance().goodsCount -= info.count;
        // 从购物车的数据库中删除商品
        mDBHelper.deleteCartInfoByGoodsId(info.goodsId);
        // 从购物车的列表中删除商品
        CartInfo removed = null;
        for (CartInfo cartInfo : mCartList) {
            if (cartInfo.goodsId == info.goodsId) {
                removed = cartInfo;
                break;
            }
        }
        mCartList.remove(removed);
        // 显示最新的商品数量
        showCount();
        ToastUtil.show(this, "已从购物车删除" + mGoodsMap.get(info.goodsId).name);
        mGoodsMap.remove(info.goodsId);
        // 刷新购物车中所有商品的总金额
        refreshTotalPrice();
    }

    // 显示购物车图标中的商品数量
    private void showCount() {
        int count = mDBHelper.countCartInfo();
        MyApplication.getInstance().goodsCount = count;
        tv_count.setText(String.valueOf(count));
        // 购物车中没有商品，显示“空空如也”
        if (MyApplication.getInstance().goodsCount == 0) {
            ll_empty.setVisibility(View.VISIBLE);
            ll_content.setVisibility(View.GONE);
            ll_cart.removeAllViews();
        } else {
            ll_content.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.GONE);
        }
    }

    // 重新计算购物车中的商品总金额
    private void refreshTotalPrice() {
        int totalPrice = 0;
        for (CartInfo info : mCartList) {
            GoodsInfo goods = mGoodsMap.get(info.goodsId);
            totalPrice += (int) (goods.price * info.count);
        }
        tv_total_price.setText(String.valueOf(totalPrice));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sz002_iv_back:
                // 点击了返回图标
                // 关闭当前页面
                finish();
                break;

            case R.id.sz002_btn_shopping_channel:
                // 从购物车页面跳到商场页面
                Intent intent = new Intent(this, Asz002ShoppingChannel.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.sz002_btn_clear:
                // 清空购物车数据库
                mDBHelper.deleteAllCartInfo();
                MyApplication.getInstance().goodsCount = 0;
                // 显示最新的商品数量
                showCount();
                ToastUtil.show(this, "购物车已清空");
                break;

            case R.id.sz002_btn_settle:
                // 点击了“结算”按钮
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("结算商品");
                builder.setMessage("客官抱歉，支付功能尚未开通，请下次再来");
                builder.setPositiveButton("我知道了", null);
                builder.create().show();
                break;
        }
    }
}