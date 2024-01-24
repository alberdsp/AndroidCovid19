package com.example.covid_19app.views.ui.medicionTemperatura;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.covid_19app.R;
import com.example.covid_19app.controllers.ApiPostTemController;
import com.example.covid_19app.models.ApiRespuesta;
import com.example.covid_19app.models.TomaDeTemperatura;

import com.example.covid_19app.views.MainActivityNav;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Fragmento que maneja los datos de medición de temperatura
 */
public class MedicionFragment extends Fragment {

    private static final String PREFERENCES_FILE_KEY = "com.example.covid_19app.PREFERENCE_FILE_KEY";
    private static final String SWITCH_STATE_KEY = "switch_state";
    private int unidadMedida; // valor del switch en el shared preferences Celsius o Fahrenheit
    private SharedPreferences sharedPreferences; // objeto para acceder al shared preferences


    private TomaDeTemperatura tomaDeTemperatura = new TomaDeTemperatura();
    private View vista;
    private Button bottonGrabar;
    private TextInputEditText textInputEditTextNombre, textInputEditTextApellidos,
            textInputEditTextTemperatura, textInputEditTextCiudad, textInputEditTextProvincia;
    private RadioGroup radioGroup;

    // Constructor por defecto
    public MedicionFragment() {

        sharedPreferences = null;
    }



    // Método que se ejecuta cuando se reanuda la actividad, establecemos el estado del switch por si ha cambiado
    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            sharedPreferences = getActivity().getSharedPreferences(PREFERENCES_FILE_KEY, MainActivityNav.MODE_PRIVATE);

            // Cambia esto para leer un valor booleano
            boolean switchState = sharedPreferences.getBoolean(SWITCH_STATE_KEY, false); // false es el valor por defecto

            // Convierte el estado del switch a un valor entero si es necesario
            unidadMedida = switchState ? 1 : 2; // Suponiendo que 1 representa 'true' y 2 representa 'false'

            inicializarComponentesUI();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // cargamos la vista del fragment
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
        // Cargamos el estado del switch y lo establecemos en el RadioGroup
        // 1 es el valor por defecto
        if (unidadMedida == 1) {
            radioGroup.check(R.id.radioButtonFahrenheit); // Asume que tienes un RadioButton con este ID
        } else {
            radioGroup.check(R.id.radioButtonCelsius); // Asume que tienes un RadioButton con este ID
        }


    }

    // Método que se encarga de configurar el botón grabar
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
     *
     * @param response tipo ApiRespuesta
     */
    private void handleApiPostResponse(ApiRespuesta response) {
        getActivity().runOnUiThread(() -> {
            if (response.isSuccess()) {
                String userId = response.getUserId(); // recibimos el id del usuario

                Bundle bundle = new Bundle();
                bundle.putString("userId", userId); // Usamos "userId" como clave

                // declaramos el navController y navegamos al fragmento informe
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.action_medicionFragment_to_informeFragment, bundle);
            } else {
                // Mostrar mensaje de error
                String errorMessage = response.getError();

            }
        });
    }


}
