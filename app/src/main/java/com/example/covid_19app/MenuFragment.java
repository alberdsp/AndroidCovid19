package com.example.covid_19app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Creamos el fragment para el menu de botones y navegación
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
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
        View vista = inflater.inflate(R.layout.fragment_menu, container, false);

        // declaramos el botón medición temperatura
        Button buttonMedicion = vista.findViewById(R.id.buttonMedicion);
        // declaramos el botón conversor
        Button buttonConversor = vista.findViewById(R.id.buttonConversor);
        // declaramos el botón configuración
        Button buttonConfig = vista.findViewById(R.id.buttonConfiguracion);
        // declaramos el botón cerrar sesión
        Button buttonCerrar = vista.findViewById(R.id.buttonLogout);

        // creamos el método listener para cada botón  que abre el fragment pasado por parametro
        // al método porpio abrinfragment
        buttonMedicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirFragment(new MedicionTempFragment());
            }
        });

        buttonConversor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirFragment(new ConversorFragment());
            }
        });

        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirFragment(new ConfigFragment());
            }
        });


        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        return vista;
    }


    // creo el método para abrir el fragment correspondiente en función del botón pulsado
    private void abrirFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fragmentContainerViewMenu, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // método para cerrar la sesión  volver al login en el MainActivity
    private void cerrarSesion() {

        // Abrimos el MainActivity
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }







}