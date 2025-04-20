package com.example.breeze.usuario;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.breeze.R;
import com.example.breeze.organizador.fragments.ChatOrgFragment;
import com.example.breeze.organizador.fragments.CrearEventFragment;
import com.example.breeze.organizador.fragments.EstadisticasFragment;
import com.example.breeze.organizador.fragments.SettingsOrgFragment;
import com.example.breeze.usuario.fragments.ChatUserFragment;
import com.example.breeze.usuario.fragments.HomeUserFragment;
import com.example.breeze.usuario.fragments.SearchFragment;
import com.example.breeze.usuario.fragments.SettingsUserFragment;
import com.example.breeze.usuario.fragments.TicketFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UsuarioHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Log.d("UsuarioHomeActivity", "onCreate started");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usuario_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(R.layout.activity_usuario_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeUserFragment()).commit();

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
            } else if (item.getItemId() == R.id.settings) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsUserFragment()).commit();
                return true;
            }

            return false;
        });

    } catch (Exception e) {
        Log.e("UsuarioHomeActivity", "Exception in onCreate: " + e.getMessage(), e);

    }
}
}