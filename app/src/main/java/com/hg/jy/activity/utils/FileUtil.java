package com.hg.jy.activity.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.hg.jy.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    // 把字符串保存到指定路径的文本文件
    public static void saveText(String path, String txt) {
        // 根据指定的文件路径构建文件输出流对象
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(txt.getBytes()); // 把字符串写入文件输出流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 从指定路径的文本文件中读取内容字符串
    public static String openText(String path) {
        String readStr = "";
        // 根据指定的文件路径构建文件输入流对象
        try (FileInputStream fis = new FileInputStream(path)) {
            byte[] b = new byte[fis.available()];
            // 从文件输入流读取字节数组
            fis.read(b);
            // 把字节数组转换为字符串
            readStr = new String(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readStr; // 返回文本文件中的文本字符串
    }

    // 把位图数据保存到指定路径的图片文件
    public static void saveImage(String path, Bitmap bitmap) {
        try (FileOutputStream fos = new FileOutputStream(path) ) {
            // 把位图数据压缩到文件输出流中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 从指定路径的图片文件中读取位图数据
    public static Bitmap openImage(String path) {
        Bitmap bitmap = null;
        try (FileInputStream fis = new FileInputStream(path)) {
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    // 检查文件是否存在，以及文件路径是否合法
    public static boolean checkFileUri(Context ctx, String path) {
        File file = new File(path);
        Log.d(Constants.TAG, "old path:" + path);
        if (!file.exists() || !file.isFile() || file.length() <= 0) {
            return false;
        }
        try {
            // 检测文件路径是否支持 FileProvider 访问方式，如果发生异常，说明不支持
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(ctx, ctx.getString(R.string.file_provider), file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
