package com.example.gsbofficiel.api;

public class Visite {
    private String date;
    private String commentaire;
    private Visiteur visiteur;
    private String praticien;
    private String motif;

    // Constructeurs, getters et setters
    public Visite(String date, String commentaire, Visiteur visiteur, String visite, String praticien, String motif) {
        this.date = date;
        this.commentaire = commentaire;
        this.visiteur = visiteur;
        this.praticien = praticien;
        this.motif = motif;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getPraticien() {
        return praticien;
    }

    public void setPraticien(String praticien) {
        this.praticien = praticien;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
