package com.krayapp.projectnotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FillFragment extends Fragment {


    public FillFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FillFragment newInstance(String param1, String param2) {
        FillFragment fragment = new FillFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fill, container, false);
    }
}