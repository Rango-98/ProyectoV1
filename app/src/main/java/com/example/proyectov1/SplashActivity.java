package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectov1.clases.Pedidos;

import java.lang.annotation.Annotation;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private final String ID_CANAL = "NOTIFICATION";
    private final String canal = "notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //animaciones
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        TextView textView = findViewById(R.id.txtTitulo3);
        ImageView imageView = findViewById(R.id.imgCompra);

        textView.setAnimation(animacion2);
        imageView.setAnimation(animacion1);


        NotificationChannel canalNotificacion = new NotificationChannel(ID_CANAL, canal, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(canalNotificacion );

        NotificationCompat.Builder crearNotificacion = new NotificationCompat.Builder(this, ID_CANAL);
        crearNotificacion.setSmallIcon(R.drawable.shopping);
        crearNotificacion.setContentTitle("Refacciones Tony");
        crearNotificacion.setContentText("Tu compra se a realizado conn exito");
        crearNotificacion.setColor(Color.BLACK);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,PaginaPrincipalActivity.class);
                startActivity(intent);
            }
        },4000);


        TimerTask t = new  TimerTask(){
            @Override
            public void run(){
                startActivity(new Intent(SplashActivity.this, pedidosActivity.class));
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(t, 7000);
    }
}