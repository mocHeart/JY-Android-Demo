package com.hg.jy.activity.dm025.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hg.jy.R;
import com.hg.jy.activity.dm025.entity.Planet;
import com.hg.jy.activity.utils.ToastUtil;

import java.util.List;

@SuppressLint("InflateParams")
public class PlanetListWithButtonAdapter extends BaseAdapter {

    private Context mContext;
    private List<Planet> mPlaneList;

    public PlanetListWithButtonAdapter(Context mContext, List<Planet> mPlaneList) {
        this.mContext = mContext;
        this.mPlaneList = mPlaneList;
    }

    // 获取列表项的个数
    @Override
    public int getCount() {
        return mPlaneList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPlaneList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
        // 根据布局文件item_list.xml生成转换视图对象
        convertView = LayoutInflater.from(mContext).inflate(R.layout.act025_item_list_with_button, null);
        holder = new ViewHolder();
        holder.ll_item = convertView.findViewById(R.id.dm025_f_ll_item);
        holder.iv_icon = convertView.findViewById(R.id.dm025_f_iv_icon);
        holder.tv_name = convertView.findViewById(R.id.dm025_f_tv_name);
        holder.tv_desc = convertView.findViewById(R.id.dm025_f_tv_desc);
        holder.btn_open = convertView.findViewById(R.id.dm025_f_btn_open);
        // 将视图持有者保存到转换视图当中
        convertView.setTag(holder);
    } else {
        holder = (ViewHolder) convertView.getTag();
    }

    // 给控制设置好数据
    Planet planet = mPlaneList.get(position);
    holder.ll_item.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    holder.iv_icon.setImageResource(planet.image);
    holder.tv_name.setText(planet.name);
    holder.tv_desc.setText(planet.desc);
    holder.btn_open.setOnClickListener(v -> {
        ToastUtil.show(mContext, "孩子按钮被点击了，" + planet.name);
    });
    return convertView;
}

    public static final class ViewHolder {
        public LinearLayout ll_item;
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_desc;
        public Button btn_open;
    }
}
