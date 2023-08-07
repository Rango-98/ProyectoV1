package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;

public class ContactanosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactanos);

        LinearLayout homeBtnco = findViewById(R.id.homeBtnco);
        LinearLayout btn_pedidosco = findViewById(R.id.btn_pedidosco);
        LinearLayout btn_carritoco = findViewById(R.id.btn_carritoco);
        LinearLayout btn_contactosco = findViewById(R.id.btn_contactosco );
        LinearLayout cerrarSesionbtnco = findViewById(R.id.cerrarSesionbtnco);


        homeBtnco.setOnClickListener(v->{
            startActivity(new Intent(this, PaginaPrincipalActivity.class));
            finish();
        });

        btn_pedidosco.setOnClickListener(v ->{
            startActivity(new Intent(this, PedidosActivity.class));
            finish();
        });

        btn_carritoco.setOnClickListener(v->{
            startActivity(new Intent(this, carritoActivity.class));
            finish();
        });

        btn_contactosco.setOnClickListener(v -> {
            startActivity(new Intent(this, ContactanosActivity.class));
            finish();
        });

        cerrarSesionbtnco.setOnClickListener(v ->{
            cerrar_Sesion();
        });
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