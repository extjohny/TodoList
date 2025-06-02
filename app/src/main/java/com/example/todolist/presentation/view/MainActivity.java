package com.example.todolist.presentation.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.data.Note;
import com.example.todolist.R;
import com.example.todolist.presentation.adapter.NotesAdapter;
import com.example.todolist.presentation.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addNoteButton;
    private RecyclerView notesRecyclerView;
    private NotesAdapter notesAdapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setUpRecyclerView();
        onAddNoteButtonListener();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getNotes().observe(this, notes -> notesAdapter.setNotes(notes));

        itemTouchHelperWork();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.refreshList();
    }

    private void bindViews() {
        addNoteButton = findViewById(R.id.buttonAddNote);
        notesRecyclerView = findViewById(R.id.recyclerViewNotes);
    }

    private void onAddNoteButtonListener() {
        addNoteButton.setOnClickListener(button ->
                startActivity(AddNoteActivity.launchAddNoteScreen(MainActivity.this)));
    }

    private void setUpRecyclerView() {
        notesAdapter = new NotesAdapter();
        notesRecyclerView.setAdapter(notesAdapter);
    }

    private void itemTouchHelperWork() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(
                    @NonNull RecyclerView recyclerView,
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(
                    @NonNull RecyclerView.ViewHolder viewHolder,
                    int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = notesAdapter.getNotes().get(position);
                viewModel.remove(note);
            }
        });
        itemTouchHelper.attachToRecyclerView(notesRecyclerView);
    }
}