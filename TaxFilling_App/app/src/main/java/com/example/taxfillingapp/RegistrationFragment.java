package com.example.taxfillingapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.List;
import android.util.Pair; // Import Pair class

public class RegistrationFragment extends Fragment {

    // Declare variables for TextViews and EditTexts
    private EditText nameEditText;

    private RecyclerAdapter adapter;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText addressStreetEditText;
    private EditText addressAptEditText;
    private EditText addressCityEditText;
    private EditText addressZipEditText;
    private EditText phoneEditText;
    private EditText websiteEditText;
    private EditText companynameEditText;
    private EditText catchphraseEditText;
    private EditText bsEditText;
    private UserService userService;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        userService = UserService.getInstance(requireContext());

        // Initialize the EditTexts and FloatingActionButton
        nameEditText = view.findViewById(R.id.nameEditText);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        addressStreetEditText = view.findViewById(R.id.addressStreetEditText);
        addressAptEditText = view.findViewById(R.id.addressAptEditText);
        addressCityEditText = view.findViewById(R.id.addressCityEditText);
        addressZipEditText = view.findViewById(R.id.addressZipEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        websiteEditText = view.findViewById(R.id.websiteEditText);
        companynameEditText = view.findViewById(R.id.companynameEditText);
        catchphraseEditText = view.findViewById(R.id.catchphraseEditText);
        bsEditText = view.findViewById(R.id.bsEditText);
        fab = view.findViewById(R.id.fab_add);

        fab.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String addressStreet = addressStreetEditText.getText().toString();
            String addressApt = addressAptEditText.getText().toString();
            String addressCity = addressCityEditText.getText().toString();
            String addressZip = addressZipEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String website = websiteEditText.getText().toString();
            String companyName = companynameEditText.getText().toString();
            String catchphrase = catchphraseEditText.getText().toString();
            String bs = bsEditText.getText().toString();

            // Perform geocoding and get the result
            Pair<Double, Double> geoResult = performGeocoding();
            if (geoResult != null) {
                double latitude = geoResult.first;
                double longitude = geoResult.second;

                // Create the Geo, Address, and Company objects
                Geo geo = new Geo(String.valueOf(latitude), String.valueOf(longitude));
                com.example.taxfillingapp.Address address = new com.example.taxfillingapp.Address(
                        addressStreet,
                        addressApt,
                        addressCity,
                        addressZip,
                        geo
                );
                Company company = new Company(companyName, catchphrase, bs);

                // Create the User object
                User user = new User(
                        0, // auto-generated ID
                        name,
                        username,
                        email,
                        password,
                        address,
                        phone,
                        website,
                        company
                );

                new Thread(() -> {
                    userService.insertUser(user, new UserService.OperationCallback() {
                        @Override
                        public void onOperationCompleted() {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(() -> {
                                    fetchData();
                                    adapter.notifyDataSetChanged();
                                });
                            }
                        }
                        @Override
                        public void onError(Exception e) {
                            Log.e("HomeFragment", "Error inserting user", e);
                        }
                    });

                }).start();

            } else {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Geocoding Result")
                        .setMessage("Failed to find address. Please check and enter a valid address.")
                        .setPositiveButton("OK", null)
                        .show();
            }
            nameEditText.setText("");
            usernameEditText.setText("");
            emailEditText.setText("");
            passwordEditText.setText("");
            addressStreetEditText.setText("");
            addressAptEditText.setText("");
            addressCityEditText.setText("");
            addressZipEditText.setText("");
            phoneEditText.setText("");
            websiteEditText.setText("");
            companynameEditText.setText("");
            catchphraseEditText.setText("");
            bsEditText.setText("");
        });

        return view;
    }


    private Pair<Double, Double> performGeocoding() {
        String addressStreet = addressStreetEditText.getText().toString();
        String addressApt = addressAptEditText.getText().toString();
        String addressCity = addressCityEditText.getText().toString();
        String addressZip = addressZipEditText.getText().toString();
        String fullAddress = addressStreet + ", " + addressCity + ", " + addressZip;

        Geocoder geocoder = new Geocoder(getActivity());
        try {
            List<android.location.Address> addresses = geocoder.getFromLocationName(fullAddress, 1);
            if (addresses != null && !addresses.isEmpty()) {
                android.location.Address address = addresses.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();

                // Convert to Strings
                String latitudeStr = String.valueOf(latitude);
                String longitudeStr = String.valueOf(longitude);

                // Log latitude and longitude
                Log.d("Geocoding", "Latitude: " + latitudeStr + ", Longitude: " + longitudeStr);

                // Create and show AlertDialog
                new AlertDialog.Builder(getActivity())
                        .setTitle("Geocoding Result")
                        .setMessage("Latitude: " + latitudeStr + "\nLongitude: " + longitudeStr)
                        .setPositiveButton("OK", null)
                        .show();

                return new Pair<>(latitude, longitude); // Return latitude and longitude

            } else {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Geocoding Result")
                        .setMessage("No latitude and longitude found. Please check the address.")
                        .setPositiveButton("OK", null)
                        .show();

                return null; // Indicate failure
            }
        } catch (IOException e) {
            e.printStackTrace();
            new AlertDialog.Builder(getActivity())
                    .setTitle("Geocoding Result")
                    .setMessage("Error: " + e.getMessage())
                    .setPositiveButton("OK", null)
                    .show();

            return null; // Indicate failure
        }
    }
    public void fetchData() {
        userService.fetchAllUsers(users ->  getActivity().runOnUiThread(() -> adapter.setData(users)));
    }


}
