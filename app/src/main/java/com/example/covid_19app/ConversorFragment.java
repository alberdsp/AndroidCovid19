package com.example.covid_19app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment que contiene el conversor de temperatura
 */
public class ConversorFragment extends Fragment {

    // Constructor por defecto
    public ConversorFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // cargamos el fragment
        return inflater.inflate(R.layout.fragment_conversor, container, false);
    }
}