package com.example.study1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void myClick(View view) {
        //显示意图的使用方法实现页面的跳转
        Intent intent=new Intent(MainActivity.this,MainActivity3.class);
        this.startActivity(intent);
    }

}
    
//关于Activity的生命周期：onCreate是界面的准备阶段
//button1.setOnClickListener(new View. OnClickListener() {
//@Override
//public void onClick(View v) {
//        Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
//        startActivity (intent);
//        }
//        });