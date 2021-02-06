package com.krayapp.projectnotes;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    private ArrayList<NoteInfo> dataSource;

    public Adapter(ArrayList<NoteInfo> dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

