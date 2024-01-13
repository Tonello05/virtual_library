package com.example.virtuallibrary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface BookDAO {

    @Insert
    public void addBook(Book book);
    @Update
    public void updateBook(Book book);
    @Delete
    public void removeBook(Book book);
    @Query("select * from book")
    public List<Book> getAllBooks();
    @Query("select * from book where bookId ==:id")
    public Book getBookById(int id);

    @Query("select * from book where whishlist == :whislist")
    public List<Book> getWhislistBooks(boolean whislist);


}
