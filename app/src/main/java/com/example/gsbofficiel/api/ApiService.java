package com.example.gsbofficiel.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("visiteurs/login")
    Call<Visiteur> getToken(@Body Map<String, String> credentials);

    @GET("visiteurs/{id}")
    Call<Visiteur> getVisiteur(@Path("id") String id, @Header("Authorization") String token);

    @GET("praticiens")
    Call<Praticien[]> getPraticiens(@Header("Authorization") String token);

    @GET("visites")
    Call<Visite[]> getVisites(@Header("Authorization") String token);
}