package com.example.taxfillingapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class userhomeFragment extends Fragment {

    private UserService userService;

    User user3;
    private TextView userContentTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userhome, container, false);
// Assuming you have a TextView to show user content

        userService = UserService.getInstance(requireContext());

        Button user_updateButton = view.findViewById(R.id.updatedata_user2);

        TextView emailTextView = view.findViewById(R.id.user_emailTextView);
        TextView statusTextView = view.findViewById(R.id.user_statusTextView);
        EditText nameEditText = view.findViewById(R.id.user_nameEditText);
        EditText usernameEditText = view.findViewById(R.id.user_usernameEditText);
        EditText passwordEditText = view.findViewById(R.id.user_passwordEditText);
        EditText companyEditText = view.findViewById(R.id.user_companyEditText);
        EditText streetEditText = view.findViewById(R.id.user_streetEditText);
        EditText zipEditText = view.findViewById(R.id.user_zipEditText);
        EditText cityEditText = view.findViewById(R.id.user_cityEditText);
        EditText countryEditText = view.findViewById(R.id.user_countryEditText);
        EditText phoneEditText = view.findViewById(R.id.user_phoneEditText);
        EditText companyBusinessEditText = view.findViewById(R.id.user_companyBusinessEditText);
        EditText companyPhraseEditText = view.findViewById(R.id.user_companyPhraseEditText);

        // Show email input dialog
        showEmailInputDialog();
        user_updateButton.setOnClickListener(v -> updateData());


        return view;
    }

    private void showEmailInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Enter Email");

        final EditText input = new EditText(requireContext());
        input.setHint("Email");
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String email = input.getText().toString().trim();
            if (!email.isEmpty()) {
                fetchUserByEmail(email);
            } else {
                Toast.makeText(requireContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void fetchUserByEmail(String email) {
        userService.findUserByEmail(email, user -> {
            if (user != null) {
                displayUserContent(user);
            } else {
                requireActivity().runOnUiThread(() ->
                        new AlertDialog.Builder(requireContext())
                                .setTitle("User Not Found")
                                .setMessage("Email does not exist. Please retry or register.")
                                .setPositiveButton("Retry", (dialog, which) -> showEmailInputDialog())
                                .setNegativeButton("Register", (dialog, which) -> navigateToRegistrationFragment())
                                .show()
                );
            }
        });
    }

    private void displayUserContent(User user) {



        requireActivity().runOnUiThread(() -> {
            // Populate TextViews
            TextView emailTextView = requireView().findViewById(R.id.user_emailTextView);
            TextView statusTextView = requireView().findViewById(R.id.user_statusTextView);

            // Populate EditTexts
            EditText nameEditText = requireView().findViewById(R.id.user_nameEditText);
            EditText usernameEditText = requireView().findViewById(R.id.user_usernameEditText);
            EditText passwordEditText = requireView().findViewById(R.id.user_passwordEditText);
            EditText companyEditText = requireView().findViewById(R.id.user_companyEditText);
            EditText streetEditText = requireView().findViewById(R.id.user_streetEditText);
            EditText zipEditText = requireView().findViewById(R.id.user_zipEditText);
            EditText cityEditText = requireView().findViewById(R.id.user_cityEditText);
            EditText countryEditText = requireView().findViewById(R.id.user_countryEditText);
            EditText phoneEditText = requireView().findViewById(R.id.user_phoneEditText);
            EditText companyBusinessEditText = requireView().findViewById(R.id.user_companyBusinessEditText);
            EditText companyPhraseEditText = requireView().findViewById(R.id.user_companyPhraseEditText);

            // Display user details
            emailTextView.setText("Email: " + user.email);
            statusTextView.setText("Status: " + user.status);
            nameEditText.setText(user.name);
            usernameEditText.setText(user.username);
            passwordEditText.setText(user.password);
            companyEditText.setText(user.company != null ? user.company.name : "N/A");
            // Assuming Address class has appropriate getter methods
            if (user.address != null) {
                streetEditText.setText(user.address.street);
                zipEditText.setText(user.address.suite);
                cityEditText.setText(user.address.city);
                countryEditText.setText(user.address.zipcode);
            }
            phoneEditText.setText(user.phone);
            companyBusinessEditText.setText(user.company != null ? user.company.bs: "N/A");
            companyPhraseEditText.setText(user.company != null ? user.company.catchPhrase : "N/A");
        });
    }
    private void navigateToRegistrationFragment() {
        Fragment registrationFragment = new RegistrationFragment(); // Ensure the fragment class name matches
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_user, registrationFragment); // Replace R.id.fragment_container with your container ID
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void updateData() {
        new Thread(() -> {
            if (user3 != null) {
                userService.updateUser(user3, new UserService.OperationCallback() {
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
