package com.example.covid_19app.views.ui.conversor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.covid_19app.R;
import com.google.android.material.textfield.TextInputEditText;

/**
 *  clase fragmento para el conversor de temperaturas
 */
public class ConversorFragment extends Fragment {

    private View vista;
    private TextInputEditText textInputCelsius;
    private TextInputEditText textInputFahrenheit;
    private TextView textOutputCelsius;
    private TextView textOutputFahrenheit;
    private ConversorViewModel conversorViewModel;

    public ConversorFragment() {
        // constructor por defecto
    }


    // método para crear la vista del fragmento
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_conversor, container, false);
        textInputCelsius = vista.findViewById(R.id.TextinputCelsius);
        textInputFahrenheit = vista.findViewById(R.id.textinputFahren);
        textOutputCelsius = vista.findViewById(R.id.textViewCelsius);
        textOutputFahrenheit = vista.findViewById(R.id.textViewFahrenheit);

        vista.findViewById(R.id.buttonCalcular).setOnClickListener(v -> calcularTemperaturas());
        return vista;
    }


    // cargamos la vista del fragment
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        conversorViewModel = new ViewModelProvider(this).get(ConversorViewModel.class);

        conversorViewModel.getCelsiusToFahrenheitResult().observe(getViewLifecycleOwner(), result -> {
            textOutputFahrenheit.setText(result);
        });

        conversorViewModel.getFahrenheitToCelsiusResult().observe(getViewLifecycleOwner(), result -> {
            textOutputCelsius.setText(result);
        });
    }

    // método para calcular las temperaturas
    private void calcularTemperaturas() {
        if (!textInputCelsius.getText().toString().isEmpty()) {
            int celsius = Integer.parseInt(textInputCelsius.getText().toString());
            conversorViewModel.convertCelsiusToFahrenheit(celsius);
        }

        if (!textInputFahrenheit.getText().toString().isEmpty()) {
            int fahrenheit = Integer.parseInt(textInputFahrenheit.getText().toString());
            conversorViewModel.convertFahrenheitToCelsius(fahrenheit);
        }
    }
}
