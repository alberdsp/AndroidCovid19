package com.example.covid_19app.controllers;

import com.example.covid_19app.models.ApiListRespuesta;
import com.example.covid_19app.models.Conexion;
import com.example.covid_19app.models.Users;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListController implements Runnable {

    private final Callback callback;

    public ListController(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        ApiListRespuesta response = fetchUsers();
        if (callback != null) {
            callback.onResult(response);
        }
    }

    private ApiListRespuesta fetchUsers() {
        ApiListRespuesta apiResponse = new ApiListRespuesta();
        List<Users> usersList = new ArrayList<>();
        Conexion conexion = new Conexion();

        try {
            URL url = new URL(conexion.getUrl() + "list.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject jsonResponse = new JSONObject(response.toString());

                apiResponse.setSuccess(jsonResponse.getBoolean("success"));
                apiResponse.setError(jsonResponse.optString("error", null));

                if (apiResponse.isSuccess() && apiResponse.getError() == null) {
                    JSONArray tempArray = jsonResponse.getJSONArray("temp");

                    for (int i = 0; i < tempArray.length(); i++) {
                        JSONObject tempObject = tempArray.getJSONObject(i);
                        Users user = new Users(tempObject);
                        usersList.add(user);
                    }

                    apiResponse.setUsuarios(usersList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            apiResponse.setSuccess(false);
            apiResponse.setError("Error al conectar con el servidor.");
        }

        return apiResponse;
    }

    public interface Callback {
        void onResult(ApiListRespuesta result);
    }
}
