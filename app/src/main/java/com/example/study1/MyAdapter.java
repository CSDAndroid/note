package com.example.study1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//数据适配器，用来给listview加载数据用的,然后又创建了四个方法,而且适配器中提供的数据应该来自数据库，取到数据才能用上这个适配器2
public class MyAdapter extends BaseAdapter {
    //使用list<Note>,list集合会存储数据库中note表的所有记录.....适配器的数据来自于list
    //此行声明一个 List<Note> 类型的私有实例变量 list 。该列表将存储适配器将在 ListView 中显示的数据
    private List<Note> list;
    //layoutInflater用于将某个布局转化为View对象，类似于简化打包
    private LayoutInflater layoutInflater;

    //当创建MyAdapter适配器对象时，我们需要先准备好list的数据，为了拿到list的数据，可以为MyAdapter这个类加上一个构造器
    //使得创造适配器对象之前，list先初始化好，下面就可以直接用数据了
    public  MyAdapter(Context context,List<Note> list){   //两个参数，第一个是存了数据库中所有记录内容的list，第二个是传入上下文，即在哪个界面上调用的适配器，把activity传来
        layoutInflater=layoutInflater.from(context);   //从哪个界面（content）中拿到布局对象
        this.list=list;
    }//这是MyAdapter类的构造函数
    @Override
    public int getCount() {
        return list.size();   //返回记录的总条数，即list集合中的元素个数
    }
//获取item数量
    @Override//position指的是集合list里的元素下标，从0开始，然后适配器自动执行后，position就执行下一个item的内容，即pst=1.。。
    public Object getItem(int position) {
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
            convertView=layoutInflater.inflate(R.layout.itemlayout,null,false);//把itemlayout布局转换成了一个视图对象,以便在Activity中使用
            viewHolder=new ViewHolder(convertView);//对应下面那个优化方案里面的内容
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();//又用gettag方法获取加载的内容并存好
        }
        //将数据库中的内容加载到对应的控件上
        Note note= (Note) getItem(position);//把这条记录存的内容给note，就那三个图的过程
        viewHolder.t_content.setText(note.getContent());
        viewHolder.t_time.setText(note.getNote_time());
        return convertView;
    }   //用于配置listview要加载的内容，包括视图和数据项
    //将item.xml文件找出来并转换成View对象，就是在这里定义一个变量然后把找到的view用那个变量储存起来

    //listview的优化方案，防止向下滑动的内容无上限，让手机死机
    class ViewHolder{//设计一个类，用于给item的视图加载数据内容
        TextView t_content,t_time;//定义两个变量，以便于之后修改相应布局里的内容
        public ViewHolder(View view){//传入一个view对象
            t_content=view.findViewById(R.id.item_content);//把对应的视图赋给对象，把找这两个对象的过程封装在这个class类当中
            t_time=view.findViewById(R.id.item_time);
        }
    }
}
