package com.example.covid_19app.views.ui.home;


import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.covid_19app.controllers.ApiGetListController;
import com.example.covid_19app.models.ApiRespuesta;
import com.example.covid_19app.models.Users;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * Clase que se encarga de la vista modelo de la pantalla de inicio
 * ABF 16/01/2024
 */

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<Users>> userItemsList;

    /**
     * Constructor de la clase vista modelo
     * @param application
     */
    public HomeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        userItemsList = new MutableLiveData<>();
        mText.setValue("fragment inicio");
       // loadUsers();
    }


    /**
     * Método que carga los usuarios haciendo uso de un hilo para no bloquear la interfaz
     * de usuario llama a ListController y recibe un callback con la lista de usuarios
     */
    private void loadUsers() {

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new ApiGetListController(new ApiGetListController.Callback() {
                @Override
                public void onResult(ApiRespuesta result) {
                    if (result.isSuccess()) {
                        userItemsList.setValue(result.getUsuarios());
                    } else {
                        // a implementar error
                    }
                }
            }));
            executorService.shutdown();

    }


   // devuelve la lista de usuarios
    public LiveData<List<Users>> getUserItemsList() {
        return userItemsList;
    }

    // devuelve el texto cabecera
    public LiveData<String> getText() {
        return mText;
    }
}
