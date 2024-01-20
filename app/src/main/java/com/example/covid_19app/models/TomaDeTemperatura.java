package com.example.covid_19app.models;

/**
 * ABF 2023
 * clase que contempla las mediciones de temperatura
 */

public class TomaDeTemperatura {

    private String nombre,apellidos,ciudad,provincia;
    private int temperatura,tipotemperatura;


    // constructor por defecto
    public  TomaDeTemperatura(){

    }

    /**
     *  Constructor con parámetros
     * @param nombre
     * @param apellidos
     * @param temperatura
     * @param tipotemperatura  int 1 para celsius 2 para fahrenheit
     * @param ciudad
     * @param provincia
     */

    public TomaDeTemperatura(String nombre,String apellidos, int temperatura, int tipotemperatura,
                             String ciudad,String provincia){

        this.nombre = nombre;
        this.apellidos = apellidos;
        this.temperatura = temperatura;
        this.tipotemperatura = tipotemperatura;
        this.ciudad = ciudad;
        this.provincia = provincia;


    }


    // generamos los getter y setters

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

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    /**
     *  devuleve el tipo de temperatura
     * @return    1 si celsius 2 si fahrenheit
     */
    public int getTipotemperatura() {
        return tipotemperatura;
    }

    /**
     *  establece el tipo de temperatura
     * @param tipotemperatura    1 si celsius 2 si fahrenheit
     */
    public void setTipotemperatura(int tipotemperatura) {
        this.tipotemperatura = tipotemperatura;
    }
}
