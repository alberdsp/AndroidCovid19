package com.example.covid_19app.views.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
 * Clase que se encarga de controlar el fragmento de la lista de usuarios de inicio
 * ABF 30/01/2024
 * implementa interfaz ApiGetListController.Callback para realizar peticiones al webservice
 */
public class HomeFragment extends Fragment implements ApiGetListController.Callback {

    private FragmentHomeBinding binding;
    private List<Users> usersList;
    private UserAdapter adapter;

    private OnUserClickListener userClickListener;




    //  metodo onAttach  que ancla el fragmento a la actividad
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUserClickListener) {
            userClickListener = (OnUserClickListener) context;
        } else {
            throw new ClassCastException(context.toString() + " error");
        }
    }


    // creamos la vista del fragmento
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

        ApiGetListController apiGetListController = new ApiGetListController(this);
        Thread thread = new Thread(apiGetListController);
        thread.start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Users selectedUser = usersList.get(position);
                if (userClickListener != null) {
                    userClickListener.onUserClick(String.valueOf(selectedUser.getId()));
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    // metodo que se ejecuta cuando se completa la peticion al webservice
    @Override
    public void onResult(final ApiRespuesta result) {
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


    // interfaz que se implementa en la actividad para que al hacer clic en un usuario de la lista se muestre el mapa
    
    public interface OnUserClickListener {
        void onUserClick(String userId);
    }
}
