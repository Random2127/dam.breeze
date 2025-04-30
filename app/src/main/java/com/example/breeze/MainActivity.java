package com.example.breeze;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.breeze.organizador.OrganizadorActivity;
import com.example.breeze.usuario.ClienteActivity;

public class MainActivity extends AppCompatActivity {



    protected EditText caja1;
    protected EditText caja2;
    protected Button boton1;
    protected TextView texto1;

    protected String contenidoCaja1;
    protected String contenidoCaja2;

    protected GestorBaseDatos gbd;
    protected String role;
    protected Intent pasarPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        caja1 = (EditText) findViewById(R.id.caja1_main);
        caja2 = (EditText) findViewById(R.id.caja2_main);
        boton1 = (Button) findViewById(R.id.boton1_main);
        texto1 = (TextView) findViewById(R.id.texto1_main);


        gbd = new GestorBaseDatos(this);

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contenidoCaja1 = caja1.getText().toString().trim();
                contenidoCaja2 = caja2.getText().toString().trim();
                role = gbd.comprobarCredenciales(contenidoCaja1,contenidoCaja2, MainActivity.this);

                if (role == null || role.isEmpty()) {
                    Toast.makeText(MainActivity.this,"Credenciales no válidas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Login correcto como " + role, Toast.LENGTH_SHORT).show();

                    // Guardamos el rol para controlar pantallas
                    SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("user_role", role);
                    editor.apply();

                    if (role != null && role.equalsIgnoreCase("cliente")) {
                        pasarPantalla = new Intent(MainActivity.this, ClienteActivity.class);
                        startActivity(pasarPantalla);
                        finish();
                    } else if (role != null && role.equalsIgnoreCase("organizador")) {
                        pasarPantalla = new Intent(MainActivity.this, OrganizadorActivity.class);
                        startActivity(pasarPantalla);
                        finish();
                    }
                }
            }
        });

        texto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(MainActivity.this, Registro.class);
                finish();
                startActivity(pasarPantalla);
            }
        });


    }
}