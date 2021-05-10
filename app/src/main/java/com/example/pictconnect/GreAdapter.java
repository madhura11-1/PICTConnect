package com.example.pictconnect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class GreAdapter extends RecyclerView.Adapter<GreAdapter.GreViewHolder> {

    Context context;
    ArrayList<Post> posts;

    GreAdapter(Context context,ArrayList<Post> arr){

        this.context = context;
        posts = arr;
    }

    @NonNull
    @Override
    public GreAdapter.GreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_placement,parent,false);
        return new GreAdapter.GreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GreAdapter.GreViewHolder holder, int position) {

        holder.title.setText(posts.get(position).getTitle());
        holder.subheading.setText(posts.get(position).getSubheading());
        holder.author.setText(posts.get(position).getUser_id());          // from user id get author name

        holder.date.setText(posts.get(position).getDate().toString());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,FullPost.class);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class GreViewHolder extends RecyclerView.ViewHolder{

        TextView title, subheading, date, author;
        MaterialCardView card;
        public GreViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subheading = itemView.findViewById(R.id.subheading);
            date = itemView.findViewById(R.id.date);
            author = itemView.findViewById(R.id.author);
            card = itemView.findViewById(R.id.cardV);
        }
    }
}
