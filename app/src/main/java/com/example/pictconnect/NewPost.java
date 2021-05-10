package com.example.pictconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.AsynchronousChannelGroup;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewPost extends AppCompatActivity {

    TextInputLayout title,subheading,content;
    MaterialButton post;
    String titles,subheadings,contents,user_ids,date;
    int likes,type;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        title = findViewById(R.id.title_post);
        subheading = findViewById(R.id.subtitle_post);
        content = findViewById(R.id.content_post);
        post = findViewById(R.id.post_descriptive);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
        RadioButton radioButton1 = findViewById(R.id.radio1);
        RadioButton radioButton2 = findViewById(R.id.radio2);

        if(radioButton1.isChecked()){
            type = 1;
        }
        else{
            type = 2;
        }

        // This overrides the radiogroup onCheckListener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    if(checkedRadioButton.getText().equals("Placement")){
                        type = 1;
                    }else{
                        type = 2;
                    }
                }
            }
        });


         post.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 titles = title.getEditText().getText().toString();
                 subheadings = subheading.getEditText().getText().toString();
                 contents = content.getEditText().getText().toString();
                 user_ids = "C2K18106114";
                 likes = 0;
                 date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                 new PostData().execute("https://pict-connect.herokuapp.com/api/posts/new");
             }
         });
    }

    class PostData extends AsyncTask<String,Void,String>{

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NewPost.this);
            progressDialog.setMessage("Successfully posting the content...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type",type);
                jsonObject.put("body",contents);
                jsonObject.put("title",titles);
                jsonObject.put("subheading",subheadings);
                jsonObject.put("date",date);
                jsonObject.put("likes",likes);
                jsonObject.put("user_id",user_ids);
                Log.d("json object",jsonObject.toString());

                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Accept","application/json");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.connect();

                DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jsonObject.toString());

                os.flush();
                os.close();
                Log.i("STATUS", String.valueOf(urlConnection.getResponseCode()));
                Log.i("MSG" , urlConnection.getResponseMessage());

                urlConnection.disconnect();


            } catch (MalformedURLException e) {
                return "nework erre";
            } catch (IOException e) {
                return e.getMessage();
            } catch (JSONException e) {
                return e.getMessage();
            }
            return "done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(progressDialog != null && s.equals("done")){

                Log.d("done","inside post execution");
                progressDialog.dismiss();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(NewPost.this,BaseActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                }, 2000);

            }


        }
    }
}