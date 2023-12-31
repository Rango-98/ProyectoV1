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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class VistaProductoActivity extends AppCompatActivity {
    private TextView txtTotalItems;
    private TextView tv_contador;
    private double precio_venta = 0.0, precio_final = 0.0;
    private String url = "";
    private int idusuario = 0, idproducto = 0, contador = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_producto);

        DecimalFormat decimalFormat = new DecimalFormat("#.0");

        SharedPreferences datosA = getSharedPreferences("dato.dat", MODE_PRIVATE);
        idusuario = datosA.getInt("idUsuario", 0);

        Utilidades utilidades = new Utilidades();
        url = utilidades.getUrl() + "llenar_carrito.php";

        ImageView imgProducto = findViewById(R.id.imgProducto);
        TextView txtTituloProducto = findViewById(R.id.txtTituloProducto);
        TextView txt_precioProducto = findViewById(R.id.txt_precioProducto);
        txtTotalItems = findViewById(R.id.txtTotalItems);
        tv_contador = findViewById(R.id.tv_contador);
        ImageView btnMas = findViewById(R.id.btnMas);
        ImageView btnMenos = findViewById(R.id.btnMenos);
        Button btnAgregar = findViewById(R.id.btnAgregar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        Bundle bundle = getIntent().getExtras();
        idproducto = bundle.getInt("id_producto");
        precio_venta = bundle.getDouble("precio_venta");
        txtTituloProducto.setText(bundle.getString("nombre"));
        txt_precioProducto.setText("$ " + precio_venta);
        txtTotalItems.setText("$ " + precio_venta);
        tv_contador.setText(String.valueOf(contador));

        imgProducto.setImageResource(R.drawable.shopping);

        btnMas.setOnClickListener(v -> {
            tv_contador.setText(String.valueOf((contador++) + 1));
            precio_final = precio_venta * contador;
            txtTotalItems.setText(String.format("$ %s", decimalFormat.format(precio_final)));
        });

        btnMenos.setEnabled(true);
        btnMenos.setOnClickListener(v -> {
            tv_contador.setText(String.valueOf((contador--) - 1));
            precio_final = precio_final - precio_venta;
            txtTotalItems.setText(String.format("$ %s", decimalFormat.format(precio_final)));
        });

        btnAgregar.setOnClickListener(v -> {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                startActivity(new Intent(this, carritoActivity.class));

            }, error -> {
                System.out.println(error.getMessage());
                Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> param = new HashMap<>();
                    param.put("idproducto", String.valueOf(idproducto));

                    if(precio_final != 0){
                        param.put("costo", String.valueOf(precio_final));
                    }else{
                        param.put("costo", String.valueOf(precio_venta));
                    }

                    param.put("cantidad", String.valueOf(contador));
                    param.put("idusuario", String.valueOf(idusuario));
                    return param;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        });

        btnCancelar.setOnClickListener(v -> {
            startActivity(new Intent(this, PaginaPrincipalActivity.class));
            finish();
        });
    }
}