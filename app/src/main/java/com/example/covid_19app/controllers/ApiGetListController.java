package com.example.covid_19app.controllers;

import com.example.covid_19app.models.ApiRespuesta;
import com.example.covid_19app.models.Users;
import com.example.covid_19app.models.Conexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/***
 * Clase que se encarga de realizar la conexión con el servidor para obtener la lista de usuarios
 * registrados en la aplicación. Puede pasarse un id como parametro y devuelve un un solo usuario
 */


public class ApiGetListController implements Runnable {

    private final Callback callback;
    private final Conexion conexion;


    private String userId;  // ID del usuario (opcional)

    // constructor para el list completo
    public ApiGetListController(Callback callback) {
        this.callback = callback;
        this.conexion = new Conexion();
    }

    // Sobrecargamos el constructor para aceptar un ID de usuario
    public ApiGetListController(Callback callback, String userId) {
        this.callback = callback;
        this.conexion = new Conexion();
        this.userId = userId;
    }


    @Override
    public void run() {
        ApiRespuesta response;
        if (this.userId != null && !this.userId.isEmpty()) {
            response = fetchUser(); // Llamada para un usuario específico
        } else {
            response = fetchUsers(); // Llamada para la lista completa de usuarios
        }
        if (callback != null) {
            callback.onResult(response);
        }
    }




    /***
     * Método que se encarga de realizar la conexión con el servidor para obtener la lista de usuarios
     * registrados en la aplicación.
     * @return Devuelve un objeto de tipo ApiListRespuesta con la lista de usuarios registrados en la aplicación.
     */

    private ApiRespuesta fetchUsers() {
        ApiRespuesta apiRespuesta = new ApiRespuesta();
        List<Users> usersList = new ArrayList<>();

        try {
            URL url = new URL(conexion.getUrl() + "list.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            // Se comprueba si la conexión con el servidor es correcta.
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                // recibe un json con los datos de los usuarios registrados en la aplicación.
                JSONObject jsonResponse = new JSONObject(response.toString());
                apiRespuesta.setSuccess(jsonResponse.optBoolean("success"));
                apiRespuesta.setError(jsonResponse.optString("error", null));
                // si recibimos respuesta correcta, se añaden los usuarios a la lista.
                if (apiRespuesta.isSuccess()) {
                    JSONArray tempArray = jsonResponse.optJSONArray("temp");
                    if (tempArray != null) {
                        for (int i = 0; i < tempArray.length(); i++) {
                            JSONObject tempObject = tempArray.optJSONObject(i);
                            if (tempObject != null) {
                                Users user = new Users();
                                user.setId(tempObject.optString("id"));
                                user.setNombre(tempObject.optString("nombre"));
                                user.setApellidos(tempObject.optString("apellidos"));
                                user.setTemperatura(tempObject.optString("temperatura"));
                                user.setFormat(tempObject.optString("format"));
                                user.setCiudad(tempObject.optString("ciudad"));
                                user.setProvincia(tempObject.optString("provincia"));
                                usersList.add(user);
                            }
                        }
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            apiRespuesta.setSuccess(false);
            apiRespuesta.setError("Error al conectar con el servidor.");
        }

        apiRespuesta.setUsuarios(usersList);
        return apiRespuesta;
    }

    // Método nuevo para obtener un usuario específico
    private ApiRespuesta fetchUser() {
        ApiRespuesta apiRespuesta = new ApiRespuesta();
        List<Users> usersList = new ArrayList<>();

        try {
            // hacemos petición a  'listid.php'
            URL url = new URL(conexion.getUrl() + "listid.php?id=" + this.userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Se comprueba si la conexión con el servidor es correcta.
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                // recibe un json con los datos de los usuarios registrados en la aplicación.
                JSONObject jsonResponse = new JSONObject(response.toString());
                apiRespuesta.setSuccess(jsonResponse.optBoolean("success"));
                apiRespuesta.setError(jsonResponse.optString("error", null));
                // si recibimos respuesta correcta, se añaden los usuarios a la lista.
                if (apiRespuesta.isSuccess()) {
                    JSONArray tempArray = jsonResponse.optJSONArray("temp");
                    if (tempArray != null) {
                        for (int i = 0; i < tempArray.length(); i++) {
                            JSONObject tempObject = tempArray.optJSONObject(i);
                            if (tempObject != null) {
                                Users user = new Users();
                                user.setId(tempObject.optString("id"));
                                user.setNombre(tempObject.optString("nombre"));
                                user.setApellidos(tempObject.optString("apellidos"));
                                user.setTemperatura(tempObject.optString("temperatura"));
                                user.setFormat(tempObject.optString("format"));
                                user.setCiudad(tempObject.optString("ciudad"));
                                user.setProvincia(tempObject.optString("provincia"));
                                usersList.add(user);
                            }
                        }
                    }
                }
            }

            // si la respuesta no es succes
        } catch (IOException e) {
            e.printStackTrace();
            apiRespuesta.setSuccess(false);
            apiRespuesta.setError("Error al conectar con el servidor.");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return apiRespuesta;
    }



   public interface Callback {
        void onResult(ApiRespuesta result);

    }






}