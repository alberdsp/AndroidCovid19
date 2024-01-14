package com.example.covid_19app.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid_19app.R;

/**
 * Fragment que de configuración
 */
public class ConfigFragment extends Fragment {
    // Constructo por fedecto
    public ConfigFragment() {
        //
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // cargamos el fragment
        return inflater.inflate(R.layout.fragment_config, container, false);
    }
}