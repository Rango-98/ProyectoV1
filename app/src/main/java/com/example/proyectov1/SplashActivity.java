package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
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

public class SplashActivity extends AppCompatActivity {
    private final String ID_CANAL = "NOTIFICATION";
    private final String canal = "notification";

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getWindow().setStatusBarColor(Color.WHITE);

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
        crearNotificacion.setContentText("Tu compra se a realizado con exito");
        crearNotificacion.setColor(Color.BLACK);

        NotificationManagerCompat desplegarNotificacion = NotificationManagerCompat.from(this);
        desplegarNotificacion.notify(0, crearNotificacion.build());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, PedidosActivity.class);
                startActivity(intent);
            }
        },4000);
    }
}