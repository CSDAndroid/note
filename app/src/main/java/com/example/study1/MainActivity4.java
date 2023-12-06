package com.example.study1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity4 extends AppCompatActivity implements View.OnClickListener {
    private ImageView delete,saveNote;
    private Button backHome;
    private TextView showTime;
    private EditText et_Content;
    private MyDBhelper myDBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        init();
        //为对象设计监听器，类似于判断用户有没有使用哪些控件
        //用接口的方式来设计监听器
        backHome.setOnClickListener(this);
        delete.setOnClickListener(this);//都增加这个监听器
        saveNote.setOnClickListener(this);

        // 获取Intent中传递的内容
        String contentFromIntent = getIntent().getStringExtra("content");
        if (contentFromIntent != null) {
            et_Content.setText(contentFromIntent);
            init();
        }
    }
    //获取控件对象
    private void init(){
        backHome=findViewById(R.id.backhome);
        delete=findViewById(R.id.delete);
        saveNote=findViewById(R.id.save_note);
        //backHome=findViewById(R.id.backhome);
        showTime=findViewById(R.id.showTime);
        et_Content=findViewById(R.id.content);
    }

//实现onClick这个接口
    @Override
    public void onClick(View v) {
        int id = v.getId();///

        if(id==R.id.delete){//返回上一页没必要做一个意图的跳转，只需要把当前界面销毁即可，把要返回的那个界面变成栈顶
            et_Content.setText("");//执行编辑框的内容设为空
        }

        if(id==R.id.backhome){//返回上一页没必要做一个意图的跳转，只需要把当前界面销毁即可，把要返回的那个界面变成栈顶
           //关闭当前的活动页面
            finish();
        }
        if (id == R.id.save_note){
            // 获取编辑文本的内容
            String content=et_Content.getText().toString();//获取文本编辑框（EditText）中的内容，并将其转换为字符串
            if(content==null){

                Toast.makeText(MainActivity4.this,"内容不能为空！！",Toast.LENGTH_SHORT).show();//Toast在编辑页面弹出来
            }else{
                // 数据的添加或更新，调用MyDBhelper的方法，首先要有对象
                myDBhelper=new MyDBhelper(MainActivity4.this,"note.db",null,1);

                /////判断当前页面是否是编辑已有笔记的页面，即通过判断数据库中是否有此id的方法来判断
                String noteId =getIntent().getStringExtra("id");/////注意双引号里面的内容必须与前面传数据来的时候的键名一致，不要写成noteId！在mainactivity3中用了putExtra的方法传递值来这个页面，在这个页面中可以通过键名“id”来获取传过来的数据
            if(noteId!=null){/////注意使用这个if的判断条件
                // 如果是，则更新原先笔记的内容
                Boolean flag = myDBhelper.updateData(noteId, content);/////调用的是更新方法，和插入insert的方法不一样
                if (flag == true) {
                    setResult(2);
                    Toast.makeText(MainActivity4.this, "修改成功！！", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(MainActivity4.this, "修改失败！！", Toast.LENGTH_SHORT).show();
                }
            }else {
                //如果不是，则新增一条笔记
                Boolean flag = myDBhelper.insertData(content);
                if (flag == true) {
                    //如果添加成功，将数据回传的结果码设置为2
                    setResult(2);
                    Toast.makeText(MainActivity4.this, "添加成功！！", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity4.this, "添加失败！！", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
}
}

//
//不能用switch，代码中出现 "Constant expression required" 错误，是因为在 Android 中，库项目中的资源标识符不是真正的常量，因此不能在 switch 语句中使用。
// 这是因为从 Android Gradle Plugin 8.0.0 开始，//资源（如 R.id. ）不再被声明为 final（即常量表达式），这是为了优化构建速度，这是在 switch 语句中使用的先决条件5。
