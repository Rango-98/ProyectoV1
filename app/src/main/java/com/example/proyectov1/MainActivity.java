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
                if(validarRegistro()){
                    startActivity(new Intent(MainActivity.this, PaginaPrincipalActivity.class));
                }else{
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(t, 1000);
    }

    private boolean validarRegistro(){
        SharedPreferences datosSesion = getSharedPreferences("dato.dat", MODE_PRIVATE);
        return datosSesion.getBoolean("registro", false);
    }
}