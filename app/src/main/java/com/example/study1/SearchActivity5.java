package com.example.study1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

        // 设置搜索文本监听
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
                    adapter.refreshList(notelist); //// 搜索关键字为空，恢复原始数据源;根据日志：判断空字符成功了，问题是没有把原先列表适配器套在mlistview上，
                    Log.d("adapter", "restoreOriginal is succeed!!!!!!!!!!"+"-----=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                }else{
                //adapter.getFilter().isOriginalListRestored = TextUtils.isEmpty(newText); // 设置是否恢复原始数据源的标记
                    adapter.getFilter().filter(newText);//过滤器框架会自动调用performFiltering()方法，
                    // 并在后台线程中执行过滤操作，并将过滤后的结果保存在FilterResults中。
                }
                return false;
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
}