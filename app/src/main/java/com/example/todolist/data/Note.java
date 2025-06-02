package com.example.todolist.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private final int id;
    private final String text;
    private final int priority;

    public Note(int id, String text, int priority) {
        this.id = id;
        this.text = text;
        this.priority = priority;
    }

    @Ignore
    public Note(String text, int priority) {
        this(0, text, priority);
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPriority() {
        return priority;
    }

    @NonNull
    @Override
    public String toString() {
        return "Id: " + id + " Text: " + text + " Priority: " + priority;
    }
}
