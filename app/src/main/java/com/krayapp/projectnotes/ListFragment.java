package com.krayapp.projectnotes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krayapp.projectnotes.data.NoteInfo;
import com.krayapp.projectnotes.data.NoteSource;
import com.krayapp.projectnotes.data.NoteSourceImpl;


public class ListFragment extends Fragment implements OnRegisterMenu {
    private boolean isLandscape;
    static final String KEY_MEMORY = "KEY_MEMORY";
    private NoteSource data;
    private Adapter adapter;
    private RecyclerView recyclerView;


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
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
            inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(getContext(), "SettingsTapped", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_add:
                addNewNoteLand();
                return true;
            case R.id.action_save:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewNoteLand() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentTransaction.replace(R.id.landFullFrag, new FillFragment());
        } else {
            createNewNote();
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void createNewNote() {
        Intent fillIntent = new Intent(getActivity(), FillActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ListFragment.KEY_MEMORY, null);
        fillIntent.putExtra(ListFragment.KEY_MEMORY, bundle);
        startActivity(fillIntent);
    }

    private void initViews(View view) {
        data = new NoteSourceImpl().init();
        recyclerView = view.findViewById(R.id.recycler);
        adapter = new Adapter(data, this);
        adapter.setOnItemClickListener((position, note) -> showCheck(note));
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
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

    @Override
    public void onRegister(View view) {
        registerForContextMenu(view);
    }
}