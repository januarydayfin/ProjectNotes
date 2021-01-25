package com.krayapp.projectnotes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;


public class ListFragment extends Fragment {
    private boolean isLandscape;
    static final String KEY_MEMORY = "KEY_MEMORY";

    private TextView tw1;
    private TextView tw2;
    private TextView tw3;
    private NoteInfo note1;
    private NoteInfo note2;
    private NoteInfo note3;

    public ListFragment() {
        // Required empty public constructor
    }

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
       /* if (getArguments() != null) {
        }*/
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
        noteFill();
    }

    private void noteFill() { //временный метод заполнения
        clickListeners();
        note1 = new NoteInfo("Еда", "Надо приготовить покушоц", "12.12.2012", 1);
        note2 = new NoteInfo("Покупки", "Греча, Молоко, Мыло", "15.12.2012", 2);
        note3 = new NoteInfo("Дела", "Украсть у кошки еду", "13.12.2012", 3);
        tw1.setText(String.format("%s\n%s\n%s", note1.getTitle(), note1.getDescription(), note1.getDate()));
        tw2.setText(String.format("%s\n%s\n%s", note2.getTitle(), note2.getDescription(), note2.getDate()));
        tw3.setText(String.format("%s\n%s\n%s", note3.getTitle(), note3.getDescription(), note3.getDate()));
    }

    private void clickListeners() {
        tw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheck(note1);
            }
        });
        tw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheck(note2);
            }
        });
        tw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheck(note3);
            }
        });
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
        if(context!=null){
            FragmentManager fragmentManager = context.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.landFullFrag, fillFrag);
            fragmentTransaction.setTransition((fragmentTransaction.TRANSIT_FRAGMENT_FADE));
            fragmentTransaction.commit();
        }
    }

}