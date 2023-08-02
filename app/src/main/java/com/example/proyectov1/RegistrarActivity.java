package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RegistrarActivity extends AppCompatActivity {

    private Button registrar;
    private EditText usuario, correo, contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        usuario = findViewById(R.id.edtCorreo);
        correo = findViewById(R.id.editTextTextEmailAddress2);
        contrasena = findViewById(R.id.edtContrasena2);
        registrar = findViewById(R.id.btnSesion);

        registrar.setOnClickListener(v ->{
            Utilidades utilidades = new Utilidades();
            registrarUsuario(this ,utilidades.getUrl()); // -> registrarusuario.php
        });
    }

    private void registrarUsuario(Context context, String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            Toast.makeText(context, "Usuario registrado con exito", Toast.LENGTH_LONG).show();
        }, error -> {
            Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }
}