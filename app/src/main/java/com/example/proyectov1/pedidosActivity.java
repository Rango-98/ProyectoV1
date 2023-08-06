package com.example.proyectov1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectov1.clases.Adaptador_pedidos;
import com.example.proyectov1.clases.Adaptador_producto;
import com.example.proyectov1.clases.Pedidos;
import com.example.proyectov1.clases.Producto;
import com.example.proyectov1.clases.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class pedidosActivity extends AppCompatActivity {

    private ArrayList<Pedidos> arrayListPedido = new ArrayList<>();
    private ListView lista_pedido;

    private int idusuario = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        SharedPreferences datosA = getSharedPreferences("dato.dat", MODE_PRIVATE);
        idusuario = datosA.getInt("idUsuario", 0);

        Utilidades utilidades = new Utilidades();
        lista_pedido = findViewById(R.id.lista_pedido);
        cargarPedidos(this, utilidades.getUrl() + "lista_pedidos.php");
    }

    private void cargarPedidos(Context context, String url) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                if (!response.equals("0")) {
                    JSONArray array = new JSONArray(new String(response));
                    int i = 0;
                    while (i < array.length()) {
                        arrayListPedido.add(new Pedidos(array.getJSONObject(i).getInt("ID_PEDIDO"),
                                array.getJSONObject(i).getInt("CODIGO_COMPRA"),
                                array.getJSONObject(i).getInt("STATUS"),
                                array.getJSONObject(i).getInt("ID_USUARIO"),
                                array.getJSONObject(i).getInt("CODIGO_CARRITO")));
                        System.err.println(array);
                        i++;
                    }
                }

                Adaptador_pedidos adaptadorProducto = new Adaptador_pedidos(context,arrayListPedido);
                lista_pedido.setAdapter(adaptadorProducto);

                /*istProducto.setOnItemClickListener((adapterView, view, i, l) -> {
                    Producto itemProducto = p.get(i);
                    Intent intent = new Intent(context, VistaProductoActivity.class);
                    intent.putExtra("id_producto", itemProducto.getId());
                    intent.putExtra("nombre", itemProducto.getNombre());
                    intent.putExtra("precio_venta", itemProducto.getPrecio_venta());
                    startActivity(intent);
                });*/

            } catch (JSONException exception) {
                System.err.println("Error Json:" + exception.getMessage());
            }
        }, error -> {
            Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }){
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
}