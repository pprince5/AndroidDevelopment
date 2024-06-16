package com.example.navbar;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

public class Explore extends Fragment {

    EditText usernameEditText;
    EditText passwordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        Button submitBtn = view.findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFeedback();
            }
        });
        return view;
    }

    void launchFeedback() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Feedback feedbackFragment = new Feedback();

        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("password", password);

        feedbackFragment.setArguments(bundle);

        // Replace the current fragment with the FeedbackFragment
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, feedbackFragment) //
                .addToBackStack(null)
                .commit();
    }
}
