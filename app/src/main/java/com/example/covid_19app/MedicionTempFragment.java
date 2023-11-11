package com.example.covid_19app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        // Cargamos el fragment
        return inflater.inflate(R.layout.fragment_medicion_temp, container, false);
    }
}