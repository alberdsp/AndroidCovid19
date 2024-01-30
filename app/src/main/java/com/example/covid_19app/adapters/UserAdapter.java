package com.example.covid_19app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.covid_19app.R;
import com.example.covid_19app.models.Users;
import java.util.List;



/**
 * ABF 2024
 * Clase que contiene el adaptador para el listview de usuarios lo formatea en 2 lineas
 */
public class UserAdapter extends ArrayAdapter<Users> {


    /**
     * Constructor
     * @param context contexto de la aplicación
     * @param users listado de usuarios
     */
    public UserAdapter(Context context, List<Users> users) {
        super(context, 0, users);
    }


    /**
     * Método que devuelve la vista de un usuario
     * @param position posición del usuario en la lista
     * @param actualView vista
     * @param parent vista padre
     * @return vista del usuario
     */


    @Override
    public View getView(int position, View actualView, ViewGroup parent) {
        // Obtener el objeto Users para esta posición
        Users user = getItem(position);

        // Comprobar si existe una vista reciclada, si no, inflarla
        if (actualView == null) {
            actualView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        // Buscar los TextViews dentro de actualView para setear nombre y detalles del usuario
        TextView tvUserNameAndTemperature = actualView.findViewById(R.id.tvUserNameAndTemperature);
        TextView tvUserDetails = actualView.findViewById(R.id.tvUserDetails);

        // Formatea la primera línea con el nombre y la temperatura
        String temperatureSymbol = user.getFormat() == 1 ? "°C" : "°F";
        String formattedLine = formatearLinea(user.getNombre() + " " + user.getApellidos(), String.valueOf(user.getTemperatura()) + temperatureSymbol, 25);

        // Rellenar los datos en los TextViews
        tvUserNameAndTemperature.setText(formattedLine);
        tvUserDetails.setText(user.getCiudad() + ", " + user.getProvincia());

        // Verificar si el tipo de temperatura es 2 y la temperatura es mayor de 100, cambiar el color del texto a rojo

        tvUserNameAndTemperature.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

        if (user.getFormat() == 1 && user.getTemperatura() > 38) {
            tvUserNameAndTemperature.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        } else if (user.getFormat() == 2 && user.getTemperatura() > 100){

            tvUserNameAndTemperature.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        }



        return actualView;
    }

    /**
     * Método que formatea una línea con el nombre y la temperatura
     * @param nombre nombre del usuario
     * @param temperatura temperatura del usuario
     * @param espacioTotal espacio total que debe ocupar la línea
     * @return línea formateada
     */

    private String formatearLinea(String nombre, String temperatura, int espacioTotal) {
        StringBuilder formattedString = new StringBuilder();

        // Calcula la cantidad de espacios necesarios entre el nombre y la temperatura
        int spacesToAdd = espacioTotal - (nombre.length() + temperatura.length());

        // Agrega el nombre al principio de la cadena
        formattedString.append(nombre);

        // Agrega los espacios necesarios entre el nombre y la temperatura
        for (int i = 0; i < spacesToAdd; i++) {
            formattedString.append(" ");
        }

        // Agrega la temperatura al final de la cadena
        formattedString.append(temperatura);

        return formattedString.toString();
    }




}
