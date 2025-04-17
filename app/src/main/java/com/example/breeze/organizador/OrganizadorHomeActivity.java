package com.example.breeze.organizador;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.breeze.R;
import com.example.breeze.organizador.fragments.ChatOrgFragment;
import com.example.breeze.organizador.fragments.CrearEventFragment;
import com.example.breeze.organizador.fragments.EstadisticasFragment;
import com.example.breeze.organizador.fragments.HomeOrgFragment;
import com.example.breeze.organizador.fragments.SettingsOrgFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrganizadorHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_organizador_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(R.layout.activity_organizador_home);
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
            } else if (item.getItemId() == R.id.settings) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsOrgFragment()).commit();
                return true;
            }

            return false;
        });
    }
}