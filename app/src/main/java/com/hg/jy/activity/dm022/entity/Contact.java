package com.hg.jy.activity.dm022.entity;

import androidx.annotation.NonNull;

public class Contact {
    public String name; // 联系人姓名
    public String phone; // 联系号码
    public String email; // 邮箱

    @NonNull
    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
