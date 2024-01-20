package com.example.covid_19app.views;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.covid_19app.R;
import com.example.covid_19app.models.TomaDeTemperatura;

/**
 * ABF 2023
 * Fragment que contiene el informe de la medición
 */
public class InformeFragment extends Fragment {


    // declaramos atributos de la clase que vendrán por parametro al instanciarla
    TomaDeTemperatura tomaDeTemperatura = new TomaDeTemperatura();

    // declaramos los objetos que vamos a utilizar en la vista

    View vista;
    TextView textViewNombre,textViewApellidos,textViewTemperatura,textViewciudad,textViewProvincia;
    Button buttonmenu,buttonresultado;

    // constructor por defecto
    public InformeFragment() {

    }

    public InformeFragment(TomaDeTemperatura tomaDeTemperatura) {

        this.tomaDeTemperatura = tomaDeTemperatura;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void onResume() {
        super.onResume();
        // al recibir el foco cargamos los valores
        cargarValores();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // declaramos la vista
        vista = inflater.inflate(R.layout.fragment_informe, container, false);

        textViewNombre = vista.findViewById(R.id.textViewNombre);
        textViewApellidos= vista.findViewById(R.id.textViewApellidos);
        textViewTemperatura = vista.findViewById(R.id.textViewTemperatura);
        textViewciudad = vista.findViewById(R.id.textViewCiudad);
        textViewProvincia = vista.findViewById(R.id.textViewProvincia);



        // declaramos el botón Finalizar que pasa al fragment informe
        buttonresultado = vista.findViewById(R.id.buttonResultadoTest);


        // declaramos el botón Finalizar que pasa al fragment informe
        buttonmenu = vista.findViewById(R.id.buttonMenu);

        buttonmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment menuFragment = new MenuFragment();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.fragmentContainerViewMenu, menuFragment );
                transaction.addToBackStack(null);
                transaction.commit();


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
     * método que establece los valores a los objetos del fragment
     */

    public void cargarValores(){


        //  establecemos los valores de los textviews
        textViewNombre.setText(tomaDeTemperatura.getNombre());
        textViewApellidos.setText(tomaDeTemperatura.getApellidos());
        textViewTemperatura.setText(String.valueOf(tomaDeTemperatura.getTemperatura()));
        textViewciudad.setText(tomaDeTemperatura.getCiudad());
        textViewProvincia.setText(tomaDeTemperatura.getProvincia());


        // si la temperatura es buena ponemos en verde el botón sino en rojo
        if (alertaTemp(tomaDeTemperatura.getTemperatura(), tomaDeTemperatura.getTipotemperatura())){

            buttonresultado.setBackgroundColor(Color.parseColor("#AF4C63"));

        }else {


            buttonresultado.setBackgroundColor(Color.parseColor("#4CAF50"));


        };

    };




}