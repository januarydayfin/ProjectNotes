package com.krayapp.projectnotes;

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
import com.krayapp.projectnotes.data.NoteSourceImpl;

public class FillFragment extends Fragment {

    private EditText title;
    private EditText description;
    private EditText date;
    private NoteInfo getNote;

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

    public NoteInfo getGetNote() {
        return getNote;
    }

    public void setGetNote(NoteInfo getNote) {
        this.getNote = getNote;
    }

    // TODO: Rename and change types and number of parameters
    public static FillFragment newInstance(NoteInfo note) {
        FillFragment fragment = new FillFragment();
        Bundle args = new Bundle();
        args.putParcelable(ListFragment.KEY_MEMORY, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getNote = getArguments().getParcelable(ListFragment.KEY_MEMORY);

        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                setDescription(null);
                setDate(null);
                return true;
            case R.id.action_save:
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                savedNote();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        if (getNote != null) {
            populateNote(getNote);
        }
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

    public NoteInfo savedNote() {
        NoteInfo newNote = new NoteInfo(getTitle().getText().toString(), getDescription().getText().toString(), getDate().getText().toString());
        return newNote;
    }

}