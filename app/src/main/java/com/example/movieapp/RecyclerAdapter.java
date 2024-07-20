package com.example.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private ArrayList<Movie> movies;
    private FragmentManager fragmentManager;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView type;
        public TextView year;
        public TextView id;
        public ImageView img;
        public Button dataloaderlaunchbutton;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            img = view.findViewById(R.id.img);
            type = view.findViewById(R.id.type);
            id = view.findViewById(R.id.id);
            year = view.findViewById(R.id.year);
            dataloaderlaunchbutton = view.findViewById(R.id.dataloaderlaunchbutton);
        }
    }

    public RecyclerAdapter(ArrayList<Movie> movies, FragmentManager fragmentManager) {
        this.movies = movies;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie currentmovie = movies.get(position);

        holder.title.setText(currentmovie.gettitle());
        holder.type.setText(currentmovie.gettype());
        holder.year.setText(String.valueOf(currentmovie.getyear()));
        holder.id.setText(String.valueOf(currentmovie.getid()));

        // Load image using Glide
        Glide.with(holder.img.getContext())
                .load(currentmovie.getimgurl())
                .placeholder(R.drawable.placeholder) // Placeholder image
                .error(R.drawable.error) // Error image
                .into(holder.img);

        holder.dataloaderlaunchbutton.setOnClickListener(v -> {
            String title = currentmovie.gettitle();
            int year = currentmovie.getyear();
            int ID = currentmovie.getid();
            String type = currentmovie.gettype();
            String URL = currentmovie.getimgurl();
            String Desc = currentmovie.getdesc();

            MovieDetail movieDetailFragment = new MovieDetail();
            Bundle args = new Bundle();
            args.putString("movietitle", title);
            args.putInt("movieyear", year);
            args.putInt("movieid", ID);
            args.putString("movietype", type);
            args.putString("movieUrl", URL);
            args.putString("moviedesc", Desc);

            movieDetailFragment.setArguments(args);

            if (fragmentManager != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.FrameLayout, movieDetailFragment)
                        .addToBackStack(null) // Optional: Add to back stack
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void removeItem(int position) {
        movies.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Movie movie, int position) {
        movies.add(position, movie);
        notifyItemInserted(position);
    }

    public Movie getItem(int position) {
        return movies.get(position);
    }
}
