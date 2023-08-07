package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectov1.clases.Adaptador_producto;
import com.example.proyectov1.clases.Producto;
import com.example.proyectov1.clases.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PaginaPrincipalActivity extends AppCompatActivity {
    private ListView listProducto;
    private final ArrayList<Producto> p =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        SharedPreferences datosA = getSharedPreferences("dato.dat", MODE_PRIVATE);
        String nombre_usuario = datosA.getString("nombre", "usuario");

        TextView txtNombre = findViewById(R.id.txtNombre);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout btn_pedidos = findViewById(R.id.btn_pedidos);
        LinearLayout btn_carrito = findViewById(R.id.btn_carrito);
        LinearLayout btn_contactos = findViewById(R.id.btn_contactos);
        LinearLayout cerrarSesionbtn = findViewById(R.id.cerrarSesionbtn);

        txtNombre.setText(String.format("Bienvenido: %s", nombre_usuario));
        listProducto = findViewById(R.id.listProducto);

        Utilidades utilidades = new Utilidades();
        cargarProductosPopulares(this, utilidades.getUrl() + "productos_pupulares.php");

        homeBtn.setOnClickListener(v->{
            startActivity(new Intent(this, PaginaPrincipalActivity.class));
            finish();
        });

        btn_pedidos.setOnClickListener(v ->{
            startActivity(new Intent(this, PedidosActivity.class));
            finish();
        });

        btn_carrito.setOnClickListener(v->{
            startActivity(new Intent(this, carritoActivity.class));
            finish();
        });

        btn_contactos.setOnClickListener(v -> {
            startActivity(new Intent(this, ContactanosActivity.class));
            finish();
        });

        cerrarSesionbtn.setOnClickListener(v ->{
            cerrar_Sesion();
        });
    }

    private void cargarProductosPopulares(Context context, String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                if (!response.equals("0")) {
                    JSONArray array = new JSONArray(new String(response));
                    int i = 0;
                    while (i < array.length()) {
                        p.add(new Producto(array.getJSONObject(i).getInt("ID"),
                                array.getJSONObject(i).getInt("ID_PROVEEDOR"),
                                array.getJSONObject(i).getString("SKU"),
                                array.getJSONObject(i).getString("NOBMBRE"),
                                array.getJSONObject(i).getString("FOTO"),
                                array.getJSONObject(i).getInt("CANTIDAD"),
                                array.getJSONObject(i).getString("CATEGORIA"),
                                array.getJSONObject(i).getString("MARCA"),
                                array.getJSONObject(i).getString("MODELO"),
                                array.getJSONObject(i).getString("TIPO"),
                                array.getJSONObject(i).getDouble("PRECIO_PROVEEDOR"),
                                array.getJSONObject(i).getDouble("PRECIO_VENTA"),
                                array.getJSONObject(i).getString("CODIGO_BARRAS"),
                                array.getJSONObject(i).getString("FECHA_INGRESO")));
                        System.err.println(array);
                        i++;
                    }
                }

                Adaptador_producto adaptadorProducto = new Adaptador_producto(context,p);
                listProducto.setAdapter(adaptadorProducto);

                listProducto.setOnItemClickListener((adapterView, view, i, l) -> {
                    Producto itemProducto = p.get(i);
                    Intent intent = new Intent(context, VistaProductoActivity.class);
                    intent.putExtra("id_producto", itemProducto.getId());
                    intent.putExtra("nombre", itemProducto.getNombre());
                    intent.putExtra("precio_venta", itemProducto.getPrecio_venta());
                    startActivity(intent);
                });

            } catch (JSONException exception) {
                System.err.println("Error Json:" + exception.getMessage());
            }
        }, error -> {
            System.out.println(error.getMessage());
            Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overflow_inicio, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.id_refacciones) {
            Intent intent = new Intent(this,  RefaccionesActivity.class);
            intent.putExtra("categoria_refaccion", "refaccion");
            startActivity(intent);

        }else if(id == R.id.id_otros){
            Intent intent = new Intent(this,  RefaccionesActivity.class);
            intent.putExtra("categoria_refaccion", "casco");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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