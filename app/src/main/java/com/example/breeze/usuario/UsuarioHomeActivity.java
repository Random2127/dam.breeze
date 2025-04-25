package com.example.breeze.usuario;

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
import com.example.breeze.usuario.fragments.ChatUserFragment;
import com.example.breeze.usuario.fragments.HomeUserFragment;
import com.example.breeze.usuario.fragments.SearchFragment;
import com.example.breeze.usuario.fragments.TicketFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UsuarioHomeActivity extends AppCompatActivity {

    protected Intent pasarPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Esto me da full-screen
        EdgeToEdge.enable(this);
        // Determina el layout general
        setContentView(R.layout.activity_usuario_home);
        // Move this OUTSIDE the insets block
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        // Padding para no solapar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeUserFragment()).commit();

        // navbar inferior
        BottomNavigationView nav = findViewById(R.id.nav_user_menu);
        nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeUserFragment()).commit();
                return true;
            } else if (item.getItemId() == R.id.search) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
                return true;
            } else if (item.getItemId() == R.id.ticket) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TicketFragment()).commit();
                return true;
            } else if (item.getItemId() == R.id.chat) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatUserFragment()).commit();
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