package com.example.study1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity3 extends AppCompatActivity {

    private String[] data ={"Day1","Day2","Day3","Day4","Day5","Day6","Day7","Day8","Day9","Day10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ArrayAdapter<String>adapter=new ArrayAdapter<String>(
        MainActivity3.this, android.R.layout.simple_list_item_1,data);
        @SuppressLint("MissingInflatedId") ListView listView=(ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

    }
//    @Override
//    protected  void onResume(){
//        super.onResume();
//        Object adapter;
//        listView.setAdapter(adapter);
//    }
}