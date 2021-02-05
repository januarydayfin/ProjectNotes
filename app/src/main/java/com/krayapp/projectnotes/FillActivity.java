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
            finish();
        }

        if (savedInstanceState == null) {
            FillFragment fragment = new FillFragment();
            fragment.setArguments((Bundle) Objects.requireNonNull(getIntent().getExtras()).getParcelable(ListFragment.KEY_MEMORY));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fillContainer, fragment).commit();
        }

    }
}