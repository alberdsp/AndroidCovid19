package com.example.covid_19app.views.ui.conversor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


// clase vista modelo para el conversor de temperaturas
public class ConversorViewModel extends ViewModel {

    private final MutableLiveData<String> celsiusToFahrenheitResult = new MutableLiveData<>();
    private final MutableLiveData<String> fahrenheitToCelsiusResult = new MutableLiveData<>();



    // método para convertir de celsius a fahrenheit
    public void convertCelsiusToFahrenheit(int celsius) {
        int fahrenheit = ((celsius * 9) / 5) + 32;
        celsiusToFahrenheitResult.setValue(String.valueOf(fahrenheit));
    }

    //
    public void convertFahrenheitToCelsius(int fahrenheit) {
        int celsius = ((fahrenheit - 32) * 5) / 9;
        fahrenheitToCelsiusResult.setValue(String.valueOf(celsius));
    }

    // métodos para obtener los resultados de las conversiones
    public LiveData<String> getCelsiusToFahrenheitResult() {
        return celsiusToFahrenheitResult;
    }

    public LiveData<String> getFahrenheitToCelsiusResult() {
        return fahrenheitToCelsiusResult;
    }
}
