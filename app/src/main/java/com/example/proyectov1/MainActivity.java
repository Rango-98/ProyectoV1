package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimerTask t = new  TimerTask(){
            @Override
            public void run(){
                if(sesionGuardada()){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }else{
                    startActivity(new Intent(MainActivity.this, PaginaPrincipalActivity.class));
                }
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(t, 2000);
    }

    private boolean sesionGuardada() {
        SharedPreferences datosSesion = getSharedPreferences("datos.dat", MODE_PRIVATE);
        return datosSesion.getBoolean("sesion", false);
    }
}