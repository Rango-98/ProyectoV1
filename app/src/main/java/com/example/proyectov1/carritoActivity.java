package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
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

public class carritoActivity extends AppCompatActivity {

    private ArrayList<Venta_Online> arrayList_ventaOnline = new ArrayList<>();
    private ListView itmProductoCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        SharedPreferences datosA = getSharedPreferences("dato.dat", MODE_PRIVATE);
        int idUsuario = datosA.getInt("idUsuario", 0);

        itmProductoCarrito = findViewById(R.id.itmProductoCarrito);

        Utilidades utilidades =  new Utilidades();

        cargarCarrito(this, utilidades.getUrl() + "carrito.php?idU=" + idUsuario);
    }

    private void cargarCarrito(Context context, String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                if (!response.equals("0")) {
                    JSONArray array = new JSONArray(new String(response));
                    int i = 0;
                    while (i < array.length()) {
                        arrayList_ventaOnline.add(new Venta_Online(
                                array.getJSONObject(i).getInt("ID"),
                                array.getJSONObject(i).getInt("ID_PRODUCTO"),
                                array.getJSONObject(i).getString("NOMBRE_PRODUCTO"),
                                array.getJSONObject(i).getDouble("COSTO"),
                                array.getJSONObject(i).getString("FECHA_VENTA"),
                                array.getJSONObject(i).getInt("ID_USUARIO"),
                                array.getJSONObject(i).getInt("CARRITO")));
                        System.err.println(array);
                        i++;
                    }
                }

                Adaptador_Carrito adaptadorCarrito = new Adaptador_Carrito(context,arrayList_ventaOnline);
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
        }, error -> Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show());

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }
}