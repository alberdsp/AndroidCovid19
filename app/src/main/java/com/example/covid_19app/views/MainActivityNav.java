package com.example.covid_19app.views;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.example.covid_19app.R;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.covid_19app.databinding.ActivityMain2Binding;

public class MainActivityNav extends AppCompatActivity {

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
                Log.d("MainActivity2", "FAB Clicked"); // Debug log
                try {
                    NavController navController = Navigation.findNavController(MainActivityNav.this, R.id.nav_host_fragment_content_main);
                    navController.navigate(R.id.homeFragment);
                    navController.navigate(R.id.conversorFragment);
                    navController.navigate(R.id.medicionFragment);
                    navController.navigate(R.id.configFragment);
                    navController.navigate(R.id.mapaFragment);
                    navController.navigate(R.id.cerrarFragment);

                } catch (Exception e) {
                    Log.e("MainActivity2", "Navigation Error", e); // Error log
                }
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Listener para el menú de navegación



        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.medicionFragment, R.id.conversorFragment, R.id.configFragment, R.id.mapaFragment,R.id.cerrarFragment)
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
}
