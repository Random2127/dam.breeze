package com.example.breeze.usuario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.breeze.R;
import com.example.breeze.SettingsActivity;
import com.example.breeze.usuario.fragments.ChatClienteFragment;
import com.example.breeze.usuario.fragments.HomeClienteFragment;
import com.example.breeze.usuario.fragments.SearchFragment;
import com.example.breeze.usuario.fragments.TicketFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClienteActivity extends AppCompatActivity {

    protected Intent pasarPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Cargamos modo oscuro aqui, ya me vale!! :(
        // Tiene que haber una manera mejor de hacer esto!
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean darkMode = prefs.getBoolean("dark_mode", false);

        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        // Esto me da full-screen
        EdgeToEdge.enable(this);
        // Determina el layout general
        setContentView(R.layout.activity_cliente);
        // Move this OUTSIDE the insets block
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        // Padding para no solapar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeClienteFragment()).commit();

        // navbar inferior
        BottomNavigationView nav = findViewById(R.id.nav_user_menu);
        nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeClienteFragment()).commit();
                return true;
            } else if (item.getItemId() == R.id.search) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
                return true;
            } else if (item.getItemId() == R.id.ticket) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TicketFragment()).commit();
                return true;
            } else if (item.getItemId() == R.id.chat) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatClienteFragment()).commit();
                return true;
            }

            return false;
        });
    }

    // Menu opciones 3 puntos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            pasarPantalla = new Intent(this, SettingsActivity.class);
            startActivity(pasarPantalla);
            return true;
        } else if (id == R.id.action_logout) {
            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}