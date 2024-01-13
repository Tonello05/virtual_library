package com.example.virtuallibrary;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
public class myWhishlistActivity extends AppCompatActivity{
    private BookDatabse bookDB;
    private List<Book> myBooks;
    private ScrollView container;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_whishlist_layout);

        container = findViewById(R.id.container_my_wishlist);

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

        bookDB = Room.databaseBuilder(getApplicationContext(), BookDatabse.class, "bookDB").fallbackToDestructiveMigration().addCallback(myCallBack).build();
        new DatabaseOperationTaskMyBooksWishlist().execute(bookDB);



    }

    public void handleButtonClick(View v, int index) {

        switchToNewBookActivty(myBooks.get(index));

    }

    public void switchToNewBookActivty(Book book) {
        Intent intent = new Intent(this, MyWishlistBookActivity.class);
        intent.putExtra("bookName", book.title);
        intent.putExtra("author", book.author);
        intent.putExtra("year", book.yearOfPublication);
        intent.putExtra("id", book.bookId);
        startActivity(intent);
    }


    class DatabaseOperationTaskMyBooksWishlist extends AsyncTask<Object, Void, Void> {

        BookDatabse bookDB;

        @Override
        protected Void doInBackground(Object... params) {

            bookDB = (BookDatabse) params[0];

            myBooks = bookDB.getBookDAO().getWhislistBooks(true);
            bookDB.close();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            LinearLayout linearLayout = new LinearLayout(myWhishlistActivity.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            for (int i = 0; i < myBooks.size(); i++) {

                Button btn = new Button(myWhishlistActivity.this);
                btn.setText((CharSequence) myBooks.get(i).getTitle());

                final int currentBookIndex = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleButtonClick(view, currentBookIndex);
                    }
                });

                linearLayout.addView(btn);

            }
            container.removeAllViews();
            container.addView(linearLayout);

        }
    }
}
