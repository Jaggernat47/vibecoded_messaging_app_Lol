package com.example.practiceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactListRecyclerViewAdapter extends RecyclerView.Adapter<ContactListRecyclerViewAdapter.ViewHolder> {
    public List<Contact> contacts = new ArrayList<>();
    private final OnItemClickListener listener;

    public ContactListRecyclerViewAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.textName.setText(contact.Name);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(contact);
        });
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
        }
    }
}