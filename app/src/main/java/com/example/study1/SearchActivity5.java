package com.example.study1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
    private List<Note> resulList;
    private ImageView back5;

    /////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search5);
        mSearchView = (SearchView) findViewById(R.id.searchView);//这里找的组件必须在setContentView那个文件含有
        mListView = (ListView) findViewById(R.id.listView);
        myDBhelper=new MyDBhelper(SearchActivity5.this,"note.db",null,1);
        resulList=myDBhelper.query();
        mListView.setAdapter(new MyAdapter(this,resulList));
        mListView.setTextFilterEnabled(true);

        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    mListView.setFilterText(newText);
                }else{
                    mListView.clearTextFilter();
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