package com.example.covid_19app.data;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.SharedPreferences;

import com.example.covid_19app.MainActivity;
import com.example.covid_19app.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Clase que nos realiza el login pasando por parametro usuario y password
 */
public class LoginDataSource {



    public Result<LoggedInUser> login(String username, String password) {
         LoggedInUser usuario;



        try {

          // limitamos usuario solo al admin

              if ("admin".equals(username) && "admin".equals(password)){

                usuario =
                        new LoggedInUser(username,"Hola admin, Bienvenido a la app");

                return new Result.Success<>(usuario);
            }else {
                return new Result.Error(new IOException("Usuario o password incorrectos"));

            }

        } catch (Exception e) {
            return new Result.Error(new IOException("Error en el logging ", e));
        }
    }



    public void logout() {


        // TODO: revoke authentication
    }
}