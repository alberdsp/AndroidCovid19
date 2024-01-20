package com.example.covid_19app.views.ui.medicionTemperatura;

import androidx.lifecycle.ViewModel;

import com.example.covid_19app.views.TomaDeTemperatura;

public class MedicionViewModel extends ViewModel {
    private TomaDeTemperatura tomaDeTemperatura = new TomaDeTemperatura();

    public void setNombre(String nombre) {
        tomaDeTemperatura.setNombre(nombre);
    }

    public void setApellidos(String apellidos) {
        tomaDeTemperatura.setApellidos(apellidos);
    }

    public void setTemperatura(String temperatura) {
        tomaDeTemperatura.setTemperatura(Integer.parseInt(temperatura));
    }

    public void setCiudad(String ciudad) {
        tomaDeTemperatura.setCiudad(ciudad);
    }

    public void setProvincia(String provincia) {
        tomaDeTemperatura.setProvincia(provincia);
    }


    public TomaDeTemperatura getTomaDeTemperatura() {
        return tomaDeTemperatura;
    }
}