package com.krayapp.projectnotes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    private boolean isLandscape;
    static final String KEY_MEMORY = "KEY_MEMORY";

    private ArrayList<NoteInfo> noteStorage = new ArrayList<>();

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

        return inflater.inflate(R.layout.fragment_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        noteFill();
        Adapter adapter = new Adapter(noteStorage);
        adapter.setOnItemClickListener((position, note) -> showCheck(note));
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }


    private void noteFill() { //временный метод заполнения
        noteStorage.add(new NoteInfo("Еда", "Надо приготовить покушоц", "12.12.2012", noteStorage.size()));
        noteStorage.add(new NoteInfo("Покупки", "Греча, Молоко, Мыло", "15.12.2012", noteStorage.size()));
        noteStorage.add(new NoteInfo("Дела", "Украсть у кошки еду", "13.12.2012", noteStorage.size()));
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
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}