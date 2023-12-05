package com.example.study1;//目录的功能文件

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity{

//    private String[] data ={"Day1","Day2","Day3","Day4","Day5","Day6","Day7","Day8","Day9","Day10"};

    //下面的代码要用到哪个控件，就先在前面声明一下相应的对象，类似的，可以通过调用这些对象来操作这些组件，对象名可以和id不同，id是用来找到控件所在处而已
    private ListView listView;
//    //SearchView searchView;////
//    Object[] names;
//    ArrayAdapter<String> adapter;
//    ArrayList<String> mAllList = new ArrayList<String>();

    private ImageView add,search;
    private  MyDBhelper myDBhelper;//查询的过程通过该类（多个方法完成）
    private MyAdapter myAdapter;//显示数据需要适配器
    private List<Note> resulList;

//    private SQLiteDatabase db;
//    private TextView showInfo=findViewById(R.id.showInfo);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listView = findViewById(R.id.listview);//开始初始化这些控件对象，即找到这个对象对应的控件，即对这个对象下个定义

//        initActionbar();
//        names = loadData();
//        listView = (ListView) findViewById(R.id.list);
//        listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, names));
//        listView.setTextFilterEnabled(true);////
//        searchView.setOnQueryTextListener(this);
//        searchView.setSubmitButtonEnabled(false);

        add = findViewById(R.id.add);
        //定义一个add的点击事件，格式是对象名字.方法名字，括号里的参数写的是新创建一个监听器对象，格式是new View.OnClickListener
        add.setOnClickListener(view -> {//当点击按钮时会触发这个监听器里的代码
            //点击添加按钮，跳转编辑的页面进行数据的添加
            Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
            //startActivity(intent);//这个的话只能简单的执行跳转页面
            //数据回传,跳转之后期望第二个页面回传数据
            startActivityForResult(intent, 1);
        });
        //执行一个init方法进行数据的初始化
        init();


        //设置列表下的点击监听器，对相应内容进行更新
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //当列表项被点击时，对该项的内容进行修改操作
                Note note= (Note) myAdapter.getItem(position);//通过po把要修改的记录找出来
                //创建意图，将用户选中的内容传递到修改的页面
                Intent intent=new Intent(MainActivity3.this,MainActivity4.class);
                //把原先笔记的值放在这个内容中
                String sendId=note.getId();
                String sendContent=note.getContent();
                String sendTime=note.getNote_time();
                intent.putExtra("id",sendId);
                intent.putExtra("content",sendContent);
                intent.putExtra("time",sendTime);
                startActivityForResult(intent,1);
            }
        });//一个新的监听器，监听用户点击目录下的哪一条item


        //列表项长按监听器，删除对应项的内容
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                //显示对话框删除
                AlertDialog dialog=null;
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity3.this);
                AlertDialog finalDialog = dialog;
                builder.setTitle("删除记录")
                        .setMessage("你确定要删除这条记录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//第二个参数是加个监听器
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //确定要删除的记录是哪一条
                                Note note= (Note) myAdapter.getItem(position);//把object强制转换为Note的子类对象
                                String deleteId=note.getId();
                                if( myDBhelper.deleteData(deleteId)){//调用删除的方法，只需要传入id就行，方法里定义的
                                    init();//刷新数据
                                    Toast.makeText(MainActivity3.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MainActivity3.this, "删除不成功！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();//让对话框消失
                            }
                        });
                dialog=builder.create();//产生对话框对象
                dialog.show();
                return true;
            }
        });

        search=findViewById(R.id.search);
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity3.this, SearchActivity5.class);
                startActivity(intent);
            }
        });



//        //初始化searchView
//        SearchView searchView = findViewById(R.id.searchView);
//        //加个监听器
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // This method is called when the user submits a query.
//                // Here you can implement the logic to filter your notes.
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // This method is called when the query text is changed by the user.
//                // Here you can implement the logic to filter your notes.
//                return false;
//            }
//        });



//        private void filterNotes (String query) {
//            if (query == null || resulList == null || myAdapter == null || listView == null) {
//                return;
//            }
//
//            List<Note> filteredNotes = new ArrayList<>();
//
//            for (Note note : resulList) {
//                String content = note.getContent();
//                if (content != null && content.toLowerCase().contains(query.toLowerCase())) {
//                    filteredNotes.add(note);
//                }
//            }
//
//            myAdapter = new MyAdapter(MainActivity3.this, filteredNotes);
//            listView.setAdapter(myAdapter);
//        }

    }

//    private Object[] loadData() {
//        mAllList.add("aa");
//        mAllList.add("ddfa");
//        mAllList.add("qw");
//        mAllList.add("sd");
//        mAllList.add("fd");
//        mAllList.add("cf");
//        mAllList.add("re");
//        return mAllList.toArray();
//    }
//
//    private void initActionbar() {
//        // 自定义标题栏
//        getActionBar().setDisplayShowHomeEnabled(false);
//        getActionBar().setDisplayShowTitleEnabled(false);
//        getActionBar().setDisplayShowCustomEnabled(true);
//        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View mTitleView = mInflater.inflate(R.layout.custom_action_bar_layout,
//                null);
//        getActionBar().setCustomView(mTitleView);//
//        searchView = (SearchView) mTitleView.findViewById(R.id.search_view);
//    }
//    @Override
//    public boolean onQueryTextChange(String newText){
//
//        if (TextUtils.isEmpty(newText)) {
//            // Clear the text filter.
//            listView.clearTextFilter();
//        } else {
//            // Sets the initial value for the text filter.
//            listView.setFilterText(newText.toString());
//        }
//        return false;
//    }
//    @Override
//    public boolean onQueryTextSubmit(String query){
//        //TODO Auto-generated method stub
//        return false;
//    }


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


//数据回传时自动执行的方法，用于接受回传过来的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            //说明数据的添加操作正常执行，数据库新增加了一条记录，主页中listview的内容就应该更新一下
            init();
        }
    }
}
