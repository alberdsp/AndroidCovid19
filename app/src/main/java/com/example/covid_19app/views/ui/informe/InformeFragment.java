package com.example.covid_19app.views.ui.informe;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.covid_19app.R;
import com.example.covid_19app.controllers.ApiGetListController;
import com.example.covid_19app.models.ApiRespuesta;
import com.example.covid_19app.models.TomaDeTemperatura;
import com.example.covid_19app.models.Users;


import java.util.ArrayList;
import java.util.List;

/**
 * ABF 2023
 * Fragment que contiene el informe de la medición
 */
public class InformeFragment extends Fragment implements ApiGetListController.Callback {


    // declaramos el id del usuario que viene por parametro
    private String userId;

    private Users user;
    private List<Users> usersList = new ArrayList<>();


    // declaramos los objetos que vamos a utilizar en la vista

    View vista;
    TextView textViewNombre,textViewApellidos,textViewTemperatura,textViewciudad,textViewProvincia;
    Button buttonmenu,buttonresultado;

    // constructor por defecto
    public InformeFragment() {

    }


    public void onResume() {
        super.onResume();


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // declaramos la vista
        vista = inflater.inflate(R.layout.fragment_informe, container, false);

        // recuperamos los datos que vienen por parametro
        Bundle bundle = getArguments();
        if (bundle != null) {
            userId = bundle.getString("userId");
            // Usar userId como sea necesario
        }


        textViewNombre = vista.findViewById(R.id.textViewNombre);
        textViewApellidos= vista.findViewById(R.id.textViewApellidos);
        textViewTemperatura = vista.findViewById(R.id.textViewTemperatura);
        textViewciudad = vista.findViewById(R.id.textViewCiudad);
        textViewProvincia = vista.findViewById(R.id.textViewProvincia);



        // declaramos el botón Finalizar que pasa al fragment informe
        buttonresultado = vista.findViewById(R.id.buttonResultadoTest);


        // declaramos el botón Finalizar que pasa al fragment informe
        buttonmenu = vista.findViewById(R.id.buttonMenu);

        cargarValores();

        buttonmenu.setOnClickListener(new View.OnClickListener() {

                                              @Override
                                              public void onClick(View v) {
                                                  // inicalizamos el navegation controller
                                                  NavController navController = NavHostFragment.findNavController(InformeFragment.this);
                                                  navController.navigate(R.id.homeFragment); // volvemos al home
                                              }

                                          });




        return vista;
    }


    /**
     * Metodo que evalua la temperatura tomada para ver si es correcta
     * @param temperatura     entero con la temperatura tomada
     * @param tipotemperatura  entero 1 = Celsius , 2 =  Fahrenheit
     * @return  false si la temperatura es buena, true si es alerta covid
     */

    public boolean alertaTemp(int temperatura, int tipotemperatura){



        switch (tipotemperatura){

            case 1:
                // si supera 38º celsius damos alerta

                if(temperatura> 38){
                    return true;
                }else{
                    return false;
                }

            case 2:
                // si supera 100º fahrenheit damos alerta

                if(temperatura> 100){
                    return true;
                }else{
                    return false;
                }


                // capturamos la excepción si se introduce un valor erroneo en el tipo
            default:
                throw new IllegalStateException("valor inesperado: " + tipotemperatura);
        }




    };


    /**
     * método que realiza la llamada a la API para obtener los datos del user
     */

    public void cargarValores() {


        ApiGetListController apiGetListController = new ApiGetListController(this::handleApiGetResponse, userId);


        new Thread(apiGetListController).start();


    }

    /**
     * método que establece los valores a los objetos del fragment
     */
    public void actualizarUI(){

        //  establecemos los valores de los textviews
        textViewNombre.setText(user.getNombre());
        textViewApellidos.setText(user.getApellidos());
        textViewTemperatura.setText(String.valueOf(user.getTemperatura()));
        textViewciudad.setText(user.getCiudad());
        textViewProvincia.setText(user.getProvincia());


        // si la temperatura es buena ponemos en verde el botón sino en rojo
        if (alertaTemp(user.getTemperatura(), user.getFormat())){

            buttonresultado.setBackgroundColor(Color.parseColor("#AF4C63"));

        }else {

            buttonresultado.setBackgroundColor(Color.parseColor("#4CAF50"));

        };

    }

    /**
     * Método que se ejecuta cuando se recibe la respuesta de la API
     * @param apiRespuesta tipo ApiRespuesta
     */

    private void handleApiGetResponse(ApiRespuesta apiRespuesta) {
        getActivity().runOnUiThread(() -> {
            usersList = apiRespuesta.getUsuarios();

            // si no es nula la lista
            if (usersList != null) {
                user = usersList.get(0) ;
                actualizarUI();
            } else {
                // será nulo
            }
        });
    }

    /**
     * Método que se ejecuta cuando se recibe la respuesta de la API
     * @param result tipo ApiRespuesta con lista de un solo user filtrado por id
     */

    @Override
    public void onResult(ApiRespuesta result) {
        getActivity().runOnUiThread(() -> {
            if (result.isSuccess() && !result.getUsuarios().isEmpty()) {
                user = result.getUsuarios().get(0);
            } else {
                // Manejar el caso de error o lista vacía
            }
        });
    }
    ;



}
