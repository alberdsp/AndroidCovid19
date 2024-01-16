package com.example.covid_19app.views.ui.home;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.covid_19app.controllers.ListController;
import com.example.covid_19app.models.ApiListRespuesta;
import com.example.covid_19app.models.Users;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<Users>> userItemsList;

    public HomeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        userItemsList = new MutableLiveData<>();
        mText.setValue("fragment inicio");
        loadUsers();
    }

    private void loadUsers() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new ListController(new ListController.Callback() {
            @Override
            public void onResult(ApiListRespuesta result) {
                if (result != null && result.isSuccess()) {
                    userItemsList.postValue(result.getUsuarios());
                } else {
                    mText.postValue("Error al cargar usuarios: " + (result != null ? result.getError() : "Error desconocido"));
                }
            }
        }));
        executorService.shutdown();
    }


    public LiveData<List<Users>> getUserItemsList() {
        return userItemsList;
    }

    public LiveData<String> getText() {
        return mText;
    }
}
