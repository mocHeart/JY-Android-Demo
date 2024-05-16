package com.hg.jy.activity.dm018;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;
import com.hg.jy.activity.utils.FileUtil;
import com.hg.jy.activity.utils.ToastUtil;

import java.io.File;

@SuppressLint("MissingInflatedId")
public class Act018 extends AppCompatActivity implements View.OnClickListener {

    private TextView tvStr;

    private EditText et_name;
    private EditText et_age;
    private EditText et_height;
    private EditText et_weight;
    private CheckBox ck_married;
    private String path1;
    private String path2;
    private String path3;
    private String path4;
    private TextView tv_txt;
    private ImageView iv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act018);

        tvStr =  findViewById(R.id.dm018_show_str);
        findViewById(R.id.dm018_get_dir).setOnClickListener(this);


        et_name = findViewById(R.id.dm018_et_name);
        et_age = findViewById(R.id.dm018_et_age);
        et_height = findViewById(R.id.dm018_et_height);
        et_weight = findViewById(R.id.dm018_et_weight);
        ck_married = findViewById(R.id.dm018_ck_married);
        tv_txt = findViewById(R.id.tv_txt);
        findViewById(R.id.dm018_btn_save).setOnClickListener(this);
        findViewById(R.id.dm018_btn_read).setOnClickListener(this);

        findViewById(R.id.dm018_btn_save_img).setOnClickListener(this);
        findViewById(R.id.dm018_btn_read_img).setOnClickListener(this);
        iv_content = findViewById(R.id.dm018_iv_content_img);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dm018_get_dir:
                // 获取系统的公共存储路径
                String publicPath = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS).toString();
                // 获取当前App的私有存储路径
                String privatePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();

                boolean isLegacy = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // Android10的存储空间默认采取分区方式，此处判断是传统方式还是分区方式
                    isLegacy = Environment.isExternalStorageLegacy();
                }
                String desc = "系统的公共存储路径位于：\n" + publicPath +
                        "\n\n当前App的私有存储路径位于：\n" + privatePath +
                        "\n\nAndroid7.0之后默认禁止访问公共存储目录" +
                        "\n当前App的存储空间采取：\n" + (isLegacy ? "传统方式" : "分区方式");
                tvStr.setText(desc);
                break;

            case R.id.dm018_btn_save:
                String name = et_name.getText().toString();
                String age = et_age.getText().toString();
                String height = et_height.getText().toString();
                String weight = et_weight.getText().toString();

                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                sb.append("姓名:").append(name);
                sb.append("\n年龄:").append(age);
                sb.append("\n身高:").append(height);
                sb.append("\n体重:").append(weight);
                sb.append("\n婚否:").append(ck_married.isChecked() ? "是" : "否");

                String fileName = "User" + System.currentTimeMillis() + ".txt";
                String directory = null;
                // 外部存储的私有空间
                directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
                path1 = directory + File.separatorChar + fileName;
                Log.d("ning", path1);
                FileUtil.saveText(path1, sb.toString());
                ToastUtil.show(this, "保存在【外部存储的私有空间】成功");
                sb2.append("\n外部存储的私有空间: ").append(path1).append("\n");

                // 外部存储的公共空间
                directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                path2 = directory + File.separatorChar + fileName;
                Log.d("ning", path2);
                FileUtil.saveText(path2, sb.toString());
                ToastUtil.show(this, "保存在【外部存储的公共空间】成功");
                sb2.append("\n外部存储的公共空间: ").append(path2).append("\n");

                // 内部存储私有空间
                directory = getFilesDir().toString();
                path3 = directory + File.separatorChar + fileName;
                Log.d("ning", path3);
                FileUtil.saveText(path3, sb.toString());
                ToastUtil.show(this, "保存在【内部存储私有空间】成功");
                sb2.append("\n内部存储私有空间: ").append(path3).append("\n");
                tv_txt.setText(sb2.toString());
                break;
            case R.id.dm018_btn_read:
                String sb3 = FileUtil.openText(path1) + "\n" +
                        FileUtil.openText(path2) + "\n" +
                        FileUtil.openText(path3);
                tv_txt.setText(sb3);
                break;

            case R.id.dm018_btn_save_img:
                String fileName2 = System.currentTimeMillis() + ".jpeg";
                // 获取当前App的私有下载目录
                path4 = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar + fileName2;
                Log.d("ning", path4);
                // 从指定的资源文件中获取位图对象
                Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.ting2);
                // 把位图对象保存为图片文件
                FileUtil.saveImage(path4, b1);
                ToastUtil.show(this, "保存成功");
                break;
            case R.id.dm018_btn_read_img:
                //Bitmap b2 = FileUtil.openImage(path);
                //iv_content.setImageBitmap(b2);

                //Bitmap b2 = BitmapFactory.decodeFile(path);
                //iv_content.setImageBitmap(b2);

                // 直接调用setImageURI方法，设置图像视图的路径对象
                iv_content.setImageURI(Uri.parse(path4));
                break;
        }
    }
}