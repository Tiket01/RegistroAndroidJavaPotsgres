package com.example.informacionjavapostgresql;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.informacionjavapostgresql.adapter.DatosAdapter;
import com.example.informacionjavapostgresql.api.ApiService;
import com.example.informacionjavapostgresql.api.RetrofitClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etNombres, etPrimerApellido, etSegundoApellido, etEdad;
    private Button btnEnviar;
    private RecyclerView rvDatos;

    private List<Map<String, Object>> datosList = new ArrayList<>();
    private DatosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular vistas
        etNombres = findViewById(R.id.etNombres);
        etPrimerApellido = findViewById(R.id.etPrimerApellido);
        etSegundoApellido = findViewById(R.id.etSegundoApellido);
        etEdad = findViewById(R.id.etEdad);
        btnEnviar = findViewById(R.id.btnEnviar);
        rvDatos = findViewById(R.id.rvDatos);

        // Configurar RecyclerView
        rvDatos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DatosAdapter(datosList);
        rvDatos.setAdapter(adapter);

        // Acción del botón
        btnEnviar.setOnClickListener(view -> {
            String nombres = etNombres.getText().toString();
            String primerApellido = etPrimerApellido.getText().toString();
            String segundoApellido = etSegundoApellido.getText().toString();
            String edad = etEdad.getText().toString();

            if (!nombres.isEmpty() && !primerApellido.isEmpty() && !segundoApellido.isEmpty() && !edad.isEmpty()) {
                enviarDatos(nombres, primerApellido, segundoApellido, edad);
            } else {
                Toast.makeText(MainActivity.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        obtenerDatos(); // Cargar datos iniciales
    }

    private void enviarDatos(String nombres, String primerApellido, String segundoApellido, String edad) {
        Map<String, String> datos = new HashMap<>();
        datos.put("nombres", nombres);
        datos.put("primer_apellido", primerApellido);
        datos.put("segundo_apellido", segundoApellido);
        datos.put("edad", edad);
        datos.put("fecha_nacimiento", "1990-01-01");
        datos.put("hora", "12:00:00");
        datos.put("fecha_registro", "2024-01-01");
        datos.put("estado", "1");

        ApiService apiService = RetrofitClient.getApiService();
        Call<Void> call = apiService.insertarDatos(datos);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Datos enviados correctamente", Toast.LENGTH_SHORT).show();
                    obtenerDatos();
                } else {
                    Toast.makeText(MainActivity.this, "Error al enviar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void obtenerDatos() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Map<String, Object>>> call = apiService.obtenerDatos();

        call.enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    datosList.clear();
                    datosList.addAll(response.body());
                    adapter.setDatosList(datosList);
                } else {
                    Toast.makeText(MainActivity.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
