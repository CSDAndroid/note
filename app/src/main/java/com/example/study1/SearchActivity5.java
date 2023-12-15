package com.example.study1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

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

        back5=findViewById(R.id.back5);
        back5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity5.this, MainActivity3.class);
                startActivity(intent);
            }
        });

    }
}