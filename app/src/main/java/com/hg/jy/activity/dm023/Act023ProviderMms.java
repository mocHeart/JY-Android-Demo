package com.hg.jy.activity.dm023;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.hg.jy.R;
import com.hg.jy.activity.utils.Constants;
import com.hg.jy.activity.utils.FileUtil;
import com.hg.jy.activity.utils.PermissionUtil;
import com.hg.jy.activity.utils.ToastUtil;
import com.hg.jy.activity.utils.ViewUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Act023ProviderMms extends AppCompatActivity {

    private static final String[] PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_REQUEST_CODE = 1;

    private final List<ImageInfo> mImageList = new ArrayList<>();
    private GridLayout gl_appendix;
    private EditText et_phone;
    private EditText et_title;
    private EditText et_message;

    //MediaStore
    String[] columns = new String[] {
            MediaStore.Images.Media._ID, // 编号
            MediaStore.Images.Media.TITLE, // 标题
            MediaStore.Images.Media.SIZE,// 文件大小
            MediaStore.Images.Media.DATA,// 文件路径
    };

    public Act023ProviderMms() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act023_provider_mms);

        et_phone = findViewById(R.id.dm023_2_et_phone);
        et_title = findViewById(R.id.dm023_2_et_title);
        et_message = findViewById(R.id.dm023_2_et_message);
        gl_appendix = findViewById(R.id.dm023_gl_appendix);

        //手动让MediaStore扫描入库
        MediaScannerConnection.scanFile(this, new String[]{ Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() },
                null, null);
        if (PermissionUtil.checkPermission(this, PERMISSIONS, PERMISSION_REQUEST_CODE)) {
            // 加载图片列表
            loadImageList();
            // 显示图像网格
            showImageGrid();
        }
    }

    // 加载图片列表
    @SuppressLint({"Range", "Recycle"})
    private void loadImageList() {
        // 清空图片列表
        mImageList.clear();
        // 图片大小在10MB以内(10*1024*1024)
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns,
                "_size < 10485760",
                null,
                "_size DESC"
        );
        int count = 0;
        if (cursor != null) {
            while (cursor.moveToNext() && count < 6) {
                ImageInfo image = new ImageInfo();
                image.id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                image.name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
                image.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                image.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                if (FileUtil.checkFileUri(this, image.path)) {
                    count++;
                    mImageList.add(image);
                }
                Log.d(Constants.TAG, "image:" + image);
            }
            cursor.close();
        }
    }

    // 显示图像网格
    private void showImageGrid() {
        gl_appendix.removeAllViews();
        for (ImageInfo image : mImageList) {
            // image -> ImageView
            ImageView iv_appendix = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeFile(image.path);
            iv_appendix.setImageBitmap(bitmap);
            // 设置图像视图的缩放类型
            iv_appendix.setScaleType(ImageView.ScaleType.FIT_CENTER);
            // 设置图像视图的布局参数
            int px = ViewUtil.dip2px(this, 110);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px, px);
            iv_appendix.setLayoutParams(params);
            // 设置图像视图的内部间距
            int padding = ViewUtil.dip2px(this, 5);
            iv_appendix.setPadding(padding, padding, padding, padding);
            iv_appendix.setOnClickListener(v -> {
                sendMms(et_phone.getText().toString(),
                        et_title.getText().toString(),
                        et_message.getText().toString(),
                        image.path);
            });
            // 把图像视图添加至网格布局
            gl_appendix.addView(iv_appendix);
        }
    }

    // 发送带图片的彩信
    private void sendMms(String phone, String title, String message, String path) {
        // 根据指定路径创建一个Uri对象
        Uri uri = Uri.parse(path);
        // 兼容Android7.0，把访问文件的Uri方式改为FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 通过FileProvider获得文件的Uri访问方式
            uri = FileProvider.getUriForFile(this, getString(R.string.file_provider), new File(path));
            Log.d(Constants.TAG, String.format("new uri:%s", uri.toString()));
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Intent 的接受者将被准许读取Intent 携带的URI数据
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("address", phone);     // 彩信发送的目标号码
        intent.putExtra("subject", title);     // 彩信的标题
        intent.putExtra("sms_body", message);  // 彩信的内容
        intent.putExtra(Intent.EXTRA_STREAM, uri);   // 彩信的图片附件
        intent.setType("image/*");                   // 彩信的附件为图片
        // 因为未指定要打开哪个页面，所以系统会在底部弹出选择窗口
        startActivity(intent);
        ToastUtil.show(this, "请在弹窗中选择短信或者信息应用");
    }

}