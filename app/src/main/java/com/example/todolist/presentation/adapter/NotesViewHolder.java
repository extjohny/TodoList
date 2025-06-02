package com.example.todolist.presentation.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    TextView textViewNote;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewNote = itemView.findViewById(R.id.textViewNote);
    }
}
