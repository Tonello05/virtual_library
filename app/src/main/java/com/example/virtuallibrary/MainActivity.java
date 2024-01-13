package com.example.virtuallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSearchBooks;
    private Button btnMyBooks;
    private Button btnMyWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSearchBooks = (Button) findViewById(R.id.btn_search_books);
        btnSearchBooks.setOnClickListener(this);
        btnMyBooks = (Button) findViewById(R.id.btn_my_books);
        btnMyBooks.setOnClickListener(this);
        btnMyWishlist = (Button) findViewById(R.id.btn_wish_list);
        btnMyWishlist.setOnClickListener(this);
    }

    public void switchToSearchActivity(){
        Intent intent = new Intent(this, SearchBooksActivity.class);
        startActivity(intent);
    }

    public void switchToMyBooksActivity(){
        Intent intent = new Intent(this, myBooksActivity.class);
        startActivity(intent);
    }

    public void switchTowishlistActivity(){
        Intent intent = new Intent(this, myWhishlistActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view == btnSearchBooks ){
            switchToSearchActivity();
        }else if(view.getId() == R.id.btn_my_books){
            switchToMyBooksActivity();
        }else if(view.getId() == R.id.btn_wish_list){
            switchTowishlistActivity();
        }
    }
}