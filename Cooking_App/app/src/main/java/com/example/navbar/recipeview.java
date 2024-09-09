package com.example.navbar;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class recipeview extends Fragment {

    private TextView recipeTextView;
    private TextView recipetextfile;
    private Button resetTimerButton;
    private Button startTimerButton;
    private String recipeName;
    private int estimatedTime;
    private CountDownTimer countDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipeview, container, false);

        recipeTextView = view.findViewById(R.id.recipeTextView);
        recipetextfile = view.findViewById(R.id.recipetextfile);
        resetTimerButton = view.findViewById(R.id.resetTimerButton);
        startTimerButton = view.findViewById(R.id.startTimerButton);

        Bundle args = getArguments();
        if (args != null) {
            recipeName = args.getString("recipeName", "");
            estimatedTime = args.getInt("estimatedTime", 0);

            setrecipetextfile(recipeName);
            recipeTextView.setText(recipeName + "\n\nEstimated Time: " + estimatedTime + " mins");
        }

        resetTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        return view;
    }

    public void resetTimer() {
        // Reset timer logic goes here
        // For demonstration, let's just show a toast message
        Toast.makeText(getActivity(), "Timer Reset!", Toast.LENGTH_SHORT).show();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        recipeTextView.setText(recipeName + "\n\nEstimated Time: " + estimatedTime + " mins");
    }

    public void startTimer() {
        // Start timer logic goes here
        // For demonstration, let's create a simple CountDownTimer
        countDownTimer = new CountDownTimer(estimatedTime * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 1000 / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeLeft = String.format("%02d:%02d", minutes, seconds);
                recipeTextView.setText(recipeName + "\n\nTime Left: " + timeLeft);
            }

            @Override
            public void onFinish() {
                recipeTextView.setText(recipeName + "\n\nTimer Finished!");
            }
        }.start();
    }

    private void setrecipetextfile(String recipeName) {
        if (recipeName == "Indian Food"){

            recipetextfile.setText("Recipe for Indian food:\n" +
                    "\n" + "1. Heat oil, add spices.\\n\n" +"\n" + "2. Sauté onions till golden.\\n\n" +"\n" + "3. Add garlic, ginger paste.\\n\n" +"\n" +
                    "4. Stir in tomatoes, cook.\\n\n" +"\n" + "5. Mix in vegetables, simmer.\\n\n" +"\n" + "6. Add spices, cook gently.\\n\n" + "\n" +"7. Stir in yogurt, combine.\\n\n" +
                    "\n" +"8. Garnish with cilantro, serve.\\n");

        }
        else if (recipeName == "Mexican Food"){
            recipetextfile.setText("Recipe for Mexican food:\n" +
                    "\n" + "1. Brown ground beef with onions, garlic.\\n\n\n" + "\n" + "2. Stir in taco seasoning, cook until fragrant.\\n\n" + "\n" +"3. Add beans, tomatoes, simmer for 10 mins.\\n\n" + "\n" +"4. Layer tortilla, meat mixture, cheese, repeat.\\n\n" +
                    "\n" +"5. Bake until cheese melts, garnish with cilantro.\\n\n" +"\n" + "6. Serve with salsa, sour cream, enjoy!\\n\n" + "\n" + "This should make a delicious Mexican dish for you to enjoy!");

        }
        else if (recipeName == "Sea Food"){
            recipetextfile.setText("Recipe for seafood:\n" + "\n" + "1. Sauté garlic, onions in olive oil.\\n\n" +"\n" + "2. Add shrimp, cook until pink.\\n\n" +
                    "\n" +"3. Stir in diced tomatoes, simmer gently.\\n\n" +"\n" + "4. Season with salt, pepper, paprika.\\n\n" + "\n" +"5. Toss in cooked pasta, mix well.\\n\n" +"\n" + "6. Sprinkle parsley, squeeze lemon juice.\\n\n" +
                    "\n" +"7. Serve hot with crusty bread.\\n\n" + "\n" + "Enjoy your flavorful seafood dish!");

        }
        else{
            recipetextfile.setText("Recipe for Thai food:\n" + "\n" + "1. Heat oil, stir-fry garlic, chili.\\n\n" + "\n" +"2. Add chicken, cook until browned.\\n\n" +"\n" + "3. Mix in vegetables, stir-fry briefly.\\n\n" +"\n" +
                    "4. Pour coconut milk, simmer gently.\\n\n" + "\n" +"5. Add fish sauce, sugar, stir.\\n\n" +"\n" + "6. Toss in basil leaves, cook briefly.\\n\n" +"\n" + "7. Squeeze lime juice, adjust seasoning.\\n\n" +"\n" + "8. Garnish with cilantro, serve hot.\\n\n" +
                    "\n" + "Enjoy your Thai dish!");

        }
    }
}
