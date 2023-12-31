package com.example.study1;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//数据适配器，用来给listview加载数据用的,然后又创建了四个方法,而且适配器中提供的数据应该来自数据库，取到数据才能用上这个适配器2
public class MyAdapter extends BaseAdapter implements Filterable {
    //使用list<Note>,list集合会存储数据库中note表的所有记录.....适配器的数据来自于list
    //此行声明一个 List<Note> 类型的私有实例变量 list 。该列表将存储适配器将在 ListView 中显示的数据
    private List<Note> list;//这个集合用来存数据表中的所有记录，如n1，n2......
    //layoutInflater用于将某个布局转化为View对象，类似于简化打包
    private LayoutInflater layoutInflater;
    private List<Note> mOriginalValues;
    private List<Note> mDisplayedValues;//mNoteList
    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();
    //当创建MyAdapter适配器对象时，我们需要先准备好list的数据，为了拿到list的数据，可以为MyAdapter这个类加上一个构造器
    //使得创造适配器对象之前，list先初始化好，下面就可以直接用数据了
    public  MyAdapter(Context context,List<Note> list){   //两个参数，第一个是存了数据库中所有记录内容的list，第二个是传入上下文，即在哪个界面上调用的适配器，把activity传来
        layoutInflater=layoutInflater.from(context);   //从哪个界面（content）中拿到布局对象
        this.list=list;
        this.mOriginalValues = list;
        this.mDisplayedValues = list;
        mInflater = layoutInflater.from(context);
    }//这是MyAdapter类的构造函数

    @Override
    public int getCount() {
        return mDisplayedValues.size();   //返回记录的总条数，即list集合中的元素个数
    }
//获取item数量
    @Override//position指的是集合list里的元素下标，从0开始，然后适配器自动执行后，position就执行下一个item的内容，即pst=1.。。
    public Object getItem(int position) {//返回一个Object
        return list.get(position);//点击哪项就获取该项的记录内容即获取Note对象，Note对象对应表中某条记录，get是用来获取集合list中某一个元素的方法
    }
//position记录当前的item序号，从0开始，该方法获取某个item对象（例如用户点击哪条就返回哪条的）
    @Override
    public long getItemId(int position) {
        return position;
    }
//获取item项的编号
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.itemlayout,null,false);//把itemlayout布局转换成了一个视图对象converView,以便在Activity中使用
            viewHolder=new ViewHolder(convertView);//对应下面那个优化方案里面的内容，对应下面那个构造函数，把这个itemlayout布局传进去，在view那个位置，即在itemlayout中找那两个视图对象
            convertView.setTag(viewHolder);//用setTag方法把这个数据内容加载给这个视图对象
        }else {
            viewHolder= (ViewHolder) convertView.getTag();//又用gettag方法获取加载的内容并存好
        }

        //将数据库中的内容加载到对应的控件上
        Note note= (Note) getItem(position);//这个方法是上面写的，返回的就是一个note对象（某条记录），把这条记录存的内容给note，就那三个图的过程
        viewHolder.t_content.setText(note.getContent());//取这条记录里的t_content属性的值放在适配器的t_content上
        viewHolder.t_time.setText(note.getNote_time());
        return convertView;
    }   //用于配置listview要加载的内容，包括视图和数据项
    //将item.xml文件找出来并转换成View对象，就是在这里定义一个变量然后把找到的view用那个变量储存起来

    //listview的优化方案，防止向下滑动的内容无上限，让手机死机
    class ViewHolder{//设计一个类，用于给item的视图加载数据内容,即找视图对象
        TextView t_content,t_time;//定义两个变量，以便于之后修改相应布局里的内容
        public ViewHolder(View view){//传入一个view对象
            t_content=view.findViewById(R.id.item_content);//把对应的视图赋给对象，把找这两个对象的过程封装在这个class类当中
            t_time=view.findViewById(R.id.item_time);
        }
    }

    // 实现 Filterable 接口的方法
    @Override
    public Filter getFilter() {
        //验证过滤器是否被调用
        Log.d("Filter", "getFilter() called"+"-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-");
        return mFilter;
    }


    //刷新列表的方法
    //// 恢复原始数据源的方法，解决只能搜索一次的问题的
    //用于恢复适配器的数据源为原始数据源
    public void refreshList(List<Note> newList) {  /////注意在使用这个方法的时候括号里面传的参数是一个 List<Note> 类型的对象，
                                           //传参前需要查询查询数据库并得到一个对象（直接用qurry方法），再把这个对象传进去，代表原始数据列表
        mOriginalValues = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    private class ItemFilter extends Filter {
//        private boolean isOriginalListRestored = false; // 标记是否恢复了原始数据源
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d("Filter", "performFiltering() called with constraint: " + constraint);
            FilterResults results = new FilterResults();
            List<Note> filteredList = new ArrayList<>();

            if (TextUtils.isEmpty(constraint)) {
                // 如果搜索关键字为空，则显示完整的数据源
//                results.values = mOriginalValues;
//                results.count = mOriginalValues.size();
//                    filteredList.addAll(mOriginalValues);
                filteredList = new ArrayList<>(mOriginalValues);
                Log.d("filteredList", "displayList is ready!!!!!!!!!!!!!!!!"+"-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-");
            } else {
                // 过滤数据源，只保留包含搜索关键字的笔记
                //List<Note> filteredList = new ArrayList<>();
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Note note : mOriginalValues) {
                    if (note.getContent().toLowerCase().contains(constraint.toString().toLowerCase())) {//
                        filteredList.add(note);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();///为什么这两行放三个花括号的外面
            //验证过滤器的执行,这里不能正常执行
            Log.d("Filter", "performFiltering() called with constraint: " + constraint+"=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
            Log.d("Filter", "performFiltering() finished");
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDisplayedValues.clear();
            mDisplayedValues.addAll((List<Note>) results.values);
//            mDisplayedValues = (List<Note>) results.values;
            notifyDataSetChanged();
        }
    }


}
