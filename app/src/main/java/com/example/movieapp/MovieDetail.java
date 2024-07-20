package com.example.movieapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class MovieDetail extends Fragment {

    private String title;
    private int year;
    private int ID;
    private String type;
    private String URL;
    private String Desc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            title = getArguments().getString("movietitle");
            year = getArguments().getInt("movieyear");
            ID = getArguments().getInt("movieid");
            type = getArguments().getString("movietype");
            URL = getArguments().getString("movieUrl");
            Desc = getArguments().getString("moviedesc");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        TextView titleTextView = view.findViewById(R.id.detail_title);
        TextView yearTextView = view.findViewById(R.id.detail_year);
        TextView typeTextView = view.findViewById(R.id.detail_type);
        TextView descTextView = view.findViewById(R.id.detail_desc);

        titleTextView.setText(title);
        yearTextView.setText(String.valueOf(year));
        typeTextView.setText(type);
        descTextView.setText(Desc);

        // Load image using Glide
        ImageView imageView = view.findViewById(R.id.detail_img);
        Glide.with(this).load(URL).into(imageView);

        return view;
    }
}
