package com.example.covid_19app.views.ui.configuration;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ConfigViewModel extends AndroidViewModel {

    private static final String PREFERENCES_FILE_KEY = "com.example.covid_19app.PREFERENCE_FILE_KEY";
    private static final String SWITCH_STATE_KEY = "switch_state";

    private final MutableLiveData<Boolean> switchState = new MutableLiveData<>();
    private final SharedPreferences sharedPreferences;

    public ConfigViewModel(Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences(PREFERENCES_FILE_KEY, Application.MODE_PRIVATE);
        loadSwitchState();
    }

    private void loadSwitchState() {
        boolean state = sharedPreferences.getBoolean(SWITCH_STATE_KEY, false); // falso por defecto
        switchState.setValue(state);
    }

    public void saveSwitchState(boolean state) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SWITCH_STATE_KEY, state);
        editor.apply();
        switchState.setValue(state);
    }

    public LiveData<Boolean> getSwitchState() {

        return switchState;

    }

}
