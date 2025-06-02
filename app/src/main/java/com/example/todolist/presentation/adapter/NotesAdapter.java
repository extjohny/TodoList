package com.example.todolist.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.data.Note;
import com.example.todolist.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    private List<Note> notes = new ArrayList<>();

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_item,
                parent,
                false
        );
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        int colorResId;
        switch (note.getPriority()) {
            case 0:
                colorResId = R.color.green;
                break;
            case 1:
                colorResId = R.color.yellow;
                break;
            default:
                colorResId = R.color.red;
                break;
        }
        int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
        holder.textViewNote.setBackgroundColor(color);
        holder.textViewNote.setText(note.getText());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
