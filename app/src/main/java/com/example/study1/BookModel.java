package com.example.study1;

public class BookModel {

    private  Integer id;
    public  String title;
    public String author;

    public BookModel(int title, String id, String author) {
        this.title = String.valueOf(title);
        this.id = Integer.valueOf(id);
        this.author = author;
    }

    @Override
    public String toString() {
        return "BookModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


//    public String getAuthor() {
//        return null;
//    }
//
//    public String getTitle() {
//        return null;
//    }
//
//    public String[] getId() {
//        return null;
//    }
}
