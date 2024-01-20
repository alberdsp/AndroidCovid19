package com.example.covid_19app.controllers;

import com.example.covid_19app.models.ApiRespuesta;
import com.example.covid_19app.models.Conexion;
import com.example.covid_19app.models.TomaDeTemperatura;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 * Clase que se encarga de realizar la conexión con el servidor para enviar la toma de
 * temperatura via POST al webservice
 */
public class ApiPostTemController implements Runnable {

    private final Callback callback;
    private final Conexion conexion;
    private final TomaDeTemperatura tomaDeTemperatura;

    public ApiPostTemController(Callback callback, TomaDeTemperatura tomaDeTemperatura) {
        this.callback = callback;
        this.conexion = new Conexion();
        this.tomaDeTemperatura = tomaDeTemperatura;
    }

    //  Método que se encarga de enviar el callback al hilo principal.

    @Override
    public void run() {
        ApiRespuesta response;
        try {
            response = enviarMedicion(tomaDeTemperatura);
            if (callback != null) {
                callback.onResult(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new ApiRespuesta();
            response.setSuccess(false);
            response.setError("Error en la ejecución: " + e.getMessage());
            if (callback != null) {
                callback.onResult(response);
            }
        }
    }


    /***
     * Método que se encarga de realizar la conexión con el servidor para enviar la toma de
     * temperatura
     * @return Devuelve un objeto de tipo ApiRespuesta con la respuesta del servidor
     * @throws IOException
     * @throws JSONException
     */
    private ApiRespuesta enviarMedicion(TomaDeTemperatura tomaDeTemperatura) throws IOException, JSONException {
        ApiRespuesta apiRespuesta = new ApiRespuesta();
        HttpURLConnection connection = null;


        //  Creamos objeto url que tendrá la url del servidor y los parametros que se enviarán
        try {
            URL url = new URL(conexion.getUrl() + "temp.php");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            String postData = String.format(
                    "nombre=%s&apellidos=%s&temperatura=%s&format=%s&ciudad=%s&provincia=%s",
                    URLEncoder.encode(tomaDeTemperatura.getNombre(), StandardCharsets.UTF_8.name()),
                    URLEncoder.encode(tomaDeTemperatura.getApellidos(), StandardCharsets.UTF_8.name()),
                    URLEncoder.encode(String.valueOf(tomaDeTemperatura.getTemperatura()), StandardCharsets.UTF_8.name()),
                    URLEncoder.encode(String.valueOf(tomaDeTemperatura.getTipotemperatura()), StandardCharsets.UTF_8.name()),
                    URLEncoder.encode(tomaDeTemperatura.getCiudad(), StandardCharsets.UTF_8.name()),
                    URLEncoder.encode(tomaDeTemperatura.getProvincia(), StandardCharsets.UTF_8.name())
            );

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = postData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            //  Recibimos un json con los datos de la respuesta del servidor
            JSONObject jsonResponse = new JSONObject(response.toString());
            apiRespuesta.setSuccess(jsonResponse.optBoolean("success"));
            apiRespuesta.setError(jsonResponse.optString("error"));
            apiRespuesta.setUserId(jsonResponse.optString("temp"));
        } catch (IOException e) {
            throw e;
        }
        // devuelvo la respuesta del servidor
        return apiRespuesta;
    }

    public interface Callback {
        void onResult(ApiRespuesta result);
    }
}
