package com.example.covid_19app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Fragmente que contiene los datos de medición de temperatura
 */
public class MedicionTempFragment extends Fragment {

   // constructo por defecto
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
        View vista = inflater.inflate(R.layout.fragment_medicion_temp, container, false);

        // declaramos el botón Finalizar que pasa al fragment informe
        Button buttonFinalizar = vista.findViewById(R.id.buttonFinalizar);


          buttonFinalizar.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                 Fragment informeFragment = new InformeFragment();

                  FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                          .beginTransaction();
                  transaction.replace(R.id.fragmentContainerViewMenu, informeFragment);
                  transaction.addToBackStack(null);
                  transaction.commit();

              }
          });



        return vista;
    }
}