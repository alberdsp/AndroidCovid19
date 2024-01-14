package com.example.covid_19app.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

/**
 *  ABF 2024
 *  Clase que contempla los usuarios
 *  de la aplicación
 * @version 1.0
 *
 */
public class User {

    // declaramos las variables que vamos a utilizar en la clase
    String usuario;
    String password;


   // declaramos las variables que vamos a almacenar si es el caso en el shared preferences
    private static final String SHARED_PREF_NAME = "RadarCovidSharedPref";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    private static final String SAVE_PASS = "NO";


    // constructor por defecto de la clase sin parámetros
    public User() {

    }

    // constructor por defecto de la clase recibe usuario y contraseña

    public User(TextView textLogin, TextView textPassword) {
        this.usuario = textLogin.getText().toString();
        this.password = textPassword.getText().toString();

    }


    public String getUsuario(){
        return usuario;
    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }





    // método que almacena las credenciales en el shared preferences

    public static void almacenarCredenciales(Context context, String username, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY, password);
        editor.putString(SAVE_PASS, "SI");

        editor.apply();
    }

   // método que recupera las credenciales del shared preferences
    public String getUsuarioShared(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        this.usuario = sharedPreferences.getString(USERNAME_KEY, "");
        return usuario;
    }


    // método que recupera las credenciales del shared preferences
    public String getPasswordShared(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        this.password = sharedPreferences.getString(PASSWORD_KEY, "");
        return password;
    }
    // método que recupera las credenciales del shared preferences
    public String getGuardar(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String almacenar = sharedPreferences.getString(SAVE_PASS, "NO");
        return almacenar;
    }


    // método que borra las credenciales del shared preferences
    public static void borrarPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("RadarCovidSharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear(); // Borramos todos los datos

        editor.apply();
    }







}