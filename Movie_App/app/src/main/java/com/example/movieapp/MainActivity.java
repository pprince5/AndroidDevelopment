package com.example.movieapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomnavigationview;

    Description descriptionFragment = new Description();

    Home homeFragment = new Home();

    Feedback feedbackFragment = new Feedback();

    MovieDetail moviedetailFragment = new MovieDetail();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        bottomnavigationview = findViewById(R.id.navigationbar);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.FrameLayout, homeFragment, "HomeFragment")
                .add(R.id.FrameLayout,feedbackFragment,"FeedbackFragment")
                .add(R.id.FrameLayout,descriptionFragment,"Descriptionfragment")
                .add(R.id.FrameLayout,moviedetailFragment,"MovieDetailFragment")
                .hide(moviedetailFragment)
                .hide(feedbackFragment)
                .hide(descriptionFragment)
                .commit();


        bottomnavigationview.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.Home) {
                    getSupportFragmentManager().beginTransaction().hide(descriptionFragment).hide(feedbackFragment).hide(moviedetailFragment).show(homeFragment).commit();
                    return true;
                } if (id == R.id.feedback) {
                    getSupportFragmentManager().beginTransaction().hide(homeFragment).hide(descriptionFragment).hide(moviedetailFragment).show(feedbackFragment).commit();
                    return true;
                }
                return false;
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



}