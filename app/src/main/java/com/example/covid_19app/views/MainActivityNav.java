package com.example.covid_19app.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.covid_19app.R;
import com.example.covid_19app.databinding.ActivityMain2Binding;
import com.example.covid_19app.views.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;



/**
 * Clase que se encarga de mostrar el menú de navegación y los fragmentos
 * ABF 28/01/2024 implementa interfaz HomeFragment.OnUserClickListener
 */


public class MainActivityNav extends AppCompatActivity implements HomeFragment.OnUserClickListener {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MainActivityNav", "FAB Clicked"); // Registro de depuración

                try {
                    NavController navController = Navigation.findNavController(MainActivityNav.this, R.id.nav_host_fragment_content_main);
                    navController.navigate(R.id.homeFragment);
                    navController.navigate(R.id.conversorFragment);
                    navController.navigate(R.id.medicionFragment);
                    navController.navigate(R.id.configFragment);
                    navController.navigate(R.id.mapaFragment);
                    navController.navigate(R.id.cerrarFragment);
                } catch (Exception e) {
                    Log.e("MainActivityNav", "Navigation Error", e); // Registro de error
                }
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Configuración del menú de navegación
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.medicionFragment, R.id.conversorFragment, R.id.configFragment, R.id.mapaFragment, R.id.cerrarFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Método que se ejecuta al hacer clic en un usuario de la lista
     *
     * @param userid id del Usuario seleccionado
     */


    @Override
    public void onUserClick(String userid) {
        // Crear un bundle para pasar el ID del usuario a MapaFragment
        Bundle bundle = new Bundle();
        bundle.putString("userid", userid);

        // Obtener el NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        // Navegar a MapaFragment con el bundle
        navController.navigate(R.id.action_homeFragment_to_mapaFragment, bundle);
    }




}
