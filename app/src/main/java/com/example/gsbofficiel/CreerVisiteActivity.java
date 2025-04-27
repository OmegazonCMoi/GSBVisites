package com.example.gsbofficiel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gsbofficiel.api.ApiService;
import com.example.gsbofficiel.api.Motif;
import com.example.gsbofficiel.api.RetrofitClientInstance;
import com.example.gsbofficiel.api.Visite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreerVisiteActivity extends AppCompatActivity {

    private EditText dateVisiteEditText;
    private EditText commentaireEditText;
    private Spinner motifSpinner;
    private Button createVisiteButton;

    private List<Motif> motifsList = new ArrayList<>();
    private String token;
    private String visiteurId;
    private String praticienId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_visite);

        token = getIntent().getStringExtra("token");
        visiteurId = getIntent().getStringExtra("visiteurId");
        praticienId = getIntent().getStringExtra("praticienId");

        dateVisiteEditText = findViewById(R.id.dateVisiteEditText);
        commentaireEditText = findViewById(R.id.commentaireEditText);
        motifSpinner = findViewById(R.id.motifSpinner);
        createVisiteButton = findViewById(R.id.createVisiteButton);

        fetchMotifs(token);

        createVisiteButton.setOnClickListener(v -> createVisite());
    }

    private void fetchMotifs(String token) {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<Motif[]> call = service.getMotifs(token);

        call.enqueue(new Callback<Motif[]>() {
            @Override
            public void onResponse(Call<Motif[]> call, Response<Motif[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Motif[] motifsArray = response.body();
                    motifsList.clear();
                    List<String> motifLibelles = new ArrayList<>();

                    for (Motif motif : motifsArray) {
                        motifsList.add(motif);
                        motifLibelles.add(motif.getLibelle());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(CreerVisiteActivity.this, android.R.layout.simple_spinner_item, motifLibelles);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    motifSpinner.setAdapter(adapter);

                } else {
                    Toast.makeText(CreerVisiteActivity.this, "Erreur récupération motifs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Motif[]> call, Throwable t) {
                Toast.makeText(CreerVisiteActivity.this, "Erreur connexion motifs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createVisite() {
        String dateString = dateVisiteEditText.getText().toString();
        String commentaire = commentaireEditText.getText().toString();

        if (dateString.isEmpty() || commentaire.isEmpty() || motifSpinner.getSelectedItemPosition() == -1) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Date date = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(dateString);
        } catch (Exception e) {
            Toast.makeText(this, "Date invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        Motif selectedMotif = motifsList.get(motifSpinner.getSelectedItemPosition());

        // Ici on construit à la main le Map pour l'API
        Map<String, Object> visiteData = new HashMap<>();
        SimpleDateFormat sdfApi = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        visiteData.put("dateVisite", sdfApi.format(date));
        visiteData.put("commentaire", commentaire);
        visiteData.put("visiteur", visiteurId);
        visiteData.put("praticien", praticienId);
        visiteData.put("motif", selectedMotif.getId());

        Log.d("TAG", "Visite Data: " + visiteData);

        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = service.createVisite("Bearer " + token, visiteData);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreerVisiteActivity.this, "Visite créée avec succès", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreerVisiteActivity.this, "Erreur lors de la création de la visite", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CreerVisiteActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
