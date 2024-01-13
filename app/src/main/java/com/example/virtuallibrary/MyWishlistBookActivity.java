package com.example.virtuallibrary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.android.material.snackbar.Snackbar;

public class MyWishlistBookActivity extends AppCompatActivity implements View.OnClickListener{


    private BookDatabse bookDB;

    private static String title;
    private static String author;
    private static String publicationYear;
    private static int book_id;
    private Snackbar mySnackBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wishlist_book);

        //messaggio di operazione completata
        mySnackBar = Snackbar.make(findViewById(R.id.layout_my_wishlist), "Lista dei libri aggiornata", Snackbar.LENGTH_SHORT);

        Button btn_addToIhave = findViewById(R.id.btn_remove_from_mywishlist);
        Button btn_addToWhishList = findViewById(R.id.btn_add_to_my_book_wishlist);
        btn_addToIhave.setOnClickListener(this);
        btn_addToWhishList.setOnClickListener(this);

        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        bookDB = Room.databaseBuilder(getApplicationContext(),BookDatabse.class, "bookDB").addCallback(myCallBack).build();

        Bundle extras = getIntent().getExtras();
        if(extras != null){

            title = extras.getString("bookName");
            author = extras.getString("author");

            publicationYear = extras.getString("year");
            book_id = extras.getInt("id");

        }
        TextView txt_title = findViewById(R.id.txt_book_title_my_wishList);
        txt_title.setText(title);
        TextView txt_author = findViewById(R.id.txt_book_author_my_wishlist);
        txt_author.setText("autore : " + author);
        TextView txt_year = findViewById(R.id.txt_publishing_year_my_wishlist);
        txt_year.setText("pubblicazione : " + publicationYear);

    }


    @Override
    public void onClick(View v) {
        new DatabaseOperationTaskWishlist().execute(String.valueOf(v.getId()), bookDB);
    }

    class DatabaseOperationTaskWishlist extends AsyncTask<Object, Void, Void> {

        private BookDatabse bookDB;

        @Override
        protected Void doInBackground(Object... params) {
            int viewId = Integer.parseInt((String) params[0]);
            bookDB = (BookDatabse) params[1];

            if (viewId == R.id.btn_remove_from_mywishlist) {
                removeBookFromList();
            } else if (viewId == R.id.btn_add_to_my_book_wishlist) {
                addToWhishList();
            }

            return null;
        }

        private void removeBookFromList() {
            Book p1 = new Book(book_id,title, author, publicationYear, true);

            bookDB.getBookDAO().removeBook(p1);
            bookDB.close();
        }

        private void addToWhishList(){
            Book p1 = new Book(book_id,title , author, publicationYear, false);

            bookDB.getBookDAO().updateBook(p1);
            bookDB.close();

        }



        @Override
        protected void onPostExecute(Void aVoid) {

            mySnackBar.show();

        }
    }

}
