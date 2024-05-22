package com.hg.jy.activity.dm023;

import androidx.annotation.NonNull;

public class ImageInfo {
    public long id;       // 图片编号
    public String name;   // 图片标题
    public long size;     // 文件大小
    public String path;   // 文件路径

    @NonNull
    @Override
    public String toString() {
        return "ImageInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", path='" + path + '\'' +
                '}';
    }
}
