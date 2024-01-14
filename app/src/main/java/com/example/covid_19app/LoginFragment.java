package com.example.covid_19app;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


/**
 * Fragment que contiene el login
 */
public class LoginFragment extends Fragment {


    // declaramos los objetos que vamos a utilizar en la vista
    private TextView textLogin;
    private TextView textPassword;
    private Button buttonLogin;

    // constructor por defecto, creamos la vista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_login, container, false);

        textLogin = view.findViewById(R.id.username);
        textPassword = view.findViewById(R.id.password);
        buttonLogin = view.findViewById(R.id.login);

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
        String username = textLogin.getText().toString();
        String password = textPassword.getText().toString();

        // realiza el login, si es correcto pasa al menu
        LoginServicio loginServicio = new LoginServicio(username, password, new LoginServicio.Callback() {
            @Override
            public void onResult(final String result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ("loginok".equals(result)) {
                            Intent intent = new Intent(getActivity(), MenuActivity.class);
                            startActivity(intent);
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
