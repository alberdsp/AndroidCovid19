package com.example.covid_19app.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.covid_19app.R;
import com.example.covid_19app.views.ui.mapa.MapaFragment;

public class MainActivityNavMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_nav_map);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MapaFragment.newInstance())
                    .commitNow();
        }
    }
}