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
import com.google.gson.Gson;

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

        Visiteur visiteur = (Visiteur) getIntent().getSerializableExtra("visiteur");
        assert visiteur != null;
        visiteurToken = visiteur.getToken();

        nameTextView.setText("Bienvenue " + visiteur.getPrenom() + " " + visiteur.getNom());

        binding.recyclerViewPraticiens.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), binding.recyclerViewPraticiens, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Praticien praticien = praticiensRecycler[position];
                Intent intent = new Intent(HomeActivity.this, PraticienActivity.class);
                intent.putExtra("praticienId", praticien.getId());
                intent.putExtra("token", visiteurToken);
                intent.putExtra("visiteurId", visiteur.getId());
                startActivity(intent);
            }
        }));

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

            fetchVisiteurDetails(visiteurId, visiteurToken);
            fetchPortefeuillePraticiens(visiteurId, visiteurToken);
        } else {
            Toast.makeText(this, "Visiteur non trouvé", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchVisiteurDetails(String visiteurId, String token) {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<Visiteur> call = service.getVisiteurById(visiteurId, token);  // Assurez-vous que l'API retourne un objet Visiteur

        call.enqueue(new Callback<Visiteur>() {
            @Override
            public void onResponse(Call<Visiteur> call, Response<Visiteur> response) {
                Log.d("TAG", "Reponse : " + response);
                if (response.isSuccessful() && response.body() != null) {
                    Visiteur visiteur = response.body();  // Vérification de l'objet Visiteur
                    Log.d("TAG", "Visiteur récupéré : " + visiteur.getNom() + " " + visiteur.getPrenom());

                    // Vérification des valeurs
                    if (visiteur.getNom() != null && visiteur.getPrenom() != null) {
                        String visiteurInfo = "Bienvenue " + visiteur.getPrenom() + " " + visiteur.getNom();
                        nameTextView.setText(visiteurInfo);
                    } else {
                        Log.d("TAG", "Nom ou prénom manquant");
                        Toast.makeText(HomeActivity.this, "Nom ou prénom manquant", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("TAG", "Réponse incorrecte : " + response.code() + " " + response.message());
                    Toast.makeText(HomeActivity.this, "Erreur de récupération du visiteur", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Visiteur> call, Throwable t) {
                Log.e("TAG", "Échec de l'appel API", t);
                Toast.makeText(HomeActivity.this, "Erreur lors de la connexion API", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchPortefeuillePraticiens(String visiteurId, String token) {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<Praticien[]> call = service.getPortefeuillePraticiens(visiteurId, token);

        call.enqueue(new Callback<Praticien[]>() {
            @Override
            public void onResponse(Call<Praticien[]> call, Response<Praticien[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Praticien[] praticiens = response.body();
                    PraticienAdapter adapter = new PraticienAdapter(praticiens);
                    recyclerView.setAdapter(adapter);
                    praticiensRecycler = praticiens;
                } else {
                    Toast.makeText(HomeActivity.this, "Erreur de récupération du portefeuille", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Praticien[]> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Échec de l’appel API", Toast.LENGTH_SHORT).show();
                Log.e("API_PORTF", "Erreur API", t);
            }
        });
    }
}
