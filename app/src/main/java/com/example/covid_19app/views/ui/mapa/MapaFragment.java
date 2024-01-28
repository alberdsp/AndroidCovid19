package com.example.covid_19app.views.ui.mapa;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid_19app.R;
import com.example.covid_19app.models.Coordenadas;
import com.example.covid_19app.models.Users;
import com.example.covid_19app.views.ui.home.HomeViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


/**
 * clase que muestra en el mapa las losclaizaciones de los pacientes, para ello
 * tenemos un observador del Homeviewmodel  que carga al inicio de la app
 */

public class MapaFragment extends Fragment {


    private SupportMapFragment mapFragment;

    private HomeViewModel homeViewModel;


    /**
     * Constructor stático
     * @return
     */
    public static MapaFragment newInstance() {
        return new MapaFragment();
    }





/*

   @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);


        // Observar los cambios en la lista de usuarios
        homeViewModel.getUserItemsList().observe(getViewLifecycleOwner(), new Observer<List<Users>>() {


            @Override
            public void onChanged(@Nullable List<Users> users) {
                if (users != null && !users.isEmpty()) {
                    List<Coordenadas> coordenadasList = Coordenadas.obtenerCoordenadasDeUsuarios(users);

                    // realizamos la tarea asyncrona para no enlentecer la carga del mapa
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            for (Coordenadas coordenadas : coordenadasList) {
                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(coordenadas.getLatitud(), coordenadas.getLongitud()))
                                        .title(""));
                            }
                        }
                    });
                }
            }
        });
        // cargamos el fragment del mapa

        View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);

        // Inicializa el SupportMapFragment para la transición
        mapFragment = SupportMapFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.mapaContainer, mapFragment).commit();

        return rootView;
    }

*/


    @Override

    public void onResume() {
        super.onResume();
        if (mapFragment != null) {
            mapFragment.onResume();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragment.onLowMemory();
    }



}