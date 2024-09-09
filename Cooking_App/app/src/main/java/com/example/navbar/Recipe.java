package com.example.navbar;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Recipe extends Fragment implements View.OnClickListener {

    private Button indianFoodButton, mexicanFoodButton, thaiFoodButton, seaFoodButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

        indianFoodButton = view.findViewById(R.id.indianfoodbutton);
        mexicanFoodButton = view.findViewById(R.id.mexicanfoodbutton);
        thaiFoodButton = view.findViewById(R.id.thaifoodbutton);
        seaFoodButton = view.findViewById(R.id.seafoodbutton);

        indianFoodButton.setOnClickListener(this);
        mexicanFoodButton.setOnClickListener(this);
        thaiFoodButton.setOnClickListener(this);
        seaFoodButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        String recipeName = "";
        int estimatedTime = 0;

        if (v.getId() == R.id.indianfoodbutton) {
            recipeName = "Indian Food";
            estimatedTime = 20;
        } else if (v.getId() == R.id.mexicanfoodbutton) {
            recipeName = "Mexican Food";
            estimatedTime = 30;
        } else if (v.getId() == R.id.thaifoodbutton) {
            recipeName = "Thai Food";
            estimatedTime = 50;
        } else if (v.getId() == R.id.seafoodbutton) {
            recipeName = "Sea Food";
            estimatedTime = 35;
        }

        // Navigate to recipeview fragment with arguments
        recipeview recipeviewFragment = new recipeview();
        Bundle args = new Bundle();
        args.putString("recipeName", recipeName);
        args.putInt("estimatedTime", estimatedTime);
        recipeviewFragment.setArguments(args);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, recipeviewFragment)
                .addToBackStack(null)
                .commit();
    }
}
