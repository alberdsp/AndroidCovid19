package com.example.covid_19app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        // Cargamos el fragment
        return inflater.inflate(R.layout.fragment_informe, container, false);
    }
}