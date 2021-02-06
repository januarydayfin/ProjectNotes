package com.krayapp.projectnotes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toolbar_main);
        createMainList();
        initToolbar();
    }

    private boolean checkLand() {
        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            return true;
        } else {
            return false;
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                Toast.makeText(this, "Settings Tapped", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_add:
                addNewNote();
                return true;
            case R.id.action_save:
                Toast.makeText(this, "Saved(Не правда)", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    private void addNewNote(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (checkLand()) {
            fragmentTransaction.replace(R.id.landFullFrag, new FillFragment());
        } else {
            createNewNote();
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void createNewNote(){
        Intent fillIntent = new Intent(this, FillActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ListFragment.KEY_MEMORY, null);
        fillIntent.putExtra(ListFragment.KEY_MEMORY, bundle);
        startActivity(fillIntent);
    }
    private void createMainList() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (checkLand()) {
            fragmentTransaction.replace(R.id.landNoteList, new ListFragment());
        } else {
            fragmentTransaction.replace(R.id.listNoteContainer, new ListFragment());
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}