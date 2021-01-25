package com.krayapp.projectnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

import java.util.Objects;

public class FillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Если устройство перевернули в альбомную ориентацию, то надо эту activity закрыть
            finish();
        }

        if (savedInstanceState == null) {
            // Если эта activity запускается первый раз
            // то перенаправим параметр фрагменту
            FillFragment fragment = new FillFragment();
            fragment.setArguments((Bundle) Objects.requireNonNull(getIntent().getExtras()).getParcelable(ListFragment.KEY_MEMORY));
            // Добавим фрагмент на activity
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fillContainer, fragment).commit();
        }

    }
}