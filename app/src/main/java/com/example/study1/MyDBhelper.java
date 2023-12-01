package com.example.study1;
//格式化快捷键，ctrl+alt+l,自动把代码的数据对齐
//创造一个类继承自帮助类，通过这个数据库帮助类来创建数据库

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDBhelper extends SQLiteOpenHelper {//子承父类，名为mydb的构造函数继承自SQ类
    private SQLiteDatabase db;//声明SQ对象，之后就用这个db对象来操作SQ类中的方法就好了，用哪个对象都一样，因为特征一样才放在同一个类里的


    //创建数据库和表
    //重写构造器（函数），作用：定义数据库，参数的含义：上下文，数据文件（库）的名称（最重要），结果集工厂，版本号
    public MyDBhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "note.db", factory, 1);//这里是调用了父类的调用函数，子类需要调用父类的构造函数来初始化父类的实例。这样，子类就可以使用父类的功能，并添加或修改一些特定的功能
        db = this.getWritableDatabase();//调用当前MyDBhelper对象的getWritableDatabase()方法。
    }//获取一个可以进行读写操作的数据库，并将这个数据库对象赋值给db变量。这样，你就可以在后续的代码中使用db来执行数据库操作了。

    //重写创建方法，数据库初始化的时候用于创建表或视图文件
    @Override
    public void onCreate(SQLiteDatabase db) {//定义了onGreate方法，参数是数据库所对应的对象，用来操作数据库
        db.execSQL("create table noteInfo(id integer primary key autoincrement,content text,note_time text)");
    }



    //对note表的操作
    //添加数据，单独定义一个方法操作note表实现对数据的添加

    public Boolean insertData(String content) {//定义了一个操作数据的方法，并返回一个布尔值来表示操作是否成功
//格式化日期，把英文时间表达形式转换成中国的,设置日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());//获取系统的日期，但是是以外国的形式显示
        String time = simpleDateFormat.format(date);//格式化日期
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);//双引号为content：在oncreate方法中，内容的列名是content，在这个方法中列名必须与CREATE TABLE SQL语句定义的内容完全一致，不然insert方法无法1找到正确列
        contentValues.put("note_time", time);//系统的当前时间
        long i = db.insert("noteInfo", null, contentValues);
        if (i > 0) {
            return true;
        } else {
            return false;
        }

    }

    //删除数据，根据数据的id进行删除
        public boolean deleteData (String deleteId){
            int i = db.delete("noteInfo", "id=?", new String[]{deleteId});
            if (i > 0) {
                return true;
            } else {
                return false;
            }
        }
        //修改数据，根据记录的id进行更新
        public boolean updateData (String updateId, String updateContent){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
            Date date = new Date(System.currentTimeMillis());//获取系统的日期，但是是以外国的形式显示
            String time = simpleDateFormat.format(date);//格式化日期
            ContentValues contentValues=new ContentValues();
            contentValues.put("content", updateContent);
            contentValues.put("time", time);
            int i=db.update("noteInfo",contentValues,"id=?",new String[]{updateId});
            if (i > 0) {
                return true;
            } else {
                return false;
            }
        }

        //查询数据,查询表中的所有内容，将查询的内容用note对象属性进行存储，并将该对象存入集合中
    public List<Note> query(){
        List<Note> list=new ArrayList<>();
        Cursor cursor=db.query("noteInfo",null,null,null,null,null,null);
        while (cursor.moveToNext()){//这个循环的游标把记录从id等于1往下移，直到取完记录，即循环体的内容执行几次，产生了几个变量
            Note note=new Note();
            note.setId(String.valueOf(cursor.getInt(0)));//把取出来的整数强制转换成字符型,第一列
            note.setContent(cursor.getString(1));//取数据表的第二列
           note.setNote_time(cursor.getString(2));
           list.add(note);//每生成一个note对象（这个对象是包含id文本时间什么的），就会把这个对象存到list数组中
        }
        return  list;
    }

        //重写升级方法
        @Override
        public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){

        }

}
