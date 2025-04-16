package com.example.gsbofficiel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsbofficiel.api.ApiService;
import com.example.gsbofficiel.api.Praticien;
import com.example.gsbofficiel.api.PraticienAdapter;
import com.example.gsbofficiel.api.RetrofitClientInstance;
import com.example.gsbofficiel.api.Visiteur;
import com.example.gsbofficiel.databinding.ActivityHomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private TextView nameTextView;
    private RecyclerView recyclerView;
    private Praticien[] praticiensRecycler;
    private String visiteurToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding;
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        nameTextView = findViewById(R.id.nameTextView);
        recyclerView = findViewById(R.id.recyclerViewPraticiens);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.recyclerViewPraticiens.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), binding.recyclerViewPraticiens, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Praticien praticien = praticiensRecycler[position];

            }
        }));

        Visiteur visiteur = (Visiteur) getIntent().getSerializableExtra("visiteur");

        ImageView iconPraticien = findViewById(R.id.iconPraticiens);
        iconPraticien.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            intent.putExtra("token", visiteur.getToken());
            startActivity(intent);
        });

        ImageView iconVisite = findViewById(R.id.iconVisites);
        iconVisite.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, VisiteActivity.class);
            intent.putExtra("token", visiteur.getToken());
            startActivity(intent);
        });

        if (visiteur != null) {
            String visiteurId = visiteur.getId();
            visiteurToken = "Bearer " + visiteur.getToken();
            nameTextView.setText("ID du Visiteur : " + visiteurId + "\nToken du Visiteur : " + visiteurToken);

            fetchVisiteurDetails(visiteurId, visiteurToken);
            fetchPraticiens(visiteurToken);
        } else {
            Toast.makeText(this, "Visiteur non trouvé", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchVisiteurDetails(String visiteurId, String token) {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<Visiteur> call = service.getVisiteur(visiteurId, token);

        call.enqueue(new Callback<Visiteur>() {
            @Override
            public void onResponse(Call<Visiteur> call, Response<Visiteur> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Visiteur visiteur = response.body();
                    String visiteurInfo = "Bienvenue " + visiteur.getPrenom() + " " + visiteur.getNom();
                    nameTextView.setText(visiteurInfo);
                } else {
                    Toast.makeText(HomeActivity.this, "Erreur de récupération des informations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Visiteur> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Erreur lors de la connexion API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchPraticiens(String token) {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<Praticien[]> call = service.getPraticiens(token);

        call.enqueue(new Callback<Praticien[]>() {

            @Override
            public void onResponse(Call<Praticien[]> call, Response<Praticien[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Praticien[] praticiens = response.body();
                    PraticienAdapter adapter = new PraticienAdapter(praticiens);
                    recyclerView.setAdapter(adapter);
                    praticiensRecycler = praticiens;
                } else {
                    Toast.makeText(HomeActivity.this, "Erreur de récupération des praticiens", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Praticien[]> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Erreur lors de la connexion API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
