package com.example.navbar;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Feedback extends Fragment {

    TextView usernameTextView;
    TextView passwordTextView;
    EditText feedbackEditText;
    RadioGroup reviewRadioGroup;
    SeekBar progressSeekBar;
    Button submitFeedbackBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        usernameTextView = view.findViewById(R.id.usernameTextView);
        passwordTextView = view.findViewById(R.id.passwordTextView);
        feedbackEditText = view.findViewById(R.id.feedbackEditText);
        reviewRadioGroup = view.findViewById(R.id.reviewRadioGroup);
        progressSeekBar = view.findViewById(R.id.progress_seekbar);
        submitFeedbackBtn = view.findViewById(R.id.submit_feedback_btn);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String username = bundle.getString("username");
            String password = bundle.getString("password");

            usernameTextView.setText(username);
            passwordTextView.setText(password);
        }

        submitFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });

        return view;
    }

    void submitFeedback() {
        String feedback = feedbackEditText.getText().toString();
        int selectedReviewId = reviewRadioGroup.getCheckedRadioButtonId();
        int progress = progressSeekBar.getProgress();

        if (selectedReviewId == -1) {
            Toast.makeText(getActivity(), "Please select a review", Toast.LENGTH_SHORT).show();
            return;
        }

        String review = "";
        if (selectedReviewId == R.id.review_bad) {
            review = "Bad";
        } else if (selectedReviewId == R.id.review_good) {
            review = "Good";
        } else if (selectedReviewId == R.id.review_very_good) {
            review = "Very Good";
        } else if (selectedReviewId == R.id.review_excellent) {
            review = "Excellent";
        }

        Toast.makeText(getActivity(), "Feedback submitted!\n" +

                "Review: " + review + "\n" +
                "Progress: " + progress + "%", Toast.LENGTH_LONG).show();
    }
}
