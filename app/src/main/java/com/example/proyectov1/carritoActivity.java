package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectov1.clases.Adaptador_Carrito;
import com.example.proyectov1.clases.Utilidades;
import com.example.proyectov1.clases.Carrito;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class carritoActivity extends AppCompatActivity {
    private ArrayList<Carrito> carritos = new ArrayList<>();
    private ArrayList<Double> precios = new ArrayList<Double>();
    private TextView txtPrecioIVA, txtPrecioProducto, txtTotalPrecio;
    private ListView itmProductoCarrito;
    private ConstraintLayout btn_Compra;
    private int idusuario = 0;
    private double sumadorCostos = 0;
    private double IVA = 0.16;
    private double precioTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        SharedPreferences datosA = getSharedPreferences("dato.dat", MODE_PRIVATE);
        idusuario = datosA.getInt("idUsuario", 0);

        Utilidades utilidades =  new Utilidades();
        cargarCarrito(this, utilidades.getUrl() + "lista_carrito.php");

        System.err.println(sumadorCostos);

        itmProductoCarrito = findViewById(R.id.itmProductoCarrito);
        txtPrecioProducto = findViewById(R.id.txtPrecioProducto);
        txtPrecioIVA = findViewById(R.id.txtPrecioIVA);
        txtTotalPrecio = findViewById(R.id.txtTotalPrecio);
        btn_Compra = findViewById(R.id.btn_Compra);

        txtPrecioIVA.setText("16%");

        btn_Compra.setOnClickListener(v ->{
            Intent intent = new Intent(this, FormaPagoActivity.class);
            startActivity(intent);
        });

    }

    private void cargarCarrito(Context context, String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                if (!response.equals("0")) {
                    JSONArray array = new JSONArray(new String(response));
                    int i = 0;
                    while (i < array.length()) {
                        carritos.add(new Carrito(
                                array.getJSONObject(i).getInt("ID"),
                                array.getJSONObject(i).getInt("ID_PRODUCTO"),
                                array.getJSONObject(i).getDouble("COSTO"),
                                array.getJSONObject(i).getInt("CANTIDAD"),
                                array.getJSONObject(i).getString("FECHA_VENTA"),
                                array.getJSONObject(i).getInt("ID_USUARIO"),
                                array.getJSONObject(i).getInt("CARRITO"),
                                array.getJSONObject(i).getString("NOMBRE_PRODUCTO")));
                        System.err.println(array);

                        precios.add(array.getJSONObject(i).getDouble("COSTO"));

                        for (Double e: precios) {
                            sumadorCostos = sumadorCostos + e;
                        }

                        i++;
                    }

                    txtPrecioProducto.setText("$ " + sumadorCostos);
                    precioTotal = sumadorCostos * IVA;
                    txtTotalPrecio.setText(String.valueOf(precioTotal));
                }

                Adaptador_Carrito adaptadorCarrito = new Adaptador_Carrito(context, carritos);
                itmProductoCarrito.setAdapter(adaptadorCarrito);

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
        }, error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show())
        {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> param = new HashMap<>();
                param.put("idusuario", String.valueOf(idusuario));
                return param;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }
}