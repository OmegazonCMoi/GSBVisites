package com.example.gsbofficiel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsbofficiel.api.ApiService;
import com.example.gsbofficiel.api.Praticien;
import com.example.gsbofficiel.api.RetrofitClientInstance;
import com.example.gsbofficiel.api.Visite;
import com.example.gsbofficiel.api.VisiteAdapter;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PraticienActivity extends AppCompatActivity {

    private TextView nomPrenom;
    private TextView email;
    private String token;
    private RecyclerView recyclerViewVisites;
    private Button buttonCreerVisite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_praticien);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        Log.d("TAG", "onCreate: " + token);
        String praticienId = getIntent().getStringExtra("praticienId");

        recyclerViewVisites = findViewById(R.id.recyclerViewVisites);
        recyclerViewVisites.setLayoutManager(new LinearLayoutManager(this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nomPrenom = findViewById(R.id.tvNomPrenom);
        email = findViewById(R.id.tvEmail);
        buttonCreerVisite = findViewById(R.id.buttonCreerVisite);

        buttonCreerVisite.setOnClickListener(v -> {
            Intent intent1 = new Intent(PraticienActivity.this, CreerVisiteActivity.class);
            intent1.putExtra("token", token);
            intent1.putExtra("praticienId", praticienId);
            intent1.putExtra("visiteurId", getIntent().getStringExtra("visiteurId"));
            startActivity(intent1);
        });

        if (praticienId != null) {
            fetchPraticienDetails(praticienId);
            fetchVisites(praticienId);
        } else {
            Toast.makeText(this, "ID du praticien manquant", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchPraticienDetails(String id) {
        ApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<Praticien> call = apiService.getPraticienById(id, token);

        call.enqueue(new Callback<Praticien>() {
            @Override
            public void onResponse(Call<Praticien> call, Response<Praticien> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Praticien praticien = response.body();
                    nomPrenom.setText(praticien.getPrenom() + " " + praticien.getNom());
                    email.setText(praticien.getEmail());
                } else {
                    Toast.makeText(PraticienActivity.this, "Erreur de récupération du praticien", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Praticien> call, Throwable t) {
                Toast.makeText(PraticienActivity.this, "Erreur API : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchVisites(String praticienId) {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);

        Call<List<Visite>> call = service.getVisitesByPraticienId(praticienId, token);

        call.enqueue(new Callback<List<Visite>>() {
            @Override
            public void onResponse(Call<List<Visite>> call, Response<List<Visite>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Visite> visites = response.body();
                    VisiteAdapter adapter = new VisiteAdapter(visites, visite -> {
                        Intent intent = new Intent(PraticienActivity.this, DetailsVisiteActivity.class);
                        intent.putExtra("visite", new Gson().toJson(visite));
                        intent.putExtra("token", token);
                        startActivity(intent);
                    });
                    recyclerViewVisites.setAdapter(adapter);
                } else {
                    Toast.makeText(PraticienActivity.this, "Aucune visite trouvée", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Visite>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t);
                Toast.makeText(PraticienActivity.this, "Erreur API visites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
