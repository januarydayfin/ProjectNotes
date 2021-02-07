package com.krayapp.projectnotes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
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
    private FillFragment fillFragment;

    FragmentManager fragmentManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        fragmentManager = getActivity().getSupportFragmentManager();
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.deleteNotes:
                //delete
                return true;
            case R.id.editNote:
                //edit
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                for (int i = 0; i < data.size(); i++) {
                    System.out.println(data.getNoteInfo(i).getTitle());
                }
                return true;
            case R.id.action_refresh:
                if (fillFragment != null){
                    data.addNoteInfo(fillFragment.savedNote());
                    adapter.notifyItemInserted(data.size()-1);
                }
                return true;
            case R.id.action_add:
                addNewNote();
                return true;
            case R.id.action_save:
                Toast.makeText(getContext(), "Nothing to save", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewNote() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentTransaction.replace(R.id.landFullFrag, new FillFragment());
        } else {
            createNewPortNote();
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void createNewPortNote() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fillFragment = new FillFragment();
        fragmentTransaction.replace(R.id.mainPortContainer, fillFragment);
        fragmentTransaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
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
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_MEMORY, note);
        FillFragment fillFragment = new FillFragment();
        fillFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.mainPortContainer, fillFragment);
        fragmentTransaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void showLand(NoteInfo note) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FillFragment fillFrag = FillFragment.newInstance(note);
        FragmentActivity context = getActivity();
        if (context != null) {
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