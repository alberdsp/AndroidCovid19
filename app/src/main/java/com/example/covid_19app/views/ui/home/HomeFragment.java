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
import com.example.covid_19app.controllers.ListController;
import com.example.covid_19app.databinding.FragmentHomeBinding;
import com.example.covid_19app.models.ApiListRespuesta;
import com.example.covid_19app.models.Users;
import com.example.covid_19app.views.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase Fragment que se encarga de la vista inicio
 * ABF 16/01/2024
 */


public class HomeFragment extends Fragment implements ListController.Callback {

    private FragmentHomeBinding binding;
    private List<Users> usersList;
    private UserAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView listView = binding.listViewUsers;
        usersList = new ArrayList<>();
        adapter = new UserAdapter(requireContext(), usersList);
        listView.setAdapter(adapter);

        // Start fetching the users
        ListController listController = new ListController(this);
        Thread thread = new Thread(listController);
        thread.start();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResult(ApiListRespuesta result) {
        if (result.isSuccess()) {
            usersList.clear();
            usersList.addAll(result.getUsuarios());
            adapter.notifyDataSetChanged();
        } else {
            // Handle error
        }
    }
}