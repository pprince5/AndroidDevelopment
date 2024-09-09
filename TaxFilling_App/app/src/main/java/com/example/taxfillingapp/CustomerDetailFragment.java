package com.example.taxfillingapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class CustomerDetailFragment extends Fragment {

    private EditText nameTextView;
    private EditText usernameTextView;
    private EditText emailTextView;
    private EditText passwordTextView;

    private EditText statusTextView;
    private EditText addressTextView;
    private EditText phoneTextView;
    private EditText websiteTextView;
    private EditText companyTextView;

    User selecteduser2;

    private UserService userService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_detail, container, false);

        userService = UserService.getInstance(requireContext());

        Button updateButton = view.findViewById(R.id.updatedata_user);

        // Initialize the TextViews
        nameTextView = view.findViewById(R.id.name_value_text_view);
        usernameTextView = view.findViewById(R.id.username_value_text_view);
        emailTextView = view.findViewById(R.id.email_value_text_view);
        passwordTextView = view.findViewById(R.id.password_value_text_view);
        addressTextView = view.findViewById(R.id.address_value_text_view);
        phoneTextView = view.findViewById(R.id.phone_value_text_view);
        websiteTextView = view.findViewById(R.id.website_value_text_view);
        companyTextView = view.findViewById(R.id.company_value_text_view);
        statusTextView = view.findViewById(R.id.status_value_text_view);

        // Extract email from arguments and display it
        Bundle args = getArguments();
        if (args != null) {
            String email = args.getString("email");
            if (email != null) {
                emailTextView.setText(email);
                findUserByEmail(email);
            }
        }
        updateButton.setOnClickListener(v -> updateData());



        return view;
    }
    private void findUserByEmail(String email) {
        userService.findUserByEmail(email, user -> {
            if (user != null) {

                selecteduser2 = user;

                // Populate the views with user data
                getActivity().runOnUiThread(() -> populateViews(user));
            } else {
                // Handle case where no user was found
                getActivity().runOnUiThread(() -> {
                    emailTextView.setText("No user found with email: " + email);
                });
            }
        });
    }
    private void populateViews(User user) {
        nameTextView.setText(user.name);
        usernameTextView.setText(user.username);
        emailTextView.setText(user.email);
        passwordTextView.setText(user.password);
        if(user.status!=null) {
            statusTextView.setText(user.status.toString());
        }
        else{
            statusTextView.setText("AWAITED");
        }
        addressTextView.setText(user.address.toString());
        phoneTextView.setText(user.phone);
        websiteTextView.setText(user.website);
        companyTextView.setText(user.company.toString());
    }
    public void updateData() {
        new Thread(() -> {
            if (selecteduser2 != null) {
                selecteduser2.status = statusTextView.getText().toString();
                userService.updateUser(selecteduser2, new UserService.OperationCallback() {
                    @Override
                    public void onOperationCompleted() {
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("HomeFragment", "Error updating user", e);
                    }
                });
            }
        }).start();
    }

}
