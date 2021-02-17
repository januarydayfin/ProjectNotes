package com.krayapp.projectnotes;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.krayapp.projectnotes.data.NoteInfo;
import com.krayapp.projectnotes.data.NoteSource;
import com.krayapp.projectnotes.data.NoteSourceFirebaseImpl;
import com.krayapp.projectnotes.dialog.BottomSheetDeleteDialog;
import com.krayapp.projectnotes.dialog.OnDialogListener;
import com.krayapp.projectnotes.observer.Publisher;


public class ListFragment extends Fragment implements OnRegisterMenu {
    private static final int MY_DEFAULT_DURATION = 1000;
    private boolean isLandscape;
    static final String KEY_MEMORY = "KEY_MEMORY";
    private NoteSource data;
    private boolean moveToFirstPosition;
    private Adapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    private FragmentManager fragmentManager;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initViews(view);
        setHasOptionsMenu(true);
        data = new NoteSourceFirebaseImpl().init(cardsData -> adapter.notifyDataSetChanged());
        adapter.setDataSource(data);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId())
        || super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }


    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recycler);
        adapter = new Adapter(this);
        adapter.setOnItemClickListener((position, note) -> showCheck(note));
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);

        if (moveToFirstPosition && data.size() > 0){
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }
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

    private boolean checkLand(Activity activity) {
        return activity.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }
    @Override
    public void onRegister(View view) {
        registerForContextMenu(view);
    }
    private boolean onItemSelected(int menuItemId){
        switch (menuItemId){
            case R.id.action_add:
                if(checkLand(getActivity())){
                    navigation.addMainLandFragment(FillFragment.newInstance(),true);
                }else{
                    navigation.addMainFragment(FillFragment.newInstance(), true);
                }
                publisher.subscribe(noteInfo -> {
                    data.addNoteInfo(noteInfo);
                    adapter.notifyItemInserted(data.size() - 1);
                    moveToFirstPosition = true;
                });
                return true;
            case R.id.action_edit:
                final int updatePosition = adapter.getMenuPosition();
                if(checkLand(getActivity())){
                    navigation.addMainLandFragment(FillFragment.newInstance(),true);
                }else{
                    navigation.addMainFragment(FillFragment.newInstance(), true);
                }
                publisher.subscribe(noteInfo -> {
                    data.updateNoteInfo(updatePosition, noteInfo);
                    adapter.notifyItemChanged(updatePosition);
                });
                return true;
            case R.id.action_delete:
                BottomSheetDeleteDialog bottomSheetDeleteDialog = new BottomSheetDeleteDialog();
                bottomSheetDeleteDialog.setOnDialogListener(() -> {
                    int deletePosition = adapter.getMenuPosition();
                    data.deleteNoteInfo(deletePosition);
                    adapter.notifyItemRemoved(deletePosition);
                });
                bottomSheetDeleteDialog.show(fragmentManager,"BottomSheetDialogTag");
                return true;
            case R.id.action_deleteall:
                data.clearNoteInfo();
                adapter.notifyDataSetChanged();
                return true;

            case R.id.action_auth:
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainPortContainer, new AuthFragment());
                fragmentTransaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
                return true;
        }
        return false;
    }
}