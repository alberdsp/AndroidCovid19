package com.example.covid_19app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
     * @param convertView vista
     * @param parent vista padre
     * @return vista del usuario
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener el objeto Users para esta posición
        Users user = getItem(position);

        // Comprobar si existe una vista reciclada, si no, inflarla
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        // Buscar los TextViews dentro de convertView para setear nombre, apellidos, ciudad, provincia y temperatura
        TextView tvUserName = convertView.findViewById(R.id.tvUserName);
        TextView tvUserDetails = convertView.findViewById(R.id.tvUserDetails);
        TextView tvTemperature = convertView.findViewById(R.id.tvTemperature);

        // Rellenar los datos en los TextViews
        tvUserName.setText(user.getNombre() + " " + user.getApellidos());
        tvUserDetails.setText(user.getCiudad() + ", " + user.getProvincia());
       // tvTemperature.setText(String.valueOf(user.getTemperatura()) + "°C");
        tvTemperature.setText(String.valueOf(user.getTemperatura()));

        return convertView;
    }
}
