package com.example.gsbofficiel.api;

import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;

import java.util.Date;

public class Visite {
    @SerializedName("_id")
    private String id;
    @SerializedName("dateVisite")
    private Date date;
    private String commentaire;
    @SerializedName("visiteurId")
    private Visiteur visiteur;
    private Praticien praticien;
    private Motif motif;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public Visite(Date date, String commentaire, Visiteur visiteur, Praticien praticien, Motif motif) {
        this.date = date;
        this.commentaire = commentaire;
        this.visiteur = visiteur;
        this.praticien = praticien;
        this.motif = motif;
    }

    public Visite(Date date, String commentaire, String visiteurId, String praticienId, String selectedMotifId) {
        this.date = date;
        this.commentaire = commentaire;
        this.visiteur = new Visiteur();
        this.visiteur.setId(visiteurId);
        this.praticien = new Praticien();
        this.praticien.setId(praticienId);

        this.motif = new Motif(); // création d'un nouvel objet Motif
        this.motif.setId(selectedMotifId); // on lui met l'id qu'on a récupéré
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Visiteur getVisiteur() {
        return visiteur;
    }

    public void setVisiteur(Visiteur visiteur) {
        this.visiteur = visiteur;
    }

    public Praticien getPraticien() {
        return praticien;
    }

    public void setPraticien(Praticien praticien) {
        this.praticien = praticien;
    }

    public Motif getMotif() {
        return motif;
    }

    public void setMotif(Motif motif) {
        this.motif = motif;
    }

    public String getMotifLibelle() {
        return motif != null ? motif.getLibelle() : "Motif non disponible";
    }


    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        formatter.setTimeZone(java.util.TimeZone.getTimeZone("UTC")); // <-- on force en UTC
        String dateStr = (date != null) ? formatter.format(date) : "Date non disponible";
        String commentaireStr = (commentaire != null) ? commentaire : "Pas de commentaire";
        String motifStr = (motif != null && motif.getLibelle() != null) ? motif.getLibelle() : "Motif non disponible";

        return "Date : " + dateStr + "\n" +
                "Commentaire : " + commentaireStr + "\n" +
                "Motif : " + motifStr;
    }
}
