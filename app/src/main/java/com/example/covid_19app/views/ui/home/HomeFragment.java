package com.example.covid_19app.views.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.covid_19app.adapters.UserAdapter;
import com.example.covid_19app.controllers.ApiGetListController;
import com.example.covid_19app.databinding.FragmentHomeBinding;
import com.example.covid_19app.models.ApiRespuesta;
import com.example.covid_19app.models.Users;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase Fragment que se encarga de la vista inicio
 * ABF 16/01/2024
 */


public class HomeFragment extends Fragment implements ApiGetListController.Callback {

    private FragmentHomeBinding binding;
    private List<Users> usersList;
    private UserAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        // hacemos el binding de la vista
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // usamos el adapter para mostrar los usuarios en la lista ordenados
        final ListView listView = binding.listViewUsers;
        usersList = new ArrayList<>();
        adapter = new UserAdapter(requireContext(), usersList);
        listView.setAdapter(adapter);

        // cargamos la lista de usuarios e iniciamos el hilo
        ApiGetListController apiGetListController = new ApiGetListController(this);
        Thread thread = new Thread(apiGetListController);
        thread.start();

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onResult(final ApiRespuesta result) {


        // recuperamos el hilos principal para mostrar los resultados
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result.isSuccess()) {
                    usersList.clear();
                    usersList.addAll(result.getUsuarios());
                    adapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(getView(), "Error: no se completó la petición al servidor", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }


    }
