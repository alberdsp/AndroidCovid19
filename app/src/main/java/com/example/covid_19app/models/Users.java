package com.example.covid_19app.models;

import org.json.JSONObject;
/**
 * ABF 2024
 * Clase que contempla los usuarios
 *  de la aplicación
 *
 */
public class Users {

    int id;  // es autoincremental en la base de datos
    String nombre;
    String apellidos;
    int temperatura;
    int format; // 1 para celsius, 2 para farenheit

    String ciudad;
    String provincia;


    // constructor por defecto de la clase sin parámetros
    public Users() {
    }


    // constructor con param
    public Users(String nombre, String apellidos, int temperatura, int format, String ciudad, String provincia) {

        this.nombre = nombre;
        this.apellidos = apellidos;
        this.temperatura = temperatura;
        this.format = format;
        this.ciudad = ciudad;
        this.provincia = provincia;
    }





    // Constructor que acepta un JSONObject
    public Users(JSONObject jsonObject) {
        if (jsonObject != null) {
            this.id = jsonObject.optInt("id");
            this.nombre = jsonObject.optString("nombre");
            this.apellidos = jsonObject.optString("apellidos");
            this.temperatura = jsonObject.optInt("temperatura");
            this.format = jsonObject.optInt("format");
            this.ciudad = jsonObject.optString("ciudad");
            this.provincia = jsonObject.optString("provincia");
        }
    }

      // getters y setters de las variables de la clase


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }




}
