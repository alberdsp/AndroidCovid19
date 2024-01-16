package com.example.covid_19app.views.ui.conversor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



/**
 * Clase que se encarga la vista modelo del conversor de temperatura
 */
public class ConversorViewModel extends ViewModel {

    private final MutableLiveData<Integer> celsiusToFahrenheit;
    private final MutableLiveData<Integer> fahrenheitToCelsius;

    public ConversorViewModel() {
        celsiusToFahrenheit = new MutableLiveData<>();
        fahrenheitToCelsius = new MutableLiveData<>();
    }

    public LiveData<Integer> getCelsiusToFahrenheit() {
        return celsiusToFahrenheit;
    }

    public LiveData<Integer> getFahrenheitToCelsius() {
        return fahrenheitToCelsius;
    }

    public void convertCelsiusToFahrenheit(int celsius) {
        int fahrenheit = ((celsius * 9) / 5) + 32;
        celsiusToFahrenheit.setValue(fahrenheit);
    }

    public void convertFahrenheitToCelsius(int fahrenheit) {
        int celsius = ((fahrenheit - 32) * 5) / 9;
        fahrenheitToCelsius.setValue(celsius);
    }
}