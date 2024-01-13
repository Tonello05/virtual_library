package com.example.virtuallibrary;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class}, version = 2)
public abstract class BookDatabse extends RoomDatabase {

    public abstract BookDAO getBookDAO();

}
