package com.example.gsbofficiel.api;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("visiteurs/login")
    Call<Visiteur> getToken(@Body Map<String, String> credentials);

    @GET("visiteurs/{id}")
    Call<Visiteur> getVisiteurById(@Path("id") String id, @Header("Authorization") String token);

    @GET("visites")
    Call<Visite[]> getVisites(@Header("Authorization") String token);

    @GET("visiteurs/{idVisiteur}/portefeuillePraticiens")
    Call<Praticien[]> getPortefeuillePraticiens(
            @Path("idVisiteur") String idVisiteur,
            @Header("Authorization") String token
    );

    @GET("praticiens/{id}")
    Call<Praticien> getPraticienById(
            @Path("id") String id,
            @Header("Authorization") String token
    );

    @GET("praticiens/{id}/visites")
    Call<List<Visite>> getVisitesByPraticienId(
            @Path("id") String praticienId,
            @Header("Authorization") String token
    );

    @GET("visites/{id}")
    Call<Visite> getVisiteById(
            @Path("id") String id,
            @Header("Authorization") String token
    );

    @PUT("visites/{id}")
    Call<Visite> updateVisite(
            @Path("id") String id,
            @Header("Authorization") String token,
            @Body Visite visite
    );

    @GET("motifs")
    Call<Motif[]> getMotifs(@Header("Authorization") String token);

    @POST("visites")
    Call<Void> createVisite(@Header("Authorization") String token, @Body Map<String, Object> visite);
}