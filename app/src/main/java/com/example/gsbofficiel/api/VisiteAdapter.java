package com.example.gsbofficiel.api;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsbofficiel.R;

import java.util.List;

public class VisiteAdapter extends RecyclerView.Adapter<VisiteAdapter.ViewHolder> {
    private List<Visite> visites;
    private OnVisiteClickListener listener;

    public interface OnVisiteClickListener {
        void onVisiteClick(Visite visite);
    }

    public VisiteAdapter(List<Visite> visites, OnVisiteClickListener listener) {
        this.visites = visites;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_visite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Visite visite = visites.get(position);
        holder.date.setText("Date : " + visite.getDate());
        holder.commentaire.setText("Commentaire : " + visite.getCommentaire());
        holder.motif.setText("Motif : " + visite.getMotifLibelle());

        holder.itemView.setOnClickListener(v -> listener.onVisiteClick(visite));
    }

    @Override
    public int getItemCount() {
        return visites.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, commentaire, motif;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tvDate);
            commentaire = itemView.findViewById(R.id.tvCommentaire);
            motif = itemView.findViewById(R.id.tvMotif);
        }
    }
}
