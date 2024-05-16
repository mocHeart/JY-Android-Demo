package com.hg.jy.activity.dm019;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hg.jy.MyApplication;
import com.hg.jy.R;
import com.hg.jy.activity.dm019.dao.BookDao;
import com.hg.jy.activity.dm019.entity.BookInfo;
import com.hg.jy.activity.utils.ToastUtil;

import java.util.List;

public class Act19Room extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_author;
    private EditText et_press;
    private EditText et_price;
    private TextView tv_show;
    private BookDao bookDao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act19_room);

        et_name = findViewById(R.id.dm019_2_et_name);
        et_author = findViewById(R.id.dm019_2_et_author);
        et_press = findViewById(R.id.dm019_2_et_press);
        et_price = findViewById(R.id.dm019_2_et_price);
        tv_show = findViewById(R.id.dm019_2_tv_str);

        findViewById(R.id.dm019_2_btn_save).setOnClickListener(this);
        findViewById(R.id.dm019_2_btn_delete).setOnClickListener(this);
        findViewById(R.id.dm019_2_btn_update).setOnClickListener(this);
        findViewById(R.id.dm019_2_btn_query).setOnClickListener(this);

        // 从App实例中获取唯一的书籍持久化对象
        bookDao = MyApplication.getInstance().getBookDB().bookDao();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String author = et_author.getText().toString();
        String press = et_press.getText().toString();
        String price = et_price.getText().toString();

        switch (v.getId()) {
            case R.id.dm019_2_btn_save:
                // 以下声明一个书籍信息对象，并填写它的各字段值
                BookInfo b1 = new BookInfo();
                b1.setName(name);
                b1.setAuthor(author);
                b1.setPress(press);
                b1.setPrice(Double.parseDouble(price));
                bookDao.insert(b1);
                ToastUtil.show(this, "保存成功");
                break;
            case R.id.dm019_2_btn_delete:
                BookInfo b2 = new BookInfo();
                b2.setId(1);
                bookDao.delete(b2);
                break;
            case R.id.dm019_2_btn_update:
                BookInfo b3 = new BookInfo();
                // 根据名字查询到数据库中已有的记录
                BookInfo b4 = bookDao.queryByName(name);
                b3.setId(b4.getId());
                b3.setName(name);
                b3.setAuthor(author);
                b3.setPress(press);
                b3.setPrice(Double.parseDouble(price));
                bookDao.update(b3);
                break;
            case R.id.dm019_2_btn_query:
                List<BookInfo> list = bookDao.queryAll();
                StringBuilder sb = new StringBuilder();
                for (BookInfo b : list) {
                    Log.d("ning", b.toString());
                    sb.append(b).append("\n");
                }
                tv_show.setText(sb.toString());
                break;
        }
    }
}