package com.hg.jy.activity.dm015;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;

import java.util.Calendar;

@SuppressLint("DefaultLocale")
public class Act015 extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Button alertBtn;
    private Button datePickBtn;
    private Button timePickBtn;
    private TextView alertTv;
    private TextView datePickTv;
    private TextView timePickTv;

    private DatePicker dp_date; // 声明一个日期选择器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act015);

        alertBtn = findViewById(R.id.dm015_alert_btn);
        datePickBtn = findViewById(R.id.dm015_date_btn);
        timePickBtn = findViewById(R.id.dm015_time_btn);
        alertBtn.setOnClickListener(this);
        datePickBtn.setOnClickListener(this);
        timePickBtn.setOnClickListener(this);

        alertTv = findViewById(R.id.dm015_alert_res);
        datePickTv = findViewById(R.id.dm015_date_res);
        timePickTv = findViewById(R.id.dm015_time_res);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dm015_alert_btn:
            // 创建提醒对话框的建造器
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // 设置对话框的标题文本
            builder.setTitle("尊敬的用户");
            // 设置对话框的内容文本
            builder.setMessage("你真的要卸载我吗？");

            // 设置对话框的肯定按钮文本及其点击监听器
            builder.setPositiveButton("残忍卸载", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertTv.setText("虽然依依不舍，但是只能离开了");
                }
            });

            // 设置对话框的否定按钮文本及其点击监听器
            builder.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertTv.setText("让我再陪你三百六十五个日夜");
                }
            });
            // 根据建造器构建提醒对话框对象
            AlertDialog alert = builder.create();
            // 显示提醒对话框
            alert.show();
            break;

            case R.id.dm015_date_btn:
                // 获取日历的一个实例，里面包含了当前的年月日
                Calendar calendar = Calendar.getInstance();
                // 构建一个日期对话框，该对话框已经集成了日期选择器。
                // DatePickerDialog的第二个构造参数指定了日期监听器
                DatePickerDialog dialog = new DatePickerDialog(this, this,
                        calendar.get(Calendar.YEAR),          // 年份
                        calendar.get(Calendar.MONTH),         // 月份
                        calendar.get(Calendar.DAY_OF_MONTH)); // 日子
                dialog.show(); // 显示日期对话框
                break;

            case R.id.dm015_time_btn:
                // 获取日历的一个实例，里面包含了当前的时分秒
                Calendar calendar2 = Calendar.getInstance();
                // 构建一个时间对话框，该对话框已经集成了时间选择器。
                // TimePickerDialog的第二个构造参数指定了时间监听器
                TimePickerDialog dialog2 = new TimePickerDialog(this, this,
                        calendar2.get(Calendar.HOUR_OF_DAY), // 小时
                        calendar2.get(Calendar.MINUTE),      // 分钟
                        true);   // true表示24小时制，false表示12小时制
                dialog2.show();  // 显示时间对话框
                break;
        }
    }

    // 一旦点击日期对话框上的确定按钮，就会触发监听器的onDateSet方法
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // 获取日期对话框设定的年月份
        String desc = String.format("您选择的日期是%d年%d月%d日", year, month + 1, dayOfMonth);
        datePickTv.setText(desc);
    }

    // 一旦点击时间对话框上的确定按钮，就会触发监听器的onTimeSet方法
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // 获取时间对话框设定的小时和分钟
        String desc = String.format("您选择的时间是%d时%d分", hourOfDay, minute);
        timePickTv.setText(desc);
    }
}