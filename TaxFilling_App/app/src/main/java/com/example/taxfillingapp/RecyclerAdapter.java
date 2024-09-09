package com.example.taxfillingapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<User> users;
    private OnUserClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView phoneTextView;

        public TextView cityTextView;

        public TextView statusTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.nametextView);
            phoneTextView = view.findViewById(R.id.phonetextView);
            cityTextView = view.findViewById(R.id.citytextView);
            statusTextView = view.findViewById(R.id.statustextView);
        }
    }

    public RecyclerAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.nameTextView.setText(user.name);
        holder.statusTextView.setText(user.status);
        holder.phoneTextView.setText(user.phone);
        holder.cityTextView.setText(user.address.city);


        int backgroundColor;
        if (user.status!=null) {
            switch (user.status) {
                case "AWAITED":
                    backgroundColor = holder.itemView.getContext().getResources().getColor(R.color.awaited_color);
                    break;
                case "FAILEDTOREACH":
                    backgroundColor = holder.itemView.getContext().getResources().getColor(R.color.failed_to_reach_color);
                    break;
                case "ONBOARDED":
                    backgroundColor = holder.itemView.getContext().getResources().getColor(R.color.onboarded_color);
                    break;
                case "INPROCESS":
                    backgroundColor = holder.itemView.getContext().getResources().getColor(R.color.in_process_color);
                    break;
                case "COMPLETED":
                    backgroundColor = holder.itemView.getContext().getResources().getColor(R.color.completed_color);
                    break;
                case "DENIED":
                    backgroundColor = holder.itemView.getContext().getResources().getColor(R.color.denied_color);
                    break;
                default:
                    backgroundColor = holder.itemView.getContext().getResources().getColor(android.R.color.white); // Default color
                    break;
            }
        } else{
            backgroundColor = holder.itemView.getContext().getResources().getColor(android.R.color.white);
        }

        holder.itemView.setBackgroundColor(backgroundColor);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUserClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }

    public interface OnUserClickListener {
        void onUserClick(User user);
    }

    public void setOnUserClickListener(OnUserClickListener listener) {
        this.listener = listener;
    }

    public User getUserAtPosition(int position) {
        return users.get(position);
    }

    public void setData(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public void removeUserAtPosition(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }
    public void clearData() {
        users.clear();
        notifyDataSetChanged();
    }

}
