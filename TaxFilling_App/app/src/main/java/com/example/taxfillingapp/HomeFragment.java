package com.example.taxfillingapp;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private User selectedUser = null;
    /*private EditText NameEditText;*/

    private UserService userService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        userService = UserService.getInstance(requireContext());

        recyclerView = view.findViewById(R.id.recycler_view);



        adapter = new RecyclerAdapter(new ArrayList<>());
        adapter.setOnUserClickListener(user -> {
            selectedUser = user;
            String email = selectedUser.email;
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                });

                CustomerDetailFragment customerDetailFragment = new CustomerDetailFragment();

                Bundle bundle = new Bundle();
                bundle.putString("email", email);

                customerDetailFragment.setArguments(bundle);

                // Replace the current fragment with CustomerDetailFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, customerDetailFragment);
                transaction.addToBackStack(null); // Optional: add to back stack
                transaction.commit();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchData();
        setupItemTouchHelper();
        return view;
    }

   /* public void insertData() {
        new Thread(() -> {
            String name = NameEditText.getText().toString();
            String username = "user" + name;
            String email = name.toLowerCase() + "@example.com";
            String password = "password123";

            Geo geo = new Geo("40.7128", "-74.0060");
            Address address = new Address("123 Main St", "Apt 4B", "City", "12345", geo);
            String phone = "123-456-7890";
            String website = "www.example.com";
            Company company = new Company("Example Corp", "Tech", "bs");

            User user = new User(0, name, username, email, password, address, phone, website, company);

            userService.insertUser(user, new UserService.OperationCallback() {
                @Override
                public void onOperationCompleted() {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            clearEditTexts();
                            fetchData(); // Fetch updated data after insertion
                        });
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.e("HomeFragment", "Error inserting user", e);
                }
            });
        }).start();
    }


    public void updateData() {
        new Thread(() -> {
            if (selectedUser != null) {
                selectedUser.name = NameEditText.getText().toString();
                userService.updateUser(selectedUser, new UserService.OperationCallback() {
                    @Override
                    public void onOperationCompleted() {
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(() -> {
                                clearEditTexts();
                                fetchData(); // Fetch updated data after updating
                            });
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("HomeFragment", "Error updating user", e);
                    }
                });
            }
        }).start();
    }*/

    private void setupItemTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // We don't want move functionality
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition(); // Get swiped item position
                User userToDelete = adapter.getUserAtPosition(position); // Get the user to delete

                userService.deleteUser(userToDelete, new UserService.OperationCallback() {
                    @Override
                    public void onOperationCompleted() {
                        // Use getActivity().runOnUiThread() to update the UI
                        getActivity().runOnUiThread(() -> {
                            adapter.removeUserAtPosition(position);
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("HomeFragment", "Error deleting user", e);
                    }
                });
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView); // Attach the ItemTouchHelper to the RecyclerView
    }


    private void fetchData() {
        userService.fetchAllUsers(users -> {
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> adapter.setData(users));
            }
        });
    }


/*    private void clearEditTexts() {
        NameEditText.setText("");
    }*/
}
