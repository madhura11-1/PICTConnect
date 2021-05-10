package com.example.pictconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText username,password;
    MaterialButton login;
    TextView signup;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.fragment_login);
        signup = findViewById(R.id.create_account);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String id = sharedpreferences.getString("loginId","null");
        String passi = sharedpreferences.getString("pass","null");

        if(id.equals("null") && passi.equals("null")) {

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(MainActivity.this, SignUp.class);
                    startActivity(intent);

                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String uname = username.getText().toString();
                    String pass = password.getText().toString();

                    if (uname.isEmpty() || pass.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please fill in all the fields", Toast.LENGTH_LONG).show();

                    } else {

                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putString("loginId", uname);
                        editor.putString("pass", pass);
                        editor.apply();

                        Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                }
            });
        }else{
            Intent intent = new Intent(MainActivity.this, BaseActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}