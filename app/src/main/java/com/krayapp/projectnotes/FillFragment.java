package com.krayapp.projectnotes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.krayapp.projectnotes.data.NoteInfo;
import com.krayapp.projectnotes.observer.Publisher;

import java.util.Calendar;
import java.util.Date;

public class FillFragment extends Fragment {

    private EditText title;
    private EditText description;
    private NoteInfo noteInfo;
    private Publisher publisher;
    private DatePicker datePicker;

    public EditText getTitle() {
        return title;
    }

    public EditText getDescription() {
        return description;
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
        Date date = getDateFromDatePicker();
        return new NoteInfo(title,description,date);
    }

    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(noteInfo);
    }

    private void initViews(View view) {
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        datePicker = view.findViewById(R.id.input_date);
    }

    private void populateNote(NoteInfo noteInfo) {
        title.setText(noteInfo.getTitle());
        description.setText(noteInfo.getDescription());
        initDatePicker(noteInfo.getDate());
    }
    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }


}