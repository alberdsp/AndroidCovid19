package com.example.covid_19app.controllers;

import static java.security.AccessController.getContext;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.example.covid_19app.models.Coordenadas;
import com.example.covid_19app.models.Users;
import com.example.covid_19app.views.MainActivityNav;

import java.util.ArrayList;
import java.util.List;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;



public class ObtenerCoordenadas {

    // Constructor por defecto
    public ObtenerCoordenadas() {
    }

    // Método que convierte las direcciones de una lista de usuarios en coordenadas
    public List<Coordenadas> coordenadasDireccion(List<Users> usersList) {
        List<Coordenadas> coordenadasList = new ArrayList<>();

        for (Users user : usersList) {
            Coordenadas coordenadas = obtenerCoordenadasDeUsuario(user);
            if (coordenadas != null) {
                coordenadasList.add(coordenadas);
            }
        }

        return coordenadasList;
    }

    // Método auxiliar para obtener las coordenadas de un solo usuario
    private Coordenadas obtenerCoordenadasDeUsuario(Users user) {
        // Suponiendo que tienes un método que realiza la geocodificación,
        // por ejemplo: geocodificarDireccion(user.getPoblacion(), user.getProvincia());

        // Implementación simulada. Aquí deberías implementar la lógica real de geocodificación.
        double latitudSimulada = 0.0; // Sustituir por el resultado real
        double longitudSimulada = 0.0; // Sustituir por el resultado real

        return new Coordenadas(latitudSimulada, longitudSimulada);
    }


    private void geocodificarDireccion(String ciudad, String provincia, final VolleyCallback callback) {


        String apiKey = "AIzaSyD8SVO-llmPcZEg8xq7-8D564j367olP_"; // Reemplaza con tu clave de API real
        String direccion = ciudad + ", " + provincia;


        //   String apiKey = obtenerClaveApi();
        direccion = ciudad + ", " + provincia;
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                Uri.encode(direccion) + "&key=" + apiKey;

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            if (results.length() > 0) {
                                JSONObject geometry = results.getJSONObject(0).getJSONObject("geometry");
                                JSONObject location = geometry.getJSONObject("location");
                                double lat = location.getDouble("lat");
                                double lng = location.getDouble("lng");
                                callback.onSuccess(new Coordenadas(lat, lng));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        error.printStackTrace();
                    }
                });

        queue.add(jsonObjectRequest);
    }

    public interface VolleyCallback {
        void onSuccess(Coordenadas coordenadas);
    }







}
