package com.example.covid_19app;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Fragment que contiene el informe de la medición
 */
public class InformeFragment extends Fragment {

    // constructor por defecto
    public InformeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // declaramos la vista
        View vista = inflater.inflate(R.layout.fragment_informe, container, false);

        // declaramos el botón Finalizar que pasa al fragment informe
        Button buttonresultado = vista.findViewById(R.id.buttonResultadoTest);

        // variables para tratar la temperatura y tipo 1 celsius 2 faherenheit

        int temperatura = 101;
        int tipotemperatura = 2;


        // si la temperatura es buena ponemos en verde el botón sino en rojo
        if (alertaTemp(temperatura,tipotemperatura)){

            buttonresultado.setBackgroundColor(Color.parseColor("#AF4C63"));

        }else {


            buttonresultado.setBackgroundColor(Color.parseColor("#4CAF50"));


        };

        return vista;
    }


    /**
     * Metodo que evalua la temperatura tomada para ver si es correcta
     * @param temperatura     entero con la temperatura tomada
     * @param tipotemperatura  entero 1 = Celsius , 2 =  Fahrenheit
     * @return  true si la temperatura es buena, false si es alerta covid
     */

    public boolean alertaTemp(int temperatura, int tipotemperatura){



        switch (tipotemperatura){

            case 1:
                // si supera 38º celsius damos alerta

                if(temperatura> 38){
                    return true;
                }else{
                    return false;
                }

            case 2:
                // si supera 100º fahrenheit damos alerta

                if(temperatura> 100){
                    return true;
                }else{
                    return false;
                }


                // capturamos la excepción si se introduce un valor erroneo en el tipo
            default:
                throw new IllegalStateException("valor inesperado: " + tipotemperatura);
        }




    };


}