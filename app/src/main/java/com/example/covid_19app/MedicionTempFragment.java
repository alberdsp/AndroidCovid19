package com.example.covid_19app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;

/**
 * Fragmente que contiene los datos de medición de temperatura
 */
public class MedicionTempFragment extends Fragment {


    // declaramos los objetos que vamos a utilizar
    TomaDeTemperatura tomaDeTemperatura = new TomaDeTemperatura();
    View vista;
    Button buttonFinalizar;

    TextInputEditText textInputEditTextNombre,textInputEditTextApellidos,textInputEditTextTemperatura,
    textInputEditTextCiudad,textInputEditTextProvincia;
    RadioGroup radioGroup;





   // constructor por defecto
    public MedicionTempFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // declaramos la vista
         vista = inflater.inflate(R.layout.fragment_medicion_temp, container, false);

        // declaramos el botón Finalizar que pasa al fragment informe
         buttonFinalizar = vista.findViewById(R.id.buttonFinalizar);


          buttonFinalizar.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                 Fragment informeFragment = new InformeFragment(cargarValores() );

                  FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                          .beginTransaction();
                  transaction.replace(R.id.fragmentContainerViewMenu, informeFragment);
                  transaction.addToBackStack(null);
                  transaction.commit();

              }
          });



        return vista;
    }


    /**
     * Método para cargar los valores introducidos
     */
    private TomaDeTemperatura cargarValores(){


        // instanciamos los objetos textinputedit de la vista
        textInputEditTextNombre = vista.findViewById(R.id.textInputEditNombre);
        textInputEditTextApellidos = vista.findViewById(R.id.textInputEditApellidos);
        textInputEditTextCiudad = vista.findViewById(R.id.textInputEditCiudad);
        textInputEditTextProvincia = vista.findViewById(R.id.textInputEditProvincia);
        textInputEditTextTemperatura = vista.findViewById(R.id.textInputEditTemperatura);
        radioGroup = vista.findViewById(R.id.radioGroup);

        tomaDeTemperatura.setNombre(textInputEditTextNombre.getText().toString());
        tomaDeTemperatura.setApellidos(textInputEditTextApellidos.getText().toString());
        tomaDeTemperatura.setCiudad(textInputEditTextCiudad.getText().toString());
        tomaDeTemperatura.setProvincia(textInputEditTextProvincia.getText().toString());

        String temperaturaStr = textInputEditTextTemperatura.getText().toString();

        try {
            int temperatura = Integer.parseInt(temperaturaStr);
            tomaDeTemperatura.setTemperatura(temperatura);
        } catch (NumberFormatException e) {
            //capturamos la excepción si viene nula la temperatura
        }


          // hacemos la comparación para saber si está chequeado el radioButton
          //  con id radioButtonCelsius, si lo está es tipo 1 sino tipo 2
          if(radioGroup.getCheckedRadioButtonId() == R.id.radioButtonCelsius){

              tomaDeTemperatura.setTipotemperatura(1);
          }else{
              tomaDeTemperatura.setTipotemperatura(2);

          }


        return tomaDeTemperatura;


    }



}