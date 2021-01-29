package com.krayapp.projectnotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ListResourceBundle;

public class FillFragment extends Fragment {

    private TextView title;
    private TextView descriprion;
    private TextView date;
    private NoteInfo getNote;

    public FillFragment() {
        // Required empty public constructor
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
        if(getNote != null){
            populateNote(getNote);
        }

    }

    private void initViews(View view) {
        title = view.findViewById(R.id.title);
        descriprion = view.findViewById(R.id.description);
        date = view.findViewById(R.id.dateView);
    }

    private void populateNote(NoteInfo note) {
        title.setText(note.getTitle());
        descriprion.setText(note.getDescription());
        date.setText(note.getDate());
    }
}