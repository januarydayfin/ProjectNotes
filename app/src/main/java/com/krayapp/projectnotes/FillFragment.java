package com.krayapp.projectnotes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.krayapp.projectnotes.data.NoteInfo;
import com.krayapp.projectnotes.observer.Publisher;

public class FillFragment extends Fragment {

    private EditText title;
    private EditText description;
    private EditText date;
    private NoteInfo noteInfo;
    private Publisher publisher;

    public EditText getTitle() {
        return title;
    }

    public void setTitle(EditText title) {
        this.title = title;
    }

    public EditText getDescription() {
        return description;
    }

    public void setDescription(EditText description) {
        this.description = description;
    }

    public EditText getDate() {
        return date;
    }

    public void setDate(EditText date) {
        this.date = date;
    }

    public NoteInfo getNoteInfo() {
        return noteInfo;
    }

    public void setNoteInfo(NoteInfo noteInfo) {
        this.noteInfo = noteInfo;
    }

    // TODO: Rename and change types and number of parameters
    public static FillFragment newInstance(NoteInfo note) {
        FillFragment fragment = new FillFragment();
        Bundle args = new Bundle();
        args.putParcelable(ListFragment.KEY_MEMORY, note);
        fragment.setArguments(args);
        return fragment;
    }

    public static FillFragment newInstance() {
        return new FillFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noteInfo = getArguments().getParcelable(ListFragment.KEY_MEMORY);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setHasOptionsMenu(true);
        if (noteInfo != null) {
            populateNote(noteInfo);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        noteInfo = collectNoteInfo();
    }

    private NoteInfo collectNoteInfo() {
        String title = this.getTitle().getText().toString();
        String description = this.getDescription().getText().toString();
        String date = this.getDate().getText().toString();
        return new NoteInfo(title,description,date);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(noteInfo);
    }

    private void initViews(View view) {
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        date = view.findViewById(R.id.dateView);
    }

    private void populateNote(NoteInfo note) {
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        date.setText(note.getDate());
    }

}