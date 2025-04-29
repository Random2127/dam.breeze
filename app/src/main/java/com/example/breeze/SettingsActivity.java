package com.example.breeze;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.breeze.organizador.OrganizadorHomeActivity;
import com.example.breeze.usuario.ClienteHomeActivity;

public class SettingsActivity extends AppCompatActivity {

    protected TextView texto1;
    protected TextView texto2;
    protected Switch darkMode;
    protected Switch notificaciones;
    protected Spinner fuente;
    protected Button boton1;
    protected Intent pasarPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainSettings), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        darkMode = (Switch) findViewById(R.id.switch_dark_mode);
        boton1 = (Button) findViewById(R.id.boton1_user_settings);
        fuente = (Spinner) findViewById(R.id.spinner1_user_settings);
        notificaciones = (Switch) findViewById(R.id.switch_notificaciones);
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        // "cliente" por defecto, por si acaso
        String userRole = prefs.getString("user_role", "cliente");

        darkMode.setChecked(prefs.getBoolean("dark_mode", false));
        darkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("dark_mode", isChecked).apply();
            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
        });

        // Tamaño fuentes
        String [] sizes = {"Pequeño", "Mediano", "Grande"}; // opciones
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_spinner_item, sizes);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuente.setAdapter(adaptador);

        int indice = prefs.getInt("font_size_index", 1); // mediano por defecto
        fuente.setSelection(indice);

        fuente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                prefs.edit().putInt("font_size_index", position).apply();

                //Seleccion de tamaños de letra
                float sizeSp;
                switch (position) {
                    case 0:
                        sizeSp = 12f;
                        break;
                    case 1:
                        sizeSp = 18f;
                        break;
                    case 2:
                        sizeSp = 24f;
                        break;
                    default:
                        sizeSp = 16f;
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // "Notificaciones"
        // Notificaciones permitidas por defecto
        notificaciones.setChecked(prefs.getBoolean("notificaciones_enabled", true));
        notificaciones.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Cambio de estado
            prefs.edit().putBoolean("notificaciones_enabled", isChecked).apply();
            // String para el Toast
            String mensajeNotificacion = isChecked ? "Notificaciones habilitades" : "Notificaciones desabilitadas";
            Toast.makeText(this, mensajeNotificacion, Toast.LENGTH_SHORT).show();
        });

        // "Volver"
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                // "cliente" por defecto, por si acaso
                String userRole = prefs.getString("user_role", "cliente");

                // If que envia a user u org segun rol
                if ("organizador".equalsIgnoreCase(userRole)) {
                    pasarPantalla = new Intent(SettingsActivity.this, OrganizadorHomeActivity.class);
                } else {
                    pasarPantalla = new Intent(SettingsActivity.this, ClienteHomeActivity.class);
                }
                startActivity(pasarPantalla);
                finish();
            }
        });
    }
}