package com.example.movieapp;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import com.example.movieapp.Movie;
import com.example.movieapp.MovieData;
import com.example.movieapp.RecyclerAdapter;

public class Home extends Fragment {

    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    ItemTouchHelper itemTouchHelper;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        movies = MovieData.getMovies();
        adapter = new RecyclerAdapter(movies,getParentFragmentManager());

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab = view.findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddMovieDialog();
            }
        });

        return view;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            movies.remove(viewHolder.getAbsoluteAdapterPosition());
            adapter.notifyDataSetChanged();

        }
    };

    private void showAddMovieDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add a new Movie");

        final EditText inputTitle = new EditText(getContext());
        inputTitle.setHint("Title");
        final EditText inputYear = new EditText(getContext());
        inputYear.setHint("Year");
        final EditText inputID = new EditText(getContext());
        inputID.setHint("ID");
        final EditText inputType = new EditText(getContext());
        inputType.setHint("Type");
        final EditText inputURL = new EditText(getContext());
        inputURL.setHint("URL");
        final EditText inputDesc = new EditText(getContext());
        inputDesc.setHint("Description");

        // Layout for the EditTexts
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20, 10, 20, 10);
        layout.addView(inputTitle);
        layout.addView(inputYear);
        layout.addView(inputID);
        layout.addView(inputType);
        layout.addView(inputURL);
        layout.addView(inputDesc);

        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = inputTitle.getText().toString().trim();
                String strYear = inputYear.getText().toString().trim();
                String type = inputType.getText().toString().trim();
                String strID = inputID.getText().toString().trim();
                String url = inputURL.getText().toString().trim();
                String desc = inputDesc.getText().toString().trim();

                if (!title.isEmpty() && !type.isEmpty() && !strID.isEmpty() && !strYear.isEmpty()) {
                    int year = Integer.parseInt(strYear);
                    int id = Integer.parseInt(strID);

                    Movie newMovie = new Movie(title, year, id, type, url, desc.isEmpty() ? "No Description added" : desc);
                    movies.add(newMovie);
                    adapter.notifyItemInserted(movies.size() - 1);
                    recyclerView.scrollToPosition(movies.size() - 1);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
