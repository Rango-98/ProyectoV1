package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class RefaccionesActivity extends AppCompatActivity {

    private ListView refacciones_list;
    private final ArrayList<Producto> p =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refacciones);

        refacciones_list = findViewById(R.id.refacciones_list);

        Bundle bundle = getIntent().getExtras();

        Utilidades utilidades = new Utilidades();
        cargarProductos(this, utilidades.getUrl() + "producto_categoria.php?categoria=" + bundle.getString("categoria_refaccion"));

        LinearLayout homeBtnr = findViewById(R.id.homeBtnr);
        LinearLayout btn_pedidosr = findViewById(R.id.btn_pedidosr);
        LinearLayout btn_carritor = findViewById(R.id.btn_carritor);
        LinearLayout btn_contactosr = findViewById(R.id.btn_contactosr);
        LinearLayout cerrarSesionbtnr = findViewById(R.id.cerrarSesionbtnr);

        homeBtnr.setOnClickListener(v->{
            startActivity(new Intent(this, PaginaPrincipalActivity.class));
            finish();
        });

        btn_pedidosr.setOnClickListener(v ->{
            startActivity(new Intent(this, PedidosActivity.class));
            finish();
        });

        btn_carritor.setOnClickListener(v->{
            startActivity(new Intent(this, carritoActivity.class));
            finish();
        });

        btn_contactosr.setOnClickListener(v -> {
            startActivity(new Intent(this, ContactanosActivity.class));
            finish();
        });

        cerrarSesionbtnr.setOnClickListener(v ->{
            cerrar_Sesion();
        });
    }

    private void cargarProductos(Context context, String url) {
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
                refacciones_list.setAdapter(adaptadorProducto);

                refacciones_list.setOnItemClickListener((adapterView, view, i, l) -> {
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