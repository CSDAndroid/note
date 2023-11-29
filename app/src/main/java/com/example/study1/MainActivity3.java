package com.example.study1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

//    private String[] data ={"Day1","Day2","Day3","Day4","Day5","Day6","Day7","Day8","Day9","Day10"};

    private ListView listView;
    private ImageView add;
    private  MyDBhelper myDBhelper;//查询的过程通过该类（多个方法完成）
    private MyAdapter myAdapter;//显示数据需要适配器
    private List<Note> resulList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listView.findViewById(R.id.listview);//找到这个对象
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {//定义一个add的点击事件
            @Override
            public void onClick(View view) {
                //点击添加按钮，跳转编辑的页面进行数据的添加
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                //startActivity(intent);//这个的话只能简单的执行跳转页面
                //数据回传,跳转之后期望第二个页面回传数据
                startActivityForResult(intent, 1);
            }
        });
        //执行一个init方法进行数据的初始化
        init();
        //设置列表下的点击监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //当列表项被点击时，对该项的内容进行修改操作
            }
        });//一个新的监听器，监听用户点击目录下的哪一条item
    }
    //这个方法用来查询数据库的内容，将表中的数据显在listview上面
    private void init(){
        if(resulList!=null){
            resulList.clear();
        }//清空一下上次列表中的内容，然后再执行下面的在数据库查询的工作，再添加内容
        myDBhelper=new MyDBhelper(MainActivity3.this,"note.db",null,1);
        resulList=myDBhelper.query();//把表中的数据存在这了
        myAdapter=new MyAdapter(MainActivity3.this,resulList);
        listView.setAdapter(myAdapter);
    }//对方法的设计
}









//    @Override
//    protected  void onResume(){
//        super.onResume();
//        Object adapter;
//        listView.setAdapter(adapter);
//    }
//}

//        ArrayAdapter<String>adapter=new ArrayAdapter<String>(
//        MainActivity3.this, android.R.layout.simple_list_item_1,data);
//        @SuppressLint("MissingInflatedId") ListView listView=(ListView) findViewById(R.id.list_view);
//        listView.setAdapter(adapter);