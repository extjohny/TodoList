package com.example.todolist.presentation.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolist.data.Note;
import com.example.todolist.R;
import com.example.todolist.presentation.viewmodel.AddNoteViewModel;

public class AddNoteActivity extends AppCompatActivity {

    private EditText noteTextEditText;
    private RadioButton lowRadioButton;
    private RadioButton middleRadioButton;
    private Button saveNoteButton;

    private AddNoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        bindViews();

        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);

        saveNoteButton.setOnClickListener(button -> {
            Note note = saveNote();
            viewModel.add(note);
        });
        viewModel.getShouldCloseScreen().observe(this, b -> {
            if (b) finish();
        });
    }

    private Note saveNote() {
        int priority;
        if (lowRadioButton.isChecked()) {
            priority = 0;
        } else if (middleRadioButton.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        String text = noteTextEditText.getText().toString();
        return new Note(text, priority);
    }

    private void bindViews() {
        noteTextEditText = findViewById(R.id.editTextEnterNote);
        lowRadioButton = findViewById(R.id.radioButtonLowPriority);
        middleRadioButton = findViewById(R.id.radioButtonMiddlePriority);
        saveNoteButton = findViewById(R.id.buttonSaveNote);
    }

    public static Intent launchAddNoteScreen(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }
}
