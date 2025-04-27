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
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsVisiteActivity extends AppCompatActivity {

    private EditText dateVisiteEditText;
    private EditText commentaireEditText;
    private Spinner motifSpinner;
    private List<Motif> motifsList = new ArrayList<>();
    private Button updateVisiteButton;

    private Visite visite;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_visite);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        Log.d("TAG", "onCreate: " + token);

        dateVisiteEditText = findViewById(R.id.dateVisiteEditText);
        commentaireEditText = findViewById(R.id.commentaireEditText);
        motifSpinner = findViewById(R.id.motifSpinner);
        updateVisiteButton = findViewById(R.id.updateVisiteButton);

        String visiteJson = getIntent().getStringExtra("visite");

        if (visiteJson != null) {
            visite = new Gson().fromJson(visiteJson, Visite.class);
            fillForm(visite);
        } else {
            Toast.makeText(this, "Erreur de chargement de la visite", Toast.LENGTH_SHORT).show();
            finish();
        }

        fetchMotifs(token);

        updateVisiteButton.setOnClickListener(v -> updateVisite());
    }

    private void fillForm(Visite visite) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(visite.getDate());
        dateVisiteEditText.setText(formattedDate);
        commentaireEditText.setText(visite.getCommentaire());
    }

    private void updateVisite() {
        String updatedCommentaire = commentaireEditText.getText().toString();
        String dateString = dateVisiteEditText.getText().toString();

        Date updatedDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            updatedDate = sdf.parse(dateString);
        } catch (Exception e) {
            Toast.makeText(this, "Date invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedPosition = motifSpinner.getSelectedItemPosition();
        Motif selectedMotif = motifsList.get(selectedPosition);

        Visite updatedVisite = new Visite(
                updatedDate,
                updatedCommentaire,
                visite.getVisiteur(),
                visite.getPraticien(),
                selectedMotif
        );

        Log.d("TAG", "updateVisite: " + updatedVisite);

        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<Visite> call = service.updateVisite(visite.getId(), "Bearer " + token, updatedVisite);
        Log.d("TAG", "fetchMotifs: " + call.request().url());

        call.enqueue(new Callback<Visite>() {
            @Override
            public void onResponse(Call<Visite> call, Response<Visite> response) {
                Log.d("TAG", "onResponse: " + response);
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(DetailsVisiteActivity.this, "Visite mise à jour avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailsVisiteActivity.this, "Erreur lors de la mise à jour de la visite", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Visite> call, Throwable t) {
                Toast.makeText(DetailsVisiteActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchMotifs(String token) {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<Motif[]> call = service.getMotifs(token);
        Log.d("TAG", "fetchMotifs: " + call);

        call.enqueue(new Callback<Motif[]>() {
            @Override
            public void onResponse(Call<Motif[]> call, Response<Motif[]> response) {
                Log.d("TAG", "onResponse: " + response);
                if (response.isSuccessful() && response.body() != null) {
                    Motif[] motifsArray = response.body();
                    motifsList.clear();

                    List<String> motifLibelles = new ArrayList<>();
                    int selectedIndex = 0;

                    for (int i = 0; i < motifsArray.length; i++) {
                        Motif motif = motifsArray[i];
                        motifsList.add(motif);
                        motifLibelles.add(motif.getLibelle());

                        // Pré-sélectionner le bon motif
                        if (visite.getMotif() != null && motif.getId() == visite.getMotif().getId()) {
                            selectedIndex = i;
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailsVisiteActivity.this, android.R.layout.simple_spinner_item, motifLibelles);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    motifSpinner.setAdapter(adapter);

                    // Pré-sélection du motif actuel
                    motifSpinner.setSelection(selectedIndex);

                } else {
                    Toast.makeText(DetailsVisiteActivity.this, "Erreur de récupération des motifs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Motif[]> call, Throwable t) {
                Toast.makeText(DetailsVisiteActivity.this, "Erreur connexion motifs", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
