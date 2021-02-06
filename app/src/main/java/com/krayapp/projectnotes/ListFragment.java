package com.krayapp.projectnotes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    private boolean isLandscape;
    static final String KEY_MEMORY = "KEY_MEMORY";
    static final String SHARED_PREF = "KEY_PREF";

    private TextView tw1;
    private TextView tw2;
    private TextView tw3;
    private Button addButton;

    private ArrayList <NoteInfo> noteStorage = new ArrayList<>();

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        tw1 = view.findViewById(R.id.note1);
        tw2 = view.findViewById(R.id.note2);
        tw3 = view.findViewById(R.id.note3);
        addButton = view.findViewById(R.id.addButton);
        noteFill();
    }



    private void noteFill() { //временный метод заполнения
        clickListeners();
        int fillCounter = 0;
        noteStorage.add(new NoteInfo("Еда", "Надо приготовить покушоц", "12.12.2012", noteStorage.size()));
        noteStorage.add(new NoteInfo("Покупки", "Греча, Молоко, Мыло", "15.12.2012", noteStorage.size()));
        noteStorage.add(new NoteInfo("Дела", "Украсть у кошки еду", "13.12.2012", noteStorage.size()));
        /*tw1.setText(String.format("%s\n%s\n%s", noteStorage.get(fillCounter).getTitle(), noteStorage.get(fillCounter).getDate(), noteStorage.get(fillCounter).getDescription()));
        fillCounter++;
        tw2.setText(String.format("%s\n%s\n%s", noteStorage.get(fillCounter).getTitle(), noteStorage.get(fillCounter).getDate(), noteStorage.get(fillCounter).getDescription()));
        fillCounter++;
        tw3.setText(String.format("%s\n%s\n%s", noteStorage.get(fillCounter).getTitle(), noteStorage.get(fillCounter).getDate(), noteStorage.get(fillCounter).getDescription()));*/
    }

    private void clickListeners() {
        addButton.setOnClickListener(v -> {
            showCheck(null);
        });
        tw1.setOnClickListener(v -> showCheck(noteStorage.get(0)));
        tw1.setOnClickListener(v -> showCheck(noteStorage.get(1)));
        tw1.setOnClickListener(v -> showCheck(noteStorage.get(2)));
    }

    private void showCheck(NoteInfo note) {
        if (isLandscape) {
            showLand(note);
        } else {
            showPort(note);
        }
    }

    private void showPort(NoteInfo note) {
        Intent fillIntent = new Intent(getContext(), FillActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_MEMORY, note);
        fillIntent.putExtra(KEY_MEMORY, bundle);
        startActivity(fillIntent);
    }

    private void showLand(NoteInfo note) {
        FillFragment fillFrag = FillFragment.newInstance(note);
        FragmentActivity context = getActivity();
        if (context != null) {
            FragmentManager fragmentManager = context.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.landFullFrag, fillFrag);
            fragmentTransaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
            fragmentTransaction.commit();
        }
    }


}