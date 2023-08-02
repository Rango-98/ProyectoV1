package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button btnSesion, btnSesion2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSesion = findViewById(R.id.btnSesion);
        btnSesion2 = findViewById(R.id.btnSesion2);

        btnSesion.setOnClickListener(v ->{
            Utilidades utilidades = new Utilidades();
            ingresarApp(utilidades.getUrl());
        });

        btnSesion2.setOnClickListener(v -> startActivity(new Intent(this, RegistrarActivity.class)));
    }

    private void ingresarApp(String url) {
    }
}