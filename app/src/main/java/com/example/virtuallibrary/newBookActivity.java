package com.example.virtuallibrary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.android.material.snackbar.Snackbar;

public class newBookActivity extends AppCompatActivity implements View.OnClickListener {

    private BookDatabse bookDB;

    private static String title;
    private static String author;
    private static String publicationYear;
    private static String book_id;
    private Snackbar mySnackBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_book_activity);

        //messaggio di operazione completata
        mySnackBar = Snackbar.make(findViewById(R.id.layout_new_book), "Lista dei libri aggiornata", Snackbar.LENGTH_SHORT);

        Button btn_addToIhave = findViewById(R.id.btn_add_to_ihave);
        Button btn_addToWhishList = findViewById(R.id.btn_add_to_whislist);
        btn_addToIhave.setOnClickListener(this);
        btn_addToWhishList.setOnClickListener(this);


        RoomDatabase.Callback myCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        bookDB = Room.databaseBuilder(getApplicationContext(), BookDatabse.class, "bookDB").fallbackToDestructiveMigration().addCallback(myCallback).build();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            title = extras.getString("bookName");
            author = extras.getString("author");

            publicationYear = extras.getString("year");
            book_id = extras.getString("id");

        }
        TextView txt_title = findViewById(R.id.txt_book_title);
        txt_title.setText(title);
        TextView txt_author = findViewById(R.id.txt_book_author);
        txt_author.setText("autore : " + author);
        TextView txt_year = findViewById(R.id.txt_publishing_year);
        txt_year.setText("pubblicazione : " + publicationYear);

    }


    @Override
    public void onClick(View v) {

        new DatabaseOperationTask().execute(String.valueOf(v.getId()), title, author, publicationYear, book_id, bookDB);

    }


    class DatabaseOperationTask extends AsyncTask<Object, Void, Void> {

        private BookDatabse bookDB;

        @Override
        protected Void doInBackground(Object... params) {
            int viewId = Integer.parseInt((String) params[0]);
            title = (String) params[1];
            author = (String) params[2];
            publicationYear = (String) params[3];
            book_id = (String) params[4];
            bookDB = (BookDatabse) params[5];

            if (viewId == R.id.btn_add_to_ihave) {
                performDatabaseOperation(false);
            } else if (viewId == R.id.btn_add_to_whislist) {
                performDatabaseOperation(true);
            }

            return null;
        }

        private void performDatabaseOperation(boolean isWishlist) {
            Book p1 = new Book(title, author, publicationYear, isWishlist);

                bookDB.getBookDAO().addBook(p1);
                bookDB.close();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            mySnackBar.show();

        }
    }
}