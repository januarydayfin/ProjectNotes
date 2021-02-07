package com.krayapp.projectnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<NoteInfo> dataSource;
    private MyClickListener myClickListener;

    public Adapter(ArrayList<NoteInfo> dataSource) {
        this.dataSource = dataSource;
    }

    public void setOnItemClickListener(MyClickListener itemClickListener) {
        myClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.frag_list_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(dataSource.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, NoteInfo note);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleList);
            description = itemView.findViewById(R.id.descriptionList);
            date = itemView.findViewById(R.id.dateList);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                myClickListener.onItemClick(position, dataSource.get(position));
            });
        }

        private void onBind(NoteInfo note) {
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            date.setText(note.getDate());
        }
    }
}

