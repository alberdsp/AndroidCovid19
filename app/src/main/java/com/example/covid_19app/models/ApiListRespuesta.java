package com.example.covid_19app.models;

import java.util.Collection;
import java.util.List;


/**
 * ABF 2024
 * Clase que contempla la respuesta del web service
 *  de la aplicación controla si fue exitosa o no y devuelve un listado de usuarios
 *
 */
public class ApiListRespuesta {
    private boolean success;
    private String error;
    private List<Users> users;

    // getters y setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Users> getUsuarios() {
        return users;
    }

    public void setUsuarios(List<Users> users) {
        this.users = users;
    }


    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
