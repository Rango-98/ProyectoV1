package com.example.proyectov1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectov1.clases.Utilidades;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private Button btnSesion, btnSesion2;
    private EditText edtUsuario, edtContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtUsuario = findViewById(R.id.edtUsuario);
        edtContrasena = findViewById(R.id.edtContrasena);
        btnSesion = findViewById(R.id.btnSesion);
        btnSesion2 = findViewById(R.id.btnSesion2);

        btnSesion.setOnClickListener(v -> {
            Utilidades utilidades = new Utilidades();
            ingresarApp(utilidades.getUrl() + "validar_login.php?usuario="+edtUsuario.getText()+"&contrasena="+edtContrasena.getText());
        });

        btnSesion2.setOnClickListener(v -> startActivity(new Intent(this, RegistrarActivity.class)));
    }

    private void ingresarApp(String url) {
        AsyncHttpClient cliente = new AsyncHttpClient();
        cliente.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        String cuerpoJSON = new String(responseBody);
                        if (!cuerpoJSON.equals("0")) {
                            JSONArray dato = new JSONArray(new String(responseBody));

                            dato.getJSONObject(0).getInt("ID");
                            dato.getJSONObject(0).getInt("ID_SUCURSAL");
                            dato.getJSONObject(0).getString("NOMBRE");
                            dato.getJSONObject(0).getString("CORREO");
                            dato.getJSONObject(0).getString("PASSWORD");
                            dato.getJSONObject(0).getString("FECHA_NACIMIENTO");
                            dato.getJSONObject(0).getString("PRIVILEGIO");

                            guardarDatosSesion(dato.getJSONObject(0).getInt("ID"),
                                    dato.getJSONObject(0).getString("NOMBRE"),
                                    dato.getJSONObject(0).getString("CORREO"),
                                    dato.getJSONObject(0).getString("PASSWORD"));
                            startActivity(new Intent(getApplicationContext(), PaginaPrincipalActivity.class));

                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Datos incorrrectos", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error JSON: " + "\n" + e, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Codigo del servidor: " + statusCode, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Error en la respuesta: " + error, Toast.LENGTH_LONG).show();
                System.out.println(error.getMessage());
            }
        });
    }

    private void guardarDatosSesion(int idUsuario, String nombre, String usuario, String contra) {
        SharedPreferences.Editor datosSesion = getSharedPreferences("dato.dat", MODE_PRIVATE).edit();
        datosSesion.putInt("idUsuario", idUsuario);
        datosSesion.putString("nombre", nombre);
        datosSesion.putString("usuario", usuario);
        datosSesion.putString("contrasena", contra);
        datosSesion.putBoolean("registro", true);
        datosSesion.apply();
    }
}