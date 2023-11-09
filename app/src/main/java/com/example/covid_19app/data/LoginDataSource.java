package com.example.covid_19app.data;

import com.example.covid_19app.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
         LoggedInUser usuario;


        try {

              if ("admin".equals(username) && "admin".equals(password)){

                usuario =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                "Hola Bienvenido a la app");

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