package com.example.virtuallibrary;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "book")
public class Book {

    @ColumnInfo(name="bookId")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int bookId;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "author")
    String author;
    @ColumnInfo(name = "yearOfPublication")
    String yearOfPublication;
    @ColumnInfo(name = "whishlist")
    boolean whishlist;

    @Ignore
    public Book(){

    }

    public Book(String title, String author, String yearOfPublication,boolean whishlist){
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.whishlist = whishlist;
    }
    @Ignore
    public Book(int bookId,String title, String author, String yearOfPublication,boolean whishlist){
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.whishlist = whishlist;
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getYearOfPublication() {
        return yearOfPublication;
    }
    public  boolean getWhishlist(){
        return whishlist;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public void setWhishlist(boolean whishlist) {
        this.whishlist = whishlist;
    }
}

