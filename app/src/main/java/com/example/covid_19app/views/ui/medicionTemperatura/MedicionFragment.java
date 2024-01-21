package com.example.covid_19app.views.ui.medicionTemperatura;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.covid_19app.R;
import com.example.covid_19app.controllers.ApiPostTemController;
import com.example.covid_19app.models.ApiRespuesta;
import com.example.covid_19app.models.TomaDeTemperatura;
import com.example.covid_19app.views.ui.informe.InformeFragment;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Fragmento que maneja los datos de medición de temperatura
 */
public class MedicionFragment extends Fragment {

    private TomaDeTemperatura tomaDeTemperatura = new TomaDeTemperatura();
    private View vista;
    private Button bottonGrabar;
    private TextInputEditText textInputEditTextNombre, textInputEditTextApellidos,
            textInputEditTextTemperatura, textInputEditTextCiudad, textInputEditTextProvincia;
    private RadioGroup radioGroup;
    // Constructor por defecto
    public MedicionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_medicion, container, false);
        inicializarComponentesUI();
        grabarButton();
        return vista;
    }

    // Método que se encarga de inicializar los componentes de la interfaz de usuario
    private void inicializarComponentesUI() {
        textInputEditTextNombre = vista.findViewById(R.id.textInputEditNombre);
        textInputEditTextApellidos = vista.findViewById(R.id.textInputEditApellidos);
        textInputEditTextCiudad = vista.findViewById(R.id.textInputEditCiudad);
        textInputEditTextProvincia = vista.findViewById(R.id.textInputEditProvincia);
        textInputEditTextTemperatura = vista.findViewById(R.id.textInputEditTemperatura);
        radioGroup = vista.findViewById(R.id.radioGroup);
        bottonGrabar = vista.findViewById(R.id.buttonFinalizar);
    }

    // Método que se encarga de configurar el botón de finalizar
    private void grabarButton() {
        bottonGrabar.setOnClickListener(v -> {
            TomaDeTemperatura datosTemperatura = cargarValores();
            ApiPostTemController apiPostTemController = new ApiPostTemController(this::handleApiPostResponse, datosTemperatura);
            new Thread(apiPostTemController).start();
        });
    }

    // Método que se encarga de cargar los valores de la vista en el objeto tomaDeTemperatura
    private TomaDeTemperatura cargarValores() {
        tomaDeTemperatura.setNombre(textInputEditTextNombre.getText().toString());
        tomaDeTemperatura.setApellidos(textInputEditTextApellidos.getText().toString());
        tomaDeTemperatura.setCiudad(textInputEditTextCiudad.getText().toString());
        tomaDeTemperatura.setProvincia(textInputEditTextProvincia.getText().toString());
        setTemperaturaYTipo();
        return tomaDeTemperatura;
    }

    // Método que se encarga de establecer la temperatura y el tipo de temperatura
    private void setTemperaturaYTipo() {
        try {
            int temperatura = Integer.parseInt(textInputEditTextTemperatura.getText().toString());
            tomaDeTemperatura.setTemperatura(temperatura);
        } catch (NumberFormatException e) {
            // Tratar excepción o establecer un valor por defecto
        }

        int tipoTemperatura = radioGroup.getCheckedRadioButtonId() == R.id.radioButtonCelsius ? 1 : 2;
        tomaDeTemperatura.setTipotemperatura(tipoTemperatura);
    }


    /**
     * Método que se ejecuta cuando se recibe la respuesta de la API
     * @param response tipo ApiRespuesta
     */
    private void handleApiPostResponse(ApiRespuesta response) {
        getActivity().runOnUiThread(() -> {
            if (response.isSuccess()) {
                String userId = response.getUserId(); // recibimos el id del usuario

                Bundle bundle = new Bundle();
                bundle.putString("userId", userId); // Usamos "userId" como clave

                // Usando NavController para navegar
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.action_medicionFragment_to_informeFragment, bundle);
            } else {
                // Mostrar mensaje de error
                String errorMessage = response.getError();
                // Aquí deberías manejar el error, como mostrar un Toast o un diálogo
            }
        });
    }



}
