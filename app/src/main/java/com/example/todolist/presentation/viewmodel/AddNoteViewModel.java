package com.example.todolist.presentation.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todolist.data.Note;
import com.example.todolist.data.NoteDatabase;
import com.example.todolist.data.NotesDao;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteViewModel extends AndroidViewModel {

    private final String TAG = "AddNoteViewModel";

    private final NotesDao notesDao;
    private final MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDao = NoteDatabase.getInstance(application).notesDao();
    }

    public void add(Note note) {
        Disposable disposable = addRx(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> shouldCloseScreen.setValue(true),
                        t -> Log.d(TAG, "Error with adding note")
                        );

        compositeDisposable.add(disposable);
    }

    private Completable addRx(Note note) {
        return Completable.fromAction(() -> notesDao.add(note));
    }

    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
