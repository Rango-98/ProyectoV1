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
import com.example.proyectov1.clases.Adaptador_pedidos;
import com.example.proyectov1.clases.Pedidos;
import com.example.proyectov1.clases.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PedidosActivity extends AppCompatActivity {

    private ArrayList<Pedidos> arrayListPedido = new ArrayList<>();
    private ListView lista_pedido;

    private int idusuario = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        SharedPreferences datosA = getSharedPreferences("dato.dat", MODE_PRIVATE);
        idusuario = datosA.getInt("idUsuario", 0);

        LinearLayout homeBtnp = findViewById(R.id.homeBtnp);
        LinearLayout btn_pedidosp = findViewById(R.id.btn_pedidosp);
        LinearLayout btn_carritop = findViewById(R.id.btn_carritop);
        LinearLayout btn_contactosp = findViewById(R.id.btn_contactosp);
        LinearLayout btn_cerrarSesionbtnp = findViewById(R.id.btn_cerrarSesionbtnp);


        Utilidades utilidades = new Utilidades();
        lista_pedido = findViewById(R.id.lista_pedido);
        cargarPedidos(this, utilidades.getUrl() + "lista_pedidos.php");


        homeBtnp.setOnClickListener(v->{
            startActivity(new Intent(this, PaginaPrincipalActivity.class));
            finish();
        });

        btn_pedidosp.setOnClickListener(v ->{
            startActivity(new Intent(this, PedidosActivity.class));
            finish();
        });

        btn_carritop.setOnClickListener(v->{
            startActivity(new Intent(this, carritoActivity.class));
            finish();
        });

        btn_contactosp.setOnClickListener(v -> {
            startActivity(new Intent(this, ContactanosActivity.class));
            finish();
        });

        btn_cerrarSesionbtnp.setOnClickListener(v ->{
            cerrar_Sesion();
        });
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