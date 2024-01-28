package com.example.covid_19app.models;

import java.util.ArrayList;
import java.util.List;


/**
 *  Clase para el tratamiento de coordenadas
 * @latitud
 * @logitud
 */
public class Coordenadas {
    private double latitud;
    private double longitud;



    // constructor por defecto
     public Coordenadas() {

     }

     public Coordenadas(double latitud,double longitud){

         this.latitud=latitud;
         this.longitud= longitud;


     }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}