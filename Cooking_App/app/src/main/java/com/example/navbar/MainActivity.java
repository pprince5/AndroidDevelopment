package com.example.navbar;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomnavigationview;
    Explore exploreFragment = new Explore();

    Recipe recipeFragment =  new Recipe();

    Home homeFragment = new Home();

    timer  timerFragment = new timer();


    Feedback feedbackFragment = new Feedback();

    recipeview recipeviewFragment = new recipeview();



    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EdgeToEdge.enable(this);
        //

        bottomnavigationview = findViewById(R.id.bottom_navigationBar);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, homeFragment, "homeFragment")
                .add(R.id.frameLayout, exploreFragment,"ExploreFragment")
                .add(R.id.frameLayout, recipeFragment, "RecipeFragment")
                .add(R.id.frameLayout, timerFragment, "TimerFragment")
                .add(R.id.frameLayout,feedbackFragment, "FeedbackFragment")
                .add(R.id.frameLayout,recipeviewFragment, "RecipeViewFragment")
                .hide(exploreFragment)
                .hide(recipeFragment)
                .hide(timerFragment)
                .hide(feedbackFragment)
                .hide(recipeviewFragment)
                .commit();

        bottomnavigationview.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.navbar_homeTab) {
                    getSupportFragmentManager().beginTransaction().hide(recipeviewFragment).hide(feedbackFragment).hide(exploreFragment).hide(recipeFragment).hide(timerFragment).show(homeFragment).commit();
                    return true;
                } if (id == R.id.navbar_RecipeTab) {
                    getSupportFragmentManager().beginTransaction().hide(recipeviewFragment).hide(feedbackFragment).hide(homeFragment).hide(exploreFragment).hide(timerFragment).show(recipeFragment).commit();
                    return true;

                } if (id == R.id.navbar_Feedbacktab) {
                    getSupportFragmentManager().beginTransaction().hide(recipeviewFragment).hide(feedbackFragment).hide(recipeFragment).hide(homeFragment).hide(timerFragment).show(exploreFragment).commit();
                    return true;
                }
                if (id == R.id.navbar_TimerTab) {
                    getSupportFragmentManager().beginTransaction().hide(recipeviewFragment).hide(feedbackFragment).hide(recipeFragment).hide(homeFragment).hide(exploreFragment).show(timerFragment).commit();
                    return true;
                }
                return false;
            }
        });
        //
        //timer code


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
