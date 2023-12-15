package com.example.study1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.WindowDecorActionBar;

import android.annotation.SuppressLint;

import android.content.Context;

import android.content.Intent;

import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.os.Bundle;

import android.text.Html;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;

import android.text.style.ImageSpan;
import android.util.Base64;

import android.util.Log;
import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import android.widget.ImageView;

import android.widget.TextView;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.io.OutputStreamWriter;

import java.io.ByteArrayOutputStream;

public class MainActivity4 extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;//在启动图片选择器的时候，你可以使用这个常量作为请求的标识，然后在 onActivityResult() 方法中根据这个标识来处理返回的结果。

    private ImageView delete,saveNote,picture,pic_select,backHome;

    private TextView showTime;

    private EditText et_Content;

    private MyDBhelper myDBhelper;

    private Uri selectedImageUri;


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

        pic_select.setOnClickListener(this);

        // 获取Intent中传递的内容，即保证每次打开一条item的时候，都会显示先前编辑好的文本和图片

        String contentFromIntent = getIntent().getStringExtra("content");

        if (contentFromIntent != null) {

            et_Content.setText(contentFromIntent);

            init();

        }
        ////// Load the image from the saved URI, if available
        String imageUri = getIntent().getStringExtra("image_uri");//注意！！这里的字段名不能错！！
        if (imageUri != null) {
            selectedImageUri = Uri.parse(imageUri);//Uri.parse 方法可以将一个字符串形式的 URI 转换为 Uri 对象
            Glide.with(this)//建一个 Glide 的实例，并将当前活动的 Context 对象传入
                    .load(selectedImageUri) //加载 URI 对应的图片
                    .into(picture); //将加载的图片设置到 ImageView 中
        }

    }

    //获取控件对象

    private void init(){

        backHome=findViewById(R.id.backhome);

        delete=findViewById(R.id.delete);

        saveNote=findViewById(R.id.save_note);

        showTime=findViewById(R.id.showTime);

        et_Content=findViewById(R.id.content);

        pic_select=findViewById(R.id.pic_select);

        picture=findViewById(R.id.picture);

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

            if(content.isEmpty()){

                Toast.makeText(MainActivity4.this,"内容不能为空！！",Toast.LENGTH_SHORT).show();//Toast在编辑页面弹出来

            }else {

                // 数据的添加或更新，调用MyDBhelper的方法，首先要有对象

                myDBhelper = new MyDBhelper(MainActivity4.this, "note.db", null, 4);

                /////判断当前页面是否是编辑已有笔记的页面，即通过判断数据库中是否有此id的方法来判断

                String noteId = getIntent().getStringExtra("id");/////注意双引号里面的内容必须与前面传数据来的时候的键名一致，不要写成noteId！在mainactivity3中用了putExtra的方法传递值来这个页面，在这个页面中可以通过键名“id”来获取传过来的数据

                if (noteId != null) {/////注意使用这个if的判断条件
                    // 如果是，则更新原先笔记的内容

                    String imageUri = "";/////确保在没有有效的 selectedImageUri 时，imageUri 仍然有一个非空的初始值。这样，即使没有选择图片，imageUri 也不会是 null，而是一个空字符串。
                    if (selectedImageUri != null) { // Check if an image has been selected

                        imageUri = selectedImageUri.toString(); // Get the selected image's URI as a string
                        Log.d("TAG", "onClick: "+imageUri.toString());

                    }else {
                        Log.d("TAG", "onClick: "+"-=-=-=-=-=-=-=-=selectedImageUri is null!!!!!!!!!!!=-=--=-=-=-=-=-=-");
                    }

                    Boolean flag = myDBhelper.updateData(noteId, content, imageUri);/////调用的是更新方法，和插入insert的方法不一样

                    if (flag == true) {
                        setResult(2);
                        Toast.makeText(MainActivity4.this, "修改成功！！", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(MainActivity4.this, "修改失败！！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //如果不是，则新增一条笔记
                    String imageUri = "";
                    if (selectedImageUri != null) { // Check if an image has been selected
                        imageUri = selectedImageUri.toString(); // Get the selected image's URI as a string
                    }

                    Boolean flag = myDBhelper.insertData(content,imageUri);
                    if (flag == true) {
                        //如果添加成功，将数据回传的结果码设置为2
                        setResult(2);
                        Toast.makeText(MainActivity4.this, "添加成功！！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity4.this, "添加失败！！", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }
        if(id==R.id.pic_select){
            // 创建一个 Intent，指定 action 为 Intent.ACTION_PICK，表示选择内容
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*"); // 指定要选择的内容类型为图片
            startActivityForResult(intent, PICK_IMAGE_REQUEST); // 启动图片选择器，并等待结果

        }

    }
    //重写 onActivityResult() 方法，并在其中获取选择的图片的 URI 并将其插入到笔记中。
    // 在 onActivityResult 方法中
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ImageView picture =findViewById(R.id.picture);
            //Uri imageUri = data.getData();
//            Log.d("TAG", "onActivityResult: "+imageUri.toString());
            selectedImageUri = data.getData();
            // 将选定的图片 URI 传递给保存笔记的逻辑
            picture.setImageURI(selectedImageUri);
            Log.d("TAG", "onActivityResult: "+selectedImageUri.toString());
            // 使用Glide加载图片并显示在ImageView中
            Glide.with(this)
                    .load(selectedImageUri)
                    .into(picture); // 将加载的图片显示在名为"picture"的ImageView中
        }
    }


}

//

//不能用switch，代码中出现 "Constant expression required" 错误，是因为在 Android 中，库项目中的资源标识符不是真正的常量，因此不能在 switch 语句中使用。

// 这是因为从 Android Gradle Plugin 8.0.0 开始，//资源（如 [R.id](http://R.id). ）不再被声明为 final（即常量表达式），这是为了优化构建速度，这是在 switch 语句中使用的先决条件5。