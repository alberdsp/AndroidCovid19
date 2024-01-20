package com.example.covid_19app.models;

import java.util.List;


/**
 * ABF 2024
 * Clase que contempla la respuesta del web service
 *  de la aplicación controla si fue exitosa o no y devuelve un listado de usuarios
 *  o id cuando se insertan regitros
 *
 */
public class ApiRespuesta {
    private boolean success;
    private String error;
    private List<Users> users;

    private String userid;




    public ApiRespuesta() {
    }

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


    public String getUserId() {
        return userid;
    }

    public void setUserId(String temp) {
        this.userid = temp;
    }

}
