package com.example.covid_19app.views.ui.conversor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.covid_19app.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Fragment que contiene el conversor de temperatura
 */
public class ConversorFragment extends Fragment {

    // declaramos los objetos que vamos a utilizar en el fragment.
    View vista;
    Button buttoncalcular;

    // instanciamos los inputtext
    TextInputEditText textinputcelsius;
    TextInputEditText textinputfahren;
    // instanciamos los textview

    TextView textoutcelsius;
    TextView textoutfahren;

    private ConversorViewModel conversorViewModel;


    // Constructor por defecto
    public ConversorFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onResume() {

        // al establecerse el foco en el fragmen actualizamos las temperaturas
        //por si el movil se gira, que se actualice.
        calcularTemperaturas();

        super.onResume();
    };


    // al cargar la vista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        vista = inflater.inflate(R.layout.fragment_conversor, container, false);

        // instanciamos el botoón de calcular

        buttoncalcular = vista.findViewById(R.id.buttonCalcular);

        // instanciamos los inputtext
        textinputcelsius = vista.findViewById(R.id.TextinputCelsius);
        textinputfahren = vista.findViewById(R.id.textinputFahren);
        // instanciamos los textview

        textoutcelsius = vista.findViewById(R.id.textViewCelsius);
        textoutfahren = vista.findViewById(R.id.textViewFahrenheit);


        buttoncalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calcularTemperaturas();


            }
        });



        // cargamos el fragment
        return vista;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        conversorViewModel = new ViewModelProvider(this).get(ConversorViewModel.class);
    }

    /**
     *  Método que transforma de celsius a fahrenheit
     * @param celsius   int grados celsius a convertir
     * @return    devuelve un int con el valor en fahrenheit
     */
    public int conversorCeltoFahren(int celsius){

        int fahrenheit=0;


        fahrenheit = ((celsius*9)/5)+32;


        return fahrenheit;

    }

    /**
     *  Método que transforma de fahrenheit a celsius
     * @param fahrenheit  int grados fahrenheit  a convertir
     * @return    devuelve un int con el valor en celsius
     */
    public int conversorFahrentoCel(int fahrenheit){

        int celsius=0;


        celsius = ((fahrenheit-32)*5)/9;


        return celsius;

    }

    /**
     * método que realiza los calculos de temperaturas y los establece en pantalla
     */
    public void calcularTemperaturas() {
        int celsiusIn = 0;
        int fahrenheitIn = 0;

        if (!textinputcelsius.getText().toString().isEmpty()) {
            celsiusIn = Integer.parseInt(textinputcelsius.getText().toString());
            conversorViewModel.convertCelsiusToFahrenheit(celsiusIn);
        }

        if (!textinputfahren.getText().toString().isEmpty()) {
            fahrenheitIn = Integer.parseInt(textinputfahren.getText().toString());
            conversorViewModel.convertFahrenheitToCelsius(fahrenheitIn);
        }
    }






}