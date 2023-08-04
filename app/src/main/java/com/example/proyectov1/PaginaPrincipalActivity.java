package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PaginaPrincipalActivity extends AppCompatActivity {

    private ListView listProducto;
    private TextView txtNombre;
    private String nombre_usuario;
    private ArrayList<Producto> p =  new ArrayList<>();

    private LinearLayout homeBtn, cerrarSesionbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        SharedPreferences datosA = getSharedPreferences("dato.dat", MODE_PRIVATE);
        nombre_usuario = datosA.getString("nombre", "usuario");

        txtNombre = findViewById(R.id.txtNombre);
        homeBtn = findViewById(R.id.homeBtn);
        cerrarSesionbtn = findViewById(R.id.cerrarSesionbtn);

        txtNombre.setText("Bienvenido: " +  nombre_usuario);
        listProducto = findViewById(R.id.listProducto);

        Utilidades utilidades = new Utilidades();
        cargarProductosPopulares(this, utilidades.getUrl() + "productos.php");

        homeBtn.setOnClickListener(v->{
            startActivity(new Intent(this, PaginaPrincipalActivity.class));
        });


        cerrarSesionbtn.setOnClickListener(v ->{
            cerrar_Sesion();
        });
    }

    private void cargarProductosPopulares(Context context, String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (!response.equals("0")) {
                        JSONArray array = new JSONArray(new String(response));
                        int i = 0;
                        while (i < array.length()) {
                            p.add(new Producto(array.getJSONObject(i).getInt("ID"),
                                    array.getJSONObject(i).getInt("ID_PROVEEDOR"),
                                    array.getJSONObject(i).getString("SKU"),
                                    array.getJSONObject(i).getString("NOBMBRE"),
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

                        /*adaptadorMisVacantes.setOnclickListener(view -> {
                            int valor_idpostulacion = datos_misvacantes.get(recycle_vacantes_usuarios.getChildAdapterPosition(view)).getId_postulacion();
                            int valor_idvacante = datos_misvacantes.get(recycle_vacantes_usuarios.getChildAdapterPosition(view)).getId_vacanteVW();
                            Intent intent = new Intent(context, Estatus_postulacion.class);
                            Bundle bundle = new Bundle();
                            System.out.println(valor_idpostulacion);
                            System.out.println(valor_idvacante );
                            bundle.putString("id_postulacion", String.valueOf(valor_idpostulacion));
                            bundle.putString("id_vacante", String.valueOf(valor_idvacante));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        });*/

                } catch (JSONException exception) {
                    System.err.println("Error Json:" + exception.getMessage());
                }
            }
        }, error -> {
            System.out.println(error.getMessage());
            Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
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