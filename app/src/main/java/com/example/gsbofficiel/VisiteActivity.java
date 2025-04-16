package com.example.gsbofficiel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsbofficiel.api.ApiService;
import com.example.gsbofficiel.api.RetrofitClientInstance;
import com.example.gsbofficiel.api.Visite;
import com.example.gsbofficiel.api.VisiteAdapter;
import com.example.gsbofficiel.api.Visiteur;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisiteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewVisites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_visite);

        Visiteur visiteur = (Visiteur) getIntent().getSerializableExtra("visiteur");

        ImageView iconPraticien = findViewById(R.id.iconPraticiens);
        iconPraticien.setOnClickListener(v -> {
            Intent intent = new Intent(VisiteActivity.this, HomeActivity.class);
            intent.putExtra("token", visiteur.getToken());
            startActivity(intent);
        });

        ImageView iconVisite = findViewById(R.id.iconVisites);
        iconVisite.setOnClickListener(v -> {
            Intent intent = new Intent(VisiteActivity.this, VisiteActivity.class);
            intent.putExtra("token", visiteur.getToken());
            startActivity(intent);
        });

        recyclerViewVisites = findViewById(R.id.recyclerViewVisites);
        recyclerViewVisites.setLayoutManager(new LinearLayoutManager(this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ex : récupère le token transmis via l'intent
        String token = getIntent().getStringExtra("token");
        if (token != null) {
            fetchVisites("Bearer " + token);
        } else {
            Toast.makeText(this, "Token non trouvé", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchVisites(String token) {
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        Call<Visite[]> call = service.getVisites(token); // Assure-toi que cette méthode existe

        call.enqueue(new Callback<Visite[]>() {
            @Override
            public void onResponse(Call<Visite[]> call, Response<Visite[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Visite[] visites = response.body();
                    VisiteAdapter adapter = new VisiteAdapter(visites);
                    recyclerViewVisites.setAdapter(adapter);
                } else {
                    Toast.makeText(VisiteActivity.this, "Erreur de récupération des visites", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Visite[]> call, Throwable t) {
                Toast.makeText(VisiteActivity.this, "Erreur API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}