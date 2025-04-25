package com.example.breeze.organizador;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.breeze.R;
import com.example.breeze.SettingsActivity;
import com.example.breeze.organizador.fragments.ChatOrgFragment;
import com.example.breeze.organizador.fragments.CrearEventFragment;
import com.example.breeze.organizador.fragments.EstadisticasFragment;
import com.example.breeze.organizador.fragments.HomeOrgFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrganizadorHomeActivity extends AppCompatActivity {
    protected Intent pasarPantalla;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_organizador_home);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeOrgFragment()).commit();

        BottomNavigationView nav = findViewById(R.id.nav_org_menu);
        nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeOrgFragment()).commit();
                return true;
            } else if (item.getItemId() == R.id.addEvent) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CrearEventFragment()).commit();
                return true;
            } else if (item.getItemId() == R.id.stats) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EstadisticasFragment()).commit();
                return true;
            } else if (item.getItemId() == R.id.chat) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatOrgFragment()).commit();
                return true;
            }

            return false;
        });
    }
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