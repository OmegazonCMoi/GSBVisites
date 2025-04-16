package com.example.gsbofficiel.api;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsbofficiel.R;

public class PraticienAdapter extends RecyclerView.Adapter<PraticienAdapter.PraticienViewHolder> {

    private final Praticien[] praticiens;

    public PraticienAdapter(Praticien[] praticiens) {
        this.praticiens = praticiens;
    }

    @NonNull
    @Override
    public PraticienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_praticien, parent, false);
        return new PraticienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PraticienViewHolder holder, int position) {
        Praticien praticien = praticiens[position];
        holder.nomTextView.setText("Nom : " + praticien.getNom() + " " + praticien.getPrenom());
        holder.villeTextView.setText("Ville : " + praticien.getVille());
    }

    @Override
    public int getItemCount() {
        return praticiens.length;
    }

    static class PraticienViewHolder extends RecyclerView.ViewHolder {
        TextView nomTextView;
        TextView villeTextView;

        public PraticienViewHolder(@NonNull View itemView) {
            super(itemView);
            nomTextView = itemView.findViewById(R.id.textViewNom);
            villeTextView = itemView.findViewById(R.id.textViewVille);
        }
    }
}
