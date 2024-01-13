package com.example.virtuallibrary;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
public class SearchBooksActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private JSONArray results;
    private ScrollView container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seacrh_books_activty);
        container = findViewById(R.id.Container_search_books);
        ImageButton btnSearch = findViewById(R.id.btn_search_button);
        btnSearch.setOnClickListener(this);
        title = findViewById(R.id.txt_title_seacrh_books);
    }

    private class SearchBooksTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            String title = params[0].replaceAll(" ", "+");

            try {
                String urlString = "https://openlibrary.org/search.json?q=" + title;
                URL url = new URL(urlString);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                System.out.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                return new JSONObject(result.toString()).optJSONArray("docs");

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            results = result;

            if (results != null) {
                LinearLayout linearLayout = new LinearLayout(SearchBooksActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                for (int i = 0; i < results.length(); i++) {
                    Button btn = new Button(SearchBooksActivity.this);

                    try {
                        btn.setText(results.getJSONObject(i).getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final int currentBookIndex = i;
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                handleButtonClick(view, results.getJSONObject(currentBookIndex));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                    linearLayout.addView(btn);
                }

                container.removeAllViews();
                container.addView(linearLayout);
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_search_button){
            EditText txtTitle = findViewById(R.id.txt_search_box);
            new SearchBooksTask().execute(txtTitle.getText().toString());
        }

    }

    private void handleButtonClick(View view, JSONObject obj){

        switchToNewBookActivty(obj);

    }

    public void switchToNewBookActivty(JSONObject obj){
        Intent intent = new Intent(this, newBookActivity.class);
        try {
            intent.putExtra("bookName", obj.getString("title"));
            intent.putExtra("author", obj.getString("author_name"));
            intent.putExtra("year", obj.getString("first_publish_year"));
            intent.putExtra("id", obj.getString("key"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        startActivity(intent);
    }

}
