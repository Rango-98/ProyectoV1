package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class PaginaPrincipalActivity extends AppCompatActivity {

    private ListView listProducto;
    private TextView txtNombre;
    private String nombre_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        SharedPreferences datosA = getSharedPreferences("sesion", MODE_PRIVATE);
        nombre_usuario = datosA.getString("nombre", "usuario");

        txtNombre = findViewById(R.id.txtNombre);

        txtNombre.setText("Bienvenido: " +  nombre_usuario);
        listProducto = findViewById(R.id.listProducto);
        listProducto.setAdapter(null);


    }


    private void cerrar_Sesion(){
        SharedPreferences.Editor datosSesion = getSharedPreferences("dato.dat", MODE_PRIVATE).edit();
        datosSesion.clear();
        datosSesion.apply();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}