package com.example.proyectov1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class VistaProductoActivity extends AppCompatActivity {

    private TextView txtTituloProducto, txt_precioProducto, txtTotalItems, tv_contador;
    private ImageView btnMas, btnMenos;
    private Button btnAgregar, btnCancelar;
    private int contador = 1;
    private double precio_venta = 0.0;
    private double precio_final = 0.0;
    private String url = "";
    private int idusuario = 0;
    private int idproducto = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_producto);

        SharedPreferences datosA = getSharedPreferences("dato.dat", MODE_PRIVATE);
        idusuario = datosA.getInt("idUsuario", 0);

        Utilidades utilidades =  new Utilidades();
        url = utilidades.getUrl() + "llenar_carrito.php";

        txtTituloProducto = findViewById(R.id.txtTituloProducto);
        txt_precioProducto = findViewById(R.id.txt_precioProducto);
        txtTotalItems = findViewById(R.id.txtTotalItems);
        tv_contador = findViewById(R.id.tv_contador);
        btnMas = findViewById(R.id.btnMas);
        btnMenos = findViewById(R.id.btnMenos);
        btnAgregar  = findViewById(R.id.btnAgregar);
        btnCancelar = findViewById(R.id.btnCancelar);

        Bundle bundle = getIntent().getExtras();
        idproducto = bundle.getInt("id_producto");
        precio_venta = bundle.getDouble("precio_venta");
        txtTituloProducto.setText(bundle.getString("nombre"));
        txt_precioProducto.setText("$ " + precio_venta);
        txtTotalItems.setText("$ " + precio_venta);
        tv_contador.setText(String.valueOf(contador));

        btnMas.setOnClickListener(v ->{
            tv_contador.setText(String.valueOf((contador++)+1));
             precio_final = precio_venta * contador;
            txtTotalItems.setText(String.format("$ %s", precio_final));
        });

        btnMenos.setOnClickListener(v ->{
            tv_contador.setText(String.valueOf((contador--)-1));
            precio_final = precio_final - precio_venta;
            txtTotalItems.setText(String.format("$ %s", precio_final));
        });

        btnAgregar.setOnClickListener(v ->{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

            }, error -> {
                System.out.println(error.getMessage());
                Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> param = new HashMap<>();
                    param.put("idproducto", String.valueOf(idproducto));
                    param.put("costo", String.valueOf(precio_final));
                    param.put("cantidad", String.valueOf(contador));
                    param.put("idusuario", String.valueOf(idusuario));
                    return param;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        });

        btnCancelar.setOnClickListener(v ->{
            startActivity(new Intent(this, PaginaPrincipalActivity.class));
            finish();
        });
    }
}