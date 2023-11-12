package com.example.covid_19app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * ABF 2023
 * Fragment que contiene el informe de la medición
 */
public class InformeFragment extends Fragment {


    // declaramos atributos de la clase que vendrán por parametro al instanciarla
     String nombre,apellidos,ciudad,provincia;

   // int que recibe 1 si es celsius y 2 si es fahrenheit

     int tipotemperatura,temperatura;

     // declaramos los objetos que vamos a utilizar en la vista

    View vista;
    TextView textViewNombre,textViewApellidos,textViewTemperatura,textViewciudad,textViewProvincia;
    Button buttonmenu,buttonresultado;

    // constructor por defecto
    public InformeFragment() {

    }

    /**
     *  constructor que recibe por parametro
     * @param nombre     String nombre
     * @param apellidos   String apellidos
     * @param temperatura     int temperatura
     * @param tipotemperatura   int tipotemperatura  1 para celsius 2 para fahrenheit
     * @param ciudad          String ciudad
     * @param provincia        String provincia
     */
    public InformeFragment(String nombre,String apellidos,int temperatura,int tipotemperatura,
                           String ciudad,String provincia) {

        this.nombre = nombre;
        this.apellidos = apellidos;
        this.temperatura = temperatura;
        this.tipotemperatura = tipotemperatura;
        this.ciudad = ciudad;
        this.provincia = provincia;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void onResume() {
        super.onResume();
       // al recibir el foco cargamos los valores
        cargarValores();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // declaramos la vista
         vista = inflater.inflate(R.layout.fragment_informe, container, false);

         textViewNombre = vista.findViewById(R.id.textViewNombre);
         textViewApellidos= vista.findViewById(R.id.textViewApellidos);
         textViewTemperatura = vista.findViewById(R.id.textViewTemperatura);
         textViewciudad = vista.findViewById(R.id.textViewCiudad);
         textViewProvincia = vista.findViewById(R.id.textViewProvincia);



        // declaramos el botón Finalizar que pasa al fragment informe
        buttonresultado = vista.findViewById(R.id.buttonResultadoTest);


        // declaramos el botón Finalizar que pasa al fragment informe
        buttonmenu = vista.findViewById(R.id.buttonMenu);

        buttonmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment menuFragment = new MenuFragment();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.fragmentContainerViewMenu, menuFragment );
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

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


    /**
     * método que establece los valores a los objetos del fragment
     */

    public void cargarValores(){


        //  establecemos los valores de los textviews
        textViewNombre.setText(nombre);
        textViewApellidos.setText(apellidos);
        textViewTemperatura.setText(String.valueOf(temperatura));
        textViewciudad.setText(ciudad);
        textViewProvincia.setText(provincia);


        // si la temperatura es buena ponemos en verde el botón sino en rojo
        if (alertaTemp(temperatura,tipotemperatura)){

            buttonresultado.setBackgroundColor(Color.parseColor("#AF4C63"));

        }else {


            buttonresultado.setBackgroundColor(Color.parseColor("#4CAF50"));


        };

    };




}