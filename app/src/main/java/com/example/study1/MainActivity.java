package com.example.study1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // 声明延迟时间（以毫秒为单位）
    private static final long DELAY_TIME = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 延迟指定时间后执行页面跳转
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 创建隐式意图
                Intent intent = new Intent(MainActivity.this,MainActivity3.class);
                startActivity(intent);
                finish(); // 可选择在跳转后关闭当前Activity
            }
        }, DELAY_TIME);
    }

//    public void myClick(View view) {
//        //显示意图的使用方法实现页面的跳转
//        Intent intent=new Intent(MainActivity.this,MainActivity3.class);
//        this.startActivity(intent);
//    }

}
    
//关于Activity的生命周期：onCreate是界面的准备阶段
