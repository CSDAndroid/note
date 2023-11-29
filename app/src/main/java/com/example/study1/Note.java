package com.example.study1;

//Note模型类：用来存放数据库表中的三个字段
public class Note {
    //这三个属性将会用来存储数据库表当中三个字段的值
    private String id;
    private String content;
    private String note_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote_time() {
        return note_time;
    }

    public void setNote_time(String note_time) {
        this.note_time = note_time;
    }
}
