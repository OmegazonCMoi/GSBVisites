package com.example.gsbofficiel.api;

public class Motif {
    private String _id;
    private String libelle;

    // Constructeur par défaut pour la désérialisation
    public Motif() {}

    // Getters et Setters
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
