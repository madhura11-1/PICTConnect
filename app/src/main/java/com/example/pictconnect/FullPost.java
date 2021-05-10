package com.example.pictconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FullPost extends AppCompatActivity {

    TextView title,date,author,subheading,content;
    ImageView vote;
    TextView vote_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_post);

        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        author = findViewById(R.id.author);
        subheading = findViewById(R.id.subheading);
        content = findViewById(R.id.content);
        vote = findViewById(R.id.vote);
        vote_count = findViewById(R.id.vote_count_no);

        Intent i = getIntent();
        Post post = (Post)i.getSerializableExtra("obj");

        title.setText(post.getTitle());
        date.setText(post.getDate());
        author.setText(post.getUser_id());
        subheading.setText(post.getSubheading());
        content.setText(post.getContent());
        vote_count.setText(String.valueOf(post.getLikes()));

        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuilder result = new StringBuilder();

                try {
                    URL url = new URL("https://pict-connect.herokuapp.com/api/posts/like/" + post.getPost_id());
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setReadTimeout(10000);
                    urlConnection.setConnectTimeout(10000);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.connect();

                   /* InputStream inputStream = urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while((line = bufferedReader.readLine()) != null){

                        result.append(line).append("\n");

                    }*/

                    vote_count.setText(String.valueOf(Integer.parseInt(vote_count.getText().toString()) + 1));

                    Toast.makeText(FullPost.this, "You liked a post", Toast.LENGTH_LONG).show();

                    urlConnection.disconnect();


                } catch (MalformedURLException e) {
                    Log.d("errror1", "onClick: nework erre");
                } catch (IOException e) {
                    Toast.makeText(FullPost.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}