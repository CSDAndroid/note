package com.example.study1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class SearchActivity5 extends AppCompatActivity {

   // private String[] mStrs = {"aaa", "bbb", "ccc", "airsaid","朱嫚妃","朱满飞","猪满飞","飞满猪","哈哈哈"};
    private SearchView mSearchView;
    private ListView mListView;
    private MyDBhelper myDBhelper;
    private List<Note> resulList;//
    //private List<Note> mOriginalValues;
    private ImageView back5;
    //private MyAdapter myAdapter;//显示数据需要适配器

    /////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search5);

        mSearchView = findViewById(R.id.searchView);//这里找的组件必须在setContentView那个文件含有
        mListView = findViewById(R.id.listView);
        myDBhelper=new MyDBhelper(SearchActivity5.this,"note.db",null,4);
        resulList=myDBhelper.query();
        //验证数据源是否正确
        Log.d("Data Source", resulList.toString()+"-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        MyAdapter adapter = new MyAdapter(this, resulList);
        mListView.setAdapter(adapter);
        mListView.setTextFilterEnabled(true);

        // 设置搜索文本监听，给列表设置点击的事件
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            // 当搜索内容改变时触发该方法
            ////在 onQueryTextChange() 方法中，当搜索关键字为空时，调用该方法恢复数据源。
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("TextUtils", newText.toString()+"-------------=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                if (TextUtils.isEmpty(newText)){
                    List<Note> notelist =myDBhelper.query();
                    adapter.refreshList(notelist);   //// 搜索关键字为空，恢复原始数据源;根据日志：判断空字符成功了，问题是没有把原先列表适配器套在mlistview上，
                    mListView.setAdapter(adapter); /////将新的适配器 adapter 设置给 mListView
                    Log.d("adapter", "restoreOriginal is succeed!!!!!!!!!!"+"-----=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                }else{
                //adapter.getFilter().isOriginalListRestored = TextUtils.isEmpty(newText); // 设置是否恢复原始数据源的标记
                    adapter.getFilter().filter(newText);//过滤器框架会自动调用performFiltering()方法，
                    // 并在后台线程中执行过滤操作，并将过滤后的结果保存在FilterResults中。
                }
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //当列表项被点击时，对该项的内容进行修改操作
                Note note = (Note) adapter.getItem(position);//通过po把要修改的记录找出来
                //创建意图，将用户选中的内容传递到修改的页面
                Intent intent = new Intent(SearchActivity5.this, MainActivity4.class);
                //把原先笔记的值放在这个内容中
                String sendId = note.getId();
                String sendContent = note.getContent();
                String sendTime = note.getNote_time();
                String sendImage = note.getImageUri();
                intent.putExtra("id", sendId);
                intent.putExtra("content", sendContent);/////打包数据
                intent.putExtra("time", sendTime);
                intent.putExtra("image_uri", sendImage);
                startActivityForResult(intent, 1);
            }
        });//一个新的监听器，监听用户点击目录下的哪一条item

        //列表项长按监听器，删除对应项的内容
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                //显示对话框删除
                AlertDialog dialog = null;
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity5.this);
                AlertDialog finalDialog = dialog;
                builder.setTitle("删除记录")
                        .setMessage("你确定要删除这条记录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//第二个参数是加个监听器
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //确定要删除的记录是哪一条
                                Note note = (Note) adapter.getItem(position);//把object强制转换为Note的子类对象
                                String deleteId = note.getId();
                                if (myDBhelper.deleteData(deleteId)) {//调用删除的方法，只需要传入id就行，方法里定义的
                                    minit();//刷新数据
                                    Toast.makeText(SearchActivity5.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SearchActivity5.this, "删除不成功！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();//让对话框消失
                            }
                        });
                dialog = builder.create();//产生对话框对象
                dialog.show();
                return true;
            }
        });


        back5=findViewById(R.id.back5);
        back5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity5.this, MainActivity3.class);
                startActivity(intent);
            }
        });

    }

    private void minit() {
        if(resulList!=null){
            resulList.clear();
        }//清空一下上次列表中的内容，然后再执行下面的在数据库查询的工作，再添加内容
        myDBhelper=new MyDBhelper(SearchActivity5.this,"note.db",null,4);
        resulList=myDBhelper.query();//把表中的数据存在这了
        MyAdapter adapter=new MyAdapter(SearchActivity5.this,resulList);
        mListView.setAdapter(adapter);
    }
}