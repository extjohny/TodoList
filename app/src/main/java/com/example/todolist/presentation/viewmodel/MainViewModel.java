package com.example.todolist.presentation.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todolist.data.Note;
import com.example.todolist.data.NoteDatabase;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private final String TAG = "MainViewModel";

    private final NoteDatabase noteDatabase;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<List<Note>> notes = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getInstance(application);
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void refreshList() {
        Disposable disposable = getNotesRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        notes::setValue,
                        t -> Log.d(TAG, "Error with loading list from DB")
                );

        compositeDisposable.add(disposable);
    }

    private Single<List<Note>> getNotesRx() {
        return Single.fromCallable(() -> noteDatabase.notesDao().getNotes());
    }

    public void remove(Note note) {
        Disposable disposable = removeRx(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::refreshList,
                        t -> Log.d(TAG, "Error with loading list from DB")
                );

        compositeDisposable.add(disposable);
    }

    private Completable removeRx(Note note) {
        return Completable.fromAction(() -> noteDatabase.notesDao().remove(note.getId()));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
