package com.example.proyectov1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class FormaPagoActivity extends AppCompatActivity {

    private Button btnFoto, btnPagar;
    private CheckBox rdbVisa, rdbTienda, rdbAgregar;
    private TextView edtNumTarjeta, et_nombreTitular, edtFecha, edtCCV;
    private String nombre_usuario;
    private String NumeroTrajeta, nombreTitular, mmaaaa, cvv;
    private int codigoTienda = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forma_pago);

        SharedPreferences datosA = getSharedPreferences("dato.dat", MODE_PRIVATE);
        nombre_usuario = datosA.getString("nombre", "usuario");


        rdbVisa = findViewById(R.id.rdbVisa);
        rdbTienda = findViewById(R.id.rdbTienda);
        rdbAgregar = findViewById(R.id.rdbAgregar);
        edtNumTarjeta = findViewById(R.id.edtNumTarjeta);
        et_nombreTitular = findViewById(R.id.et_nombreTitular);
        edtFecha = findViewById(R.id.edtFecha);
        edtCCV = findViewById(R.id.edtCCV);
        btnFoto = findViewById(R.id.btnFoto);
        btnPagar = findViewById(R.id.btnPagar);

        edtNumTarjeta.setVisibility(View.GONE);
        et_nombreTitular.setVisibility(View.GONE);
        edtFecha.setVisibility(View.GONE);
        edtCCV.setVisibility(View.GONE);
        btnFoto.setVisibility(View.GONE);


        rdbVisa.setOnClickListener(view -> {
            limpiador();
            rdbTienda.setChecked(false);
            rdbAgregar.setChecked(false);

            NumeroTrajeta = "221244236598";
            nombreTitular =  nombre_usuario;
            mmaaaa = "01/2031";
            cvv = "232";

            edtNumTarjeta.setVisibility(View.GONE);
            et_nombreTitular.setVisibility(View.GONE);
            edtFecha.setVisibility(View.GONE);
            edtCCV.setVisibility(View.GONE);
            btnFoto.setVisibility(View.GONE);
        });


        rdbTienda.setOnClickListener(v ->{
            limpiador();
            rdbVisa.setChecked(false);
            rdbAgregar.setChecked(false);

            edtNumTarjeta.setVisibility(View.GONE);
            et_nombreTitular.setVisibility(View.GONE);
            edtFecha.setVisibility(View.GONE);
            edtCCV.setVisibility(View.GONE);
            btnFoto.setVisibility(View.GONE);

            codigoTienda = (int)(Math.random() * 1000+100);

        });

        rdbAgregar.setOnClickListener(v ->{
            rdbVisa.setChecked(false);
            rdbTienda.setChecked(false);

            edtNumTarjeta.setVisibility(View.VISIBLE);
            et_nombreTitular.setVisibility(View.VISIBLE);
            edtFecha.setVisibility(View.VISIBLE);
            edtCCV.setVisibility(View.VISIBLE);
            btnFoto.setVisibility(View.VISIBLE);
        });

        btnFoto.setOnClickListener(v ->{
            IntentIntegrator intentIntegrator = new IntentIntegrator(FormaPagoActivity.this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            intentIntegrator.setPrompt("Lector de tarjetas");
            intentIntegrator.setCameraId(0);
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setBarcodeImageEnabled(true);
            intentIntegrator.initiateScan();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null ){
            if(((IntentResult) intentResult).getContents() == null){
                Toast.makeText(this, "Lectura cancelada.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Datos leídos.", Toast.LENGTH_SHORT).show();
                edtNumTarjeta.setText(intentResult.getContents());
            }
        }
    }

    private void limpiador(){
        edtNumTarjeta.setText("");
        et_nombreTitular.setText("");
        edtFecha.setText("");
        edtCCV.setText("");
    }
}