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
import com.example.covid_19app.controllers.ApiGetListController;
import com.example.covid_19app.controllers.ObtenerCoordenadas;
import com.example.covid_19app.models.ApiRespuesta;
import com.example.covid_19app.models.Coordenadas;
import com.example.covid_19app.models.Users;
import com.example.covid_19app.views.ui.home.HomeViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.List;


/**
 * Clase que se encarga de mostrar el mapa con los marcadores de los usuarios
 * ABF 28/01/2024
 */

public class MapaFragment extends Fragment {

    private SupportMapFragment mapFragment;
    private HomeViewModel homeViewModel;



    // constructor por defecto
    public static MapaFragment newInstance() {
        return new MapaFragment();
    }



    // Método que crea una instancia del fragmento recibiendo el id del usuario seleccionado

    public static MapaFragment newInstance(String userid) {
        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();
        args.putString("selectedUserId", userid);
        fragment.setArguments(args);
        return fragment;
    }


    // Método que se ejecuta al crear la vista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);

        Bundle args = getArguments();


            mapFragment = SupportMapFragment.newInstance();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.mapaContainer, mapFragment).commit();


            // cramos un observador para que cuando cambie la lista de usuarios se actualice el mapa
            homeViewModel.getUserItemsList().observe(getViewLifecycleOwner(), new Observer<List<Users>>() {
                @Override
                public void onChanged(@Nullable List<Users> users) {
                    if (users != null && !users.isEmpty()) {
                        obtenerCoordenadasYMostrarMarcadores(users);
                    }
                }
            });



            // si pasamos un id de usuario como argumento, cargamos la lista de usuarios con ese id

        if (args != null) {
            String selectedUserId = args.getString("userid");
            if (selectedUserId != null) {
                cargarListaDeUsuarios(selectedUserId);
            }
        }else {
            cargarListaDeUsuarios();

        }

        return rootView;
    }
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



    // Método que obtiene las direcciones de una lista de usuarios y las traduce en coordenadas
    // para mostrar los marcadores en el mapa
    private void obtenerCoordenadasYMostrarMarcadores(List<Users> users) {
        ObtenerCoordenadas coordenadasHelper = new ObtenerCoordenadas();
        coordenadasHelper.obtenerCoordenadasAsync(users, new ObtenerCoordenadas.CoordenadasCallback() {
            @Override
            public void onCoordenadasObtenidas(List<Coordenadas> coordenadasList) {
                mostrarMarcadoresEnMapa(coordenadasList);
            }
        }, requireContext());
    }



    // Método que muestra los marcadores en el mapa de una lista de coordenadas
    private void mostrarMarcadoresEnMapa(List<Coordenadas> coordenadasList) {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.clear(); // Borramos los marcadores existentes antes de agregar nuevos

                // Posicionamos la cámara en Madrid
                LatLng madridLatLng = new LatLng(40.416775, -3.703790);

                // Configurar la cámara para hacer zoom en Madrid
                CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(madridLatLng, 5); // con un nivel de zoom

                // Hacemos zoom suave
                   googleMap.animateCamera(cu);

                   // Agregamos los marcadores recorriendo la lista de coordenadas
                for (Coordenadas coordenada : coordenadasList) {
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(coordenada.getLatitud(), coordenada.getLongitud()))
                            .title(""));
                }
            }
        });
    }


    // Método que carga  toda la lista usuarios haciendo uso de un hilo para no bloquear la interfaz
    private void cargarListaDeUsuarios() {
        ApiGetListController apiGetListController = new ApiGetListController(new ApiGetListController.Callback() {
            @Override
            public void onResult(ApiRespuesta result) {
                if (result.isSuccess() && result.getUsuarios() != null) {
                    obtenerCoordenadasYMostrarMarcadores(result.getUsuarios());
                }
            }
        });
        Thread thread = new Thread(apiGetListController);
        thread.start();
    }



    // Método que carga la lista de usuarios filtrando por el id de usuario pasado,  haciendo uso de un hilo para no bloquear la interfaz
    private void cargarListaDeUsuarios(String userid) {
        ApiGetListController apiGetListController = new ApiGetListController(new ApiGetListController.Callback() {
            @Override
            public void onResult(ApiRespuesta result) {
                if (result.isSuccess() && result.getUsuarios() != null) {
                    obtenerCoordenadasYMostrarMarcadores(result.getUsuarios());
                }
            }
        }, userid);
        Thread thread = new Thread(apiGetListController);
        thread.start();
    }

}
