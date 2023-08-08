package com.example.proyectov1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.proyectov1.clases.Adaptador_Carrito;
import com.example.proyectov1.clases.Utilidades;
import com.example.proyectov1.clases.Carrito;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class carritoActivity extends AppCompatActivity {
    private ArrayList<Carrito> carritos;
    private ArrayList<Double> precios;
    private TextView txtPrecioProducto;
    private TextView txtTotalPrecio;
    private ListView itmProductoCarrito;
    private ConstraintLayout btn_Compra;
    private int idusuario = 0, codigo_carrito = 0;
    private double sumadorCostos = 0, precioTotal = 0;
    private final double IVA = 1.16;
    private final DecimalFormat decimalFormat = new DecimalFormat("#.00");
    private Utilidades utilidades = new Utilidades();
    private Adaptador_Carrito adaptadorCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        SharedPreferences datosA = getSharedPreferences("dato.dat", MODE_PRIVATE);
        idusuario = datosA.getInt("idUsuario", 0);

        cargarCarrito(this, utilidades.getUrl() + "lista_carrito.php");

        itmProductoCarrito = findViewById(R.id.itmProductoCarrito);
        txtPrecioProducto = findViewById(R.id.txtPrecioProducto);
        TextView txtPrecioIVA = findViewById(R.id.txtPrecioIVA);
        txtTotalPrecio = findViewById(R.id.txtTotalPrecio);
        btn_Compra = findViewById(R.id.btn_Compra);

        LinearLayout homeBtnc = findViewById(R.id.homeBtnc);
        LinearLayout btn_pedidosc = findViewById(R.id.btn_pedidosc);
        LinearLayout btn_carritoc = findViewById(R.id.btn_carritoc);
        LinearLayout btn_contactosc = findViewById(R.id.btn_contactosc);
        LinearLayout btn_cerrarSesionbtnc = findViewById(R.id.btn_cerrarSesionbtnc);

        txtPrecioIVA.setText("16%");

        homeBtnc.setOnClickListener(v -> {
            startActivity(new Intent(this, PaginaPrincipalActivity.class));
            finish();
        });

        btn_pedidosc.setOnClickListener(v -> {
            startActivity(new Intent(this, PedidosActivity.class));
            finish();
        });

        btn_carritoc.setOnClickListener(v -> {
            startActivity(new Intent(this, carritoActivity.class));
            finish();
        });

        btn_contactosc.setOnClickListener(v -> {
            startActivity(new Intent(this, ContactanosActivity.class));
            finish();
        });

        btn_cerrarSesionbtnc.setOnClickListener(v -> {
            cerrar_Sesion();
        });

    }

    private void cargarCarrito(Context context, String url) {
        itmProductoCarrito = findViewById(R.id.itmProductoCarrito);
        carritos = new ArrayList<>();
        precios = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                if (!response.equals("0")) {
                    JSONArray array = new JSONArray(new String(response));
                    int i = 0;
                    while (i < array.length()) {
                        carritos.add(new Carrito(
                                array.getJSONObject(i).getInt("ID"),
                                array.getJSONObject(i).getString("CODIGO_CARRITO"),
                                array.getJSONObject(i).getInt("ID_PRODUCTO"),
                                array.getJSONObject(i).getDouble("COSTO"),
                                array.getJSONObject(i).getInt("CANTIDAD"),
                                array.getJSONObject(i).getString("FECHA_VENTA"),
                                array.getJSONObject(i).getInt("ID_USUARIO"),
                                array.getJSONObject(i).getInt("CARRITO"),
                                array.getJSONObject(i).getString("NOMBRE_PRODUCTO")));

                        precios.add(array.getJSONObject(i).getDouble("COSTO"));
                        codigo_carrito = array.getJSONObject(0).getInt("CODIGO_CARRITO");

                        for (Double e : precios) {
                            sumadorCostos = sumadorCostos + e;
                        }
                        i++;
                    }

                    txtPrecioProducto.setText(String.format("$ %s", decimalFormat.format(sumadorCostos)));
                    precioTotal = sumadorCostos * IVA;
                    txtTotalPrecio.setText(String.format("$ %s", decimalFormat.format(precioTotal)));

                    btn_Compra.setOnClickListener(v -> {
                        Intent intent = new Intent(context, FormaPagoActivity.class);
                        intent.putExtra("codigo_carrito", codigo_carrito);
                        startActivity(intent);
                    });

                }

                if (carritos.size() != 0) {
                    adaptadorCarrito = new Adaptador_Carrito(context, carritos);
                    itmProductoCarrito.setAdapter(adaptadorCarrito);

                    itmProductoCarrito.setOnItemClickListener((adapterView, view, i, l) -> {
                        Carrito itemCarrito = carritos.get(i);

                        AlertDialog.Builder crearMensaje = new AlertDialog.Builder(context);
                        crearMensaje.setIcon(R.drawable.shopping)
                                .setTitle("Quitar de carrito")
                                .setMessage("¿Desea quitar este objeto del carrito?")
                                .setCancelable(false)
                                .setPositiveButton("Si", (dialogInterface, i12) -> {
                                    AlertDialog.Builder crearMensaje2 = new AlertDialog.Builder(context);
                                    crearMensaje2 .setTitle("Quitar de carrito")
                                            .setIcon(R.drawable.shopping)
                                            .setMessage("Articulo quitado del carrito")
                                            .setPositiveButton("Aceptar", (dialogInterface1, i13) -> {
                                                quitarArticuloCarrito(context, utilidades.getUrl() + "quitarproductocarrito.php?idusuario=" + idusuario
                                                        + "&idproducto=" + itemCarrito.getId_producto()
                                                        + "&codigocarrito=" + itemCarrito.getCodigo_carrito());

                                                itmProductoCarrito.setAdapter(adaptadorCarrito);
                                            }).show();

                                    AlertDialog generar2 = crearMensaje2.create();

                                }).setNegativeButton("No", (dialogInterface, i1) -> {
                                    dialogInterface.cancel();
                                }).show();

                                AlertDialog generar = crearMensaje.create();
                    });
                } else {
                    Toast.makeText(context, "No has agregado ningun producto al carrito", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException exception) {
                System.err.println("Error Json:" + exception.getMessage());
            }
        }, error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("idusuario", String.valueOf(idusuario));
                return param;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    private void quitarArticuloCarrito(Context context, String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {

            precios = new ArrayList<>();
            carritos = new ArrayList<>();
            precios.clear();
            precios.clear();
            cargarCarrito(this, utilidades.getUrl() + "lista_carrito.php");
            sumadorCostos = 0; precioTotal = 0;

            adaptadorCarrito = new Adaptador_Carrito(context, carritos);
            itmProductoCarrito.setAdapter(adaptadorCarrito);

            txtPrecioProducto.setText(String.format("$ %s", decimalFormat.format(sumadorCostos)));
            precioTotal = sumadorCostos * IVA;
            txtTotalPrecio.setText(String.format("$ %s", decimalFormat.format(precioTotal)));

            Toast.makeText(context, "Articulo quitado con exito", Toast.LENGTH_LONG).show();

        }, error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show());

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    private void cerrar_Sesion() {
        SharedPreferences.Editor datosSesion = getSharedPreferences("dato.dat", MODE_PRIVATE).edit();
        datosSesion.clear();
        datosSesion.apply();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}