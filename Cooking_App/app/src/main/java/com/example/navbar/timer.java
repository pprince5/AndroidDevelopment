package com.example.navbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class timer extends Fragment {

    private SeekBar timerSeekBar;
    private TextView timerTextView;
    private Button startButton, resetButton, datePickerButton;
    private CountDownTimer countDownTimer;
    private Handler clockHandler;

    private static final long START_TIME_IN_MILLIS = 60000; // 60 seconds
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private boolean mTimerRunning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        timerSeekBar = view.findViewById(R.id.timerSeekBar);
        timerTextView = view.findViewById(R.id.timertextview);
        startButton = view.findViewById(R.id.startButton);
        resetButton = view.findViewById(R.id.resetButton);
        datePickerButton = view.findViewById(R.id.datePickerButton);

        // Initialize SeekBar and set listener
        timerSeekBar.setMax(60); // max time allowed is 60 mins (60 minutes = 60 * 60 seconds)
        timerSeekBar.setProgress(1); // initial progress to avoid 0 division
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress); // update timer display based on SeekBar progress
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }
        });

        // Initialize start button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        // Initialize reset button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        // Initialize date picker button
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        updateTimer(timerSeekBar.getProgress()); // initial timer update based on SeekBar progress

        return view;
    }

    private void showDatePicker() {
        // Show date picker dialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Display selected date as toast
                        String selectedDate = String.format(Locale.getDefault(),
                                "%d/%d/%d", dayOfMonth, month + 1, year);
                        Toast.makeText(requireContext(), "Selected Date: " + selectedDate,
                                Toast.LENGTH_SHORT).show();
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                startButton.setText("Start");
            }
        }.start();

        mTimerRunning = true;
        startButton.setText("Pause");
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        mTimerRunning = false;
        startButton.setText("Start");
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        timerSeekBar.setProgress(1); // reset SeekBar to default
        if (mTimerRunning) {
            pauseTimer();
        }
    }

    private void updateTimer(int minutes) {
        mTimeLeftInMillis = minutes * 60 * 1000; // convert minutes to milliseconds
        updateCountDownText();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(timeLeftFormatted);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
