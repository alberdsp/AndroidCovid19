package com.example.covid_19app.views.ui.cerrar;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.covid_19app.views.MainActivity;


/**
 * Fragment  para cerrar sesión
 */
public class CerrarFragment extends Fragment {

    public CerrarFragment() {
        // Constructor por defecto
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Llamamos a cerrar sesión cuando el fragment se crea
        cerrarSesion();
    }


    /**
     * Método para cerrar sesión
     */
    public void cerrarSesion() {
        // si no hay activity no hacer nada
        if (getActivity() != null) {
            // Cerramos MainActivityNAv y volvemos al MainActivity cargando la pantalla de login
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish(); // Cierramos la activity
        }
    }
}
