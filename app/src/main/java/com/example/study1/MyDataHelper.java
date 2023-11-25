package com.example.study1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyDataHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "book";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";

    public MyDataHelper(@Nullable Context context) {
        super(context, "books.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INIEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT," + COLUMN_AUTHOR + " TEXT);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String addOne(BookModel bookModel){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_AUTHOR,bookModel.getAuthor());
        cv.put(COLUMN_TITLE,bookModel.getTitle());

        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        long insert = sqLiteDatabase.insert(TABLE_NAME, COLUMN_AUTHOR, cv);
        if(insert==-1){
            return "fail";
        }
        sqLiteDatabase.close();
        return "success";
    }//增加方法

    public String deleteOne(BookModel bookModel){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int delete = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(bookModel.getId())});//

        sqLiteDatabase.close();
        if (delete==0){
            return "fail";
        }
        return "success";
    }//删除方法


    public String updataOne(BookModel bookModel){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_AUTHOR,bookModel.getAuthor());
        cv.put(COLUMN_TITLE,bookModel.getTitle());

        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        int update=sqLiteDatabase.update(TABLE_NAME,cv,COLUMN_ID+"=?",new String[]{Arrays.toString(new Integer[]{bookModel.getId()})});//
        sqLiteDatabase.close();
        if (update==0){
            return "fail";
        }
        return "success";
    }//改方法

    public List<BookModel>getAll(){
        List<BookModel> list = new ArrayList<>();

        String sql ="SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase sqLiteDatabase =this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);//

        int idIndex = cursor.getColumnIndex(COLUMN_ID);
        int titileIndex = cursor.getColumnIndex(COLUMN_TITLE);
        int authorIndex = cursor.getColumnIndex(COLUMN_AUTHOR);

        while (cursor.moveToNext()){
            BookModel bookModel = new BookModel(cursor.getInt(idIndex),cursor.getString(titileIndex), cursor.getString(authorIndex));
            list.add(bookModel);
        }

        sqLiteDatabase.close();

        return list;
    }//查
}
