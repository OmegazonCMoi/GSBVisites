package com.example.gsbofficiel.api;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsbofficiel.R;

public class VisiteAdapter extends RecyclerView.Adapter<VisiteAdapter.VisiteViewHolder> {

    private final Visite[] visites;

    public VisiteAdapter(Visite[] visites) {
        this.visites = visites;
    }

    @NonNull
    @Override
    public VisiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visite, parent, false);
        return new VisiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisiteViewHolder holder, int position) {
        Visite visite = visites[position];
        holder.dateTextView.setText("Date : " + visite.getDate());
        holder.motifTextView.setText("Motif : " + visite.getMotif());
    }

    @Override
    public int getItemCount() {
        return visites.length;
    }

    static class VisiteViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView motifTextView;

        public VisiteViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            motifTextView = itemView.findViewById(R.id.textViewMotif);
        }
    }
}
