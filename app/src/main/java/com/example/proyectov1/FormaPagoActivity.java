package com.example.proyectov1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectov1.clases.Utilidades;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;

public class FormaPagoActivity extends AppCompatActivity {

    private Button btnFoto;
    private CheckBox rdbVisa, rdbTienda, rdbAgregar;
    private TextView edtNumTarjeta, et_nombreTitular, edtFecha, edtCCV;
    private String nombre_usuario;
    private String NumeroTrajeta, nombreTitular, mmaaaa, cvv;
    private int codigoTienda = 0;
    private int codigo_carrito = 0;
    private int idusuario = 0;
    private String url =  "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forma_pago);

        SharedPreferences datosA = getSharedPreferences("dato.dat", MODE_PRIVATE);
        nombre_usuario = datosA.getString("nombre", "usuario");
        idusuario = datosA.getInt("idUsuario", 0);

        Bundle bundle = getIntent().getExtras();
        codigo_carrito  = bundle.getInt("codigo_carrito");;

        System.out.println(codigo_carrito);

        Utilidades utilidades = new Utilidades();
        url = utilidades.getUrl() + "guardar_pedido.php";


        rdbVisa = findViewById(R.id.rdbVisa);
        rdbTienda = findViewById(R.id.rdbTienda);
        rdbAgregar = findViewById(R.id.rdbAgregar);
        edtNumTarjeta = findViewById(R.id.edtNumTarjeta);
        et_nombreTitular = findViewById(R.id.et_nombreTitular);
        edtFecha = findViewById(R.id.edtFecha);
        edtCCV = findViewById(R.id.edtCCV);
        btnFoto = findViewById(R.id.btnFoto);
        Button btnPagar = findViewById(R.id.btnPagar);

        LinearLayout homeBtnf = findViewById(R.id.homeBtnf);
        LinearLayout btn_pedidosf = findViewById(R.id.btn_pedidosf);
        LinearLayout btn_carritof = findViewById(R.id.btn_carritof);
        LinearLayout btn_contactosf = findViewById(R.id.btn_contactosf);
        LinearLayout cerrarSesionbtnf = findViewById(R.id.cerrarSesionbtnf);

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

        btnPagar.setOnClickListener(v ->{

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                Toast.makeText(this, "Pedido generado con exito: " + response, Toast.LENGTH_LONG).show();
                System.out.println(response);
                startActivity(new Intent(this, SplashActivity.class));
            }, error -> {
                Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams()  {
                    Map<String, String> param = new HashMap<>();
                    param.put("idusuario", String.valueOf(idusuario));
                    param.put("codigocarrito", String.valueOf(codigo_carrito));
                    return param;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        });

        homeBtnf.setOnClickListener(v->{
            startActivity(new Intent(this, PaginaPrincipalActivity.class));
            finish();
        });

        btn_pedidosf.setOnClickListener(v ->{
            startActivity(new Intent(this, PedidosActivity.class));
            finish();
        });

        btn_carritof.setOnClickListener(v->{
            startActivity(new Intent(this, carritoActivity.class));
            finish();
        });

        btn_contactosf.setOnClickListener(v -> {
            startActivity(new Intent(this, ContactanosActivity.class));
            finish();
        });

        cerrarSesionbtnf.setOnClickListener(v ->{
            cerrar_Sesion();
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