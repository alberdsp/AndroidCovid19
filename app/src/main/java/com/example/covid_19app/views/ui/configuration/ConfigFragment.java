package com.example.covid_19app.views.ui.configuration;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.covid_19app.R;

public class ConfigFragment extends Fragment {

    private ConfigViewModel configViewModel;
    private Switch switchState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_config, container, false);

        // Inicializamos el switch y el ViewModel
        switchState = vista.findViewById(R.id.switchstate);
        configViewModel = new ViewModelProvider(this).get(ConfigViewModel.class);

        // observador para actualizar el estado del switch
        configViewModel.getSwitchState().observe(getViewLifecycleOwner(), newState -> {
            switchState.setChecked(newState);
        });

        // establecer el listener para guardar el estado del switch
        switchState.setOnCheckedChangeListener((buttonView, isChecked) -> {
            configViewModel.saveSwitchState(isChecked);
        });

        return vista;
    }
}
