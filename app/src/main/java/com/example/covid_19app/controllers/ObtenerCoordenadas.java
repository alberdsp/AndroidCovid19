package com.example.covid_19app.controllers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.example.covid_19app.models.Coordenadas;
import com.example.covid_19app.models.Users;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ObtenerCoordenadas {

    // Constructor por defecto
    public ObtenerCoordenadas() {
    }

    /**
     * Método que obtiene las coordenadas de una lista de usuarios
     *
     * @param usersList Lista de usuarios
     * @param callback  Callback para manejar las coordenadas obtenidas
     * @param context   Contexto de la aplicación
     */
    public void obtenerCoordenadasAsync(final List<Users> usersList, final CoordenadasCallback callback, final Context context) {
        final Handler handler = new Handler(Looper.getMainLooper());

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Coordenadas> coordenadasList = new ArrayList<>();

                for (Users user : usersList) {
                    Coordenadas coordenadas = obtenerCoordenadasDeUsuario(user, context);
                    if (coordenadas != null) {
                        coordenadasList.add(coordenadas);
                    }
                }

                // Llama al callback en el hilo principal
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onCoordenadasObtenidas(coordenadasList);
                    }
                });
            }
        }).start();
    }

    // Método auxiliar para obtener las coordenadas de un solo usuario
    private Coordenadas obtenerCoordenadasDeUsuario(Users user, Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String direccion = user.getCiudad() + ", " + user.getProvincia();
        try {
            List<Address> addresses = geocoder.getFromLocationName(direccion, 1);
            if (addresses != null && !addresses.isEmpty()) {
                double latitud = addresses.get(0).getLatitude();
                double longitud = addresses.get(0).getLongitude();
                return new Coordenadas(latitud, longitud);
            }
        } catch (IOException e) {
            Log.e("ObtenerCoordenadas", "Error al obtener coordenadas", e);
        }
        return null;
    }

    // Interfaz para manejar las coordenadas obtenidas
    public interface CoordenadasCallback {
        void onCoordenadasObtenidas(List<Coordenadas> coordenadasList);
    }
}
