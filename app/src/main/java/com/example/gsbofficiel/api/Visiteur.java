package com.example.gsbofficiel.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Visiteur implements Serializable {
    @SerializedName("userId")
    private String id;
    @SerializedName("nom")
    private String nom;
    @SerializedName("prenom")
    private String prenom;
    @SerializedName("email")
    private String email;
    @SerializedName("telephone")
    private String telephone;
    @SerializedName("date_embauche")
    private String dateEmbauche;
    @SerializedName("login")
    private String login;
    @SerializedName("token")
    private String token;
    @SerializedName("visites")
    private Visite visites[];
    private Praticien[] portefeuillePraticiens;
    public Visiteur(String id, String nom, String prenom, String email, String password, String telephone, String token) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.token = token;
    }

    public Visiteur() {

    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getToken() {
        return token;
    }

    public void chargerPortefeuille(Callback<Praticien[]> callback) {
        ApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<Praticien[]> call = apiService.getPortefeuillePraticiens(this.id, "Bearer " + this.token);
        call.enqueue(callback);
    }
}
