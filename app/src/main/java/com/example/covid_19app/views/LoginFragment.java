package com.example.covid_19app.views;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.covid_19app.R;
import com.example.covid_19app.controllers.LoginServicio;
import com.example.covid_19app.models.User;


/**
 * Fragment que contiene el login
 */
public class LoginFragment extends Fragment {


    // declaramos los objetos que vamos a utilizar en la vista
    private TextView textLogin;
    private TextView textPassword;
    private Button buttonLogin;

    private Switch recordarContrasena;

    private User usuario = new User();



    // constructor por defecto, creamos la vista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_login, container, false);

        textLogin = view.findViewById(R.id.username);
        textPassword = view.findViewById(R.id.password);
        buttonLogin = view.findViewById(R.id.login);
        recordarContrasena = view.findViewById(R.id.recordar);
        // si usuario.getGuardar(getActivity()).equals("SI") no es nulo



        // si el switch está activado, recupera los datos del shared preferences
        if(usuario.getGuardar(getActivity()).equals("SI")){
             recordarContrasena.setChecked(true);
              textLogin.setText(usuario.getUsuarioShared(getActivity()));
              textPassword.setText(usuario.getPasswordShared(getActivity()));

        }



        // listener del botón login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hacerLogin();
            }
        });

        return view;
    }


    /**
     * Método que realiza el login
     * Si es correcto pasa al menu
     * Si no es correcto muestra un toast con el error y cambia el color de los inputtext
     */
    private void hacerLogin() {



        usuario.setUsuario(textLogin.getText().toString());
        usuario.setPassword(textPassword.getText().toString());

        // realiza el login, si es correcto pasa al menu
        LoginServicio loginServicio = new LoginServicio(usuario.getUsuario(), usuario.getPassword(), new LoginServicio.Callback() {
            @Override
            public void onResult(final String result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ("loginok".equals(result)) {
                            boolean recordar = recordarContrasena.isChecked();
                            if (recordar) {
                                // guardamos usuario y conrtaseña en shared preferences

                                usuario.almacenarCredenciales(getActivity(), usuario.getUsuario(), usuario.getPassword());

                                Intent intent = new Intent(getActivity(), MenuActivity.class);
                                startActivity(intent);

                                // si no esta activado el switch borramos las credenciales del shared preferences
                            } else {
                                 usuario.borrarPreferences(getActivity());
                                Intent intent = new Intent(getActivity(), MenuActivity.class);
                                startActivity(intent);
                            }



                        } else {
                            // si no es correcto muestra un toast con el error y cambia el color de los inputtext
                            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                            textLogin.setTextColor(Color.RED);
                            textPassword.setTextColor(Color.RED);
                        }
                    }
                });
            }
        });
        // ejecutamos el hilo del login
        new Thread(loginServicio).start();
    }

}
