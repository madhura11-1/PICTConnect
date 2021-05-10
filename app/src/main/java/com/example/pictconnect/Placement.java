package com.example.pictconnect;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Placement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Placement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Placement.
     */
    // TODO: Rename and change types and number of parameters
    public static Placement newInstance(String param1, String param2) {
        Placement fragment = new Placement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<Post> posts;
    private ShimmerRecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private LayoutAnimationController controller;
    private PlacementAdapter placementAdapter;

    public Placement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_placement, container, false);
        setGlobals(view);

        new GetAllPosts().execute("https://pict-connect.herokuapp.com/api/posts");

        return view;
    }

    private void setGlobals(View view) {

        posts = new ArrayList<>();
        controller = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.animation_down_to_up);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        placementAdapter = new PlacementAdapter(getContext(), posts);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(placementAdapter);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.showShimmerAdapter();


    }

    class GetAllPosts extends AsyncTask<String,Void,String>{


        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Loading data...");
            progressDialog.show();*/
        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = bufferedReader.readLine()) != null){

                    result.append(line).append("\n");

                }


            } catch (MalformedURLException e) {
                return "nework erre";
            } catch (IOException e) {
                return e.getMessage();
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            s = "{ \"hello\" : " + s + "\n}";

            Log.d("result", "onPostExecute: " + s);



            try {
                JSONObject jsonObject = new JSONObject(s);

                JSONArray jsonArray = jsonObject.getJSONArray("hello");
                for(int i=0;i<jsonArray.length();i++){

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String id = jsonObject1.getString("_id");
                    String type = jsonObject1.getString("type");
                    String body = jsonObject1.getString("body");
                    String title = jsonObject1.getString("title");
                    String subheading = jsonObject1.getString("subheading");
                    String date = jsonObject1.getString("date");
                    String likes = jsonObject1.getString("likes");
                    String user_id = jsonObject1.getString("user_id");

                    if(Integer.parseInt(type) == 1) {
                        Post post = new Post(id, Integer.parseInt(type), body, title, subheading, date, Integer.parseInt(likes), user_id);
                        posts.add(post);
                    }
                    placementAdapter.notifyDataSetChanged();
                    recyclerView.hideShimmerAdapter();
                    recyclerView.scheduleLayoutAnimation();

                    Log.d("kooola of " + i, id + type + body);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

/*
            if(progressDialog != null){
                progressDialog.dismiss();
            }*/
        }
    }
}