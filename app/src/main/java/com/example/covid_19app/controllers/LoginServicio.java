package com.example.covid_19app.controllers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


/**
 * Servicio que se encarga de realizar la conexión con el servidor para el login
 * y devuelve el resultado al callback que se le pasa por parámetro
 * Implementa la interfaz Runnable para poder ejecutarlo en un hilo obligatorio para los login
 *     ABF 2024
 *
 *
 */
public class LoginServicio implements Runnable {

    private final String username;
    private final String password;
    private final Callback callback;


    // constructor que recibe los datos del login y el callback
    public LoginServicio(String username, String password, Callback callback) {
        this.username = username;
        this.password = password;
        this.callback = callback;
    }

    @Override
    public void run() {
        String result = login(username, password);
        if (callback != null) {
            callback.onResult(result);
        }
    }


    // método que realiza el login recibiendo los datos de los inputtext
    private String login(String username, String password) {
        String loginResult = "error de conexión al servidor";
        try {
            URL url = new URL("https://jumerca.es/apicovid/auth.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            String postData = "user=" + username + "&passwd=" + password;

            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // recogemos el stream de salida y escribimos los datos en él
            OutputStream os = connection.getOutputStream();
            os.write(postData.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();


            // recogemos el código de respuesta
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                // abrimos el stream de entrada y lo leemos
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                // leemos el resultado y lo guardamos en un string
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // paseamos y creamos un objeto json con el resultado
                JSONObject jsonResponse = new JSONObject(response.toString());
                boolean success = jsonResponse.getBoolean("success");
                boolean credentials = jsonResponse.getBoolean("credentials");

                if (success && credentials) {
                    loginResult = "loginok";
                } else {
                    loginResult = "credenciales incorrectas";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return loginResult;
    }

    // interfaz callback que devuelve el resultado del login
    public interface Callback {
        void onResult(String result);
    }
}
