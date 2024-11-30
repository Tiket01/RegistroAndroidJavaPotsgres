package com.example.informacionjavapostgresql.api;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

// Interfaz para definir las rutas de la API
public interface ApiService {

    // Ruta para insertar datos
    @POST("insert")
    Call<Void> insertarDatos(@Body Map<String, String> datos);

    // Ruta para obtener datos
    @GET("/data")
    Call<List<Map<String, Object>>> obtenerDatos();
}
