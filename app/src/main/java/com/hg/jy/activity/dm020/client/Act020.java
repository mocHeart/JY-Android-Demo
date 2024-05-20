package com.hg.jy.activity.dm020.client;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;
import com.hg.jy.activity.dm017.enity.User;
import com.hg.jy.activity.dm020.server.provider.UserInfoContent;
import com.hg.jy.activity.utils.Constants;
import com.hg.jy.activity.utils.ToastUtil;
import com.hg.jy.activity.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("DefaultLocale")
public class Act020 extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;
    private TextView showStr;
    private LinearLayout ll_list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act020);

        et_name = findViewById(R.id.dm020_et_name);
        et_age = findViewById(R.id.dm020_et_age);
        et_height = findViewById(R.id.dm020_et_height);
        et_weight = findViewById(R.id.dm020_et_weight);
        ck_married = findViewById(R.id.dm020_ck_married);
        showStr = findViewById(R.id.dm020_showStr);
        ll_list = findViewById(R.id.dm020_ll_list);

        findViewById(R.id.dm020_btn_save).setOnClickListener(this);
        findViewById(R.id.dm020_btn_delete).setOnClickListener(this);
        findViewById(R.id.dm020_btn_read).setOnClickListener(this);
    }

    @SuppressLint({"NonConstantResourceId", "Range"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dm020_btn_save:
                ContentValues values = new ContentValues();
                values.put(UserInfoContent.USER_NAME, et_name.getText().toString());
                values.put(UserInfoContent.USER_AGE, Integer.parseInt(et_age.getText().toString()));
                values.put(UserInfoContent.USER_HEIGHT, Integer.parseInt(et_height.getText().toString()));
                values.put(UserInfoContent.USER_WEIGHT, Float.parseFloat(et_weight.getText().toString()));
                values.put(UserInfoContent.USER_MARRIED, ck_married.isChecked());
                getContentResolver().insert(UserInfoContent.CONTENT_URI, values);
                ToastUtil.show(this, "保存成功");
                Log.i(Constants.TAG, "保存成功");
                break;

            case R.id.dm020_btn_read:
                Cursor cursor = getContentResolver().query(UserInfoContent.CONTENT_URI, null, null, null, null);
                List<User> userList = new ArrayList<User>();
                if (cursor != null) {
                    // 循环取出游标指向的每条用户记录
                    while (cursor.moveToNext()) {
                        User info = new User();
                        info.id = cursor.getInt(cursor.getColumnIndex(UserInfoContent._ID));
                        info.name = cursor.getString(cursor.getColumnIndex(UserInfoContent.USER_NAME));
                        info.age = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_AGE));
                        info.height = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_HEIGHT));
                        info.weight = cursor.getFloat(cursor.getColumnIndex(UserInfoContent.USER_WEIGHT));
                        info.married = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_MARRIED)) == 1;
                        Log.d(Constants.TAG, info.toString());
                        userList.add(info);
                    }
                    cursor.close();  // 关闭数据库游标

                    String contactCount = String.format("当前共找到%d个用户", userList.size());
                    showStr.setText(contactCount);
                    ll_list.removeAllViews(); // 移除线性布局下面的所有下级视图
                    for (User user : userList) { // 遍历用户信息列表
                        String contactDesc = String.format("Id:%d, 姓名为%s，年龄为%d，身高为%d，体重为%f\n",
                                user.id, user.name, user.age, user.height, user.weight);
                        TextView tv_contact = new TextView(this); // 创建一个文本视图
                        tv_contact.setText(contactDesc);
                        tv_contact.setTextColor(Color.BLACK);
                        tv_contact.setTextSize(17);
                        int pad = ViewUtil.dip2px(this, 1);
                        tv_contact.setPadding(pad, pad, pad, pad); // 设置文本视图的内部间距
                        ll_list.addView(tv_contact); // 把文本视图添加至线性布局
                    }
                }
                break;

            case R.id.dm020_btn_delete:
                /*
                // 按ID删除的路径写法
                //content://com.hg.jy.activity.dm020.server.provider.UserInfoProvider/user/2
                Uri uri = ContentUris.withAppendedId(UserInfoContent.CONTENT_URI, 2);
                int count = getContentResolver().delete(uri, null, null);
                 */

                //content://com.hg.jy.activity.dm020.server.provider.UserInfoProvider/user
                int count = getContentResolver().delete(UserInfoContent.CONTENT_URI, "name=?", new String[]{"Jack"});
                if (count > 0) {
                    ToastUtil.show(this, "删除成功");
                    Log.i(Constants.TAG, "删除成功");
                }
                break;
        }
    }
}