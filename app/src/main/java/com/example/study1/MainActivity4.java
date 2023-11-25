package com.example.study1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity4 extends AppCompatActivity {

    private EditText editText5;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        editText5=(EditText) findViewById(R.id.editText5);
        String inputTest = load();
        if(!TextUtils.isEmpty(inputTest)){
            editText5.setText(inputTest);
            editText5.setSelection(inputTest.length());
            Toast.makeText(this,"Restoring succeed",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        String inputTest=editText5.getText().toString();
        save(inputTest);
    }
    public void save(String inputTest){
        FileOutputStream out =null;
        BufferedWriter writer=null;
        try {
            out=openFileOutput("data", Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputTest);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public String load(){
        FileInputStream in =null;
        BufferedReader reader =null;
        StringBuilder content = new StringBuilder();
        try {
            in =openFileInput("data");
            reader =new BufferedReader(new InputStreamReader(in));
            String line="";
            while ((line =reader.readLine())!=null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            {
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        } return content.toString();
    }
}

