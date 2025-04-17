package com.example.breeze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.breeze.organizador.OrganizadorHomeActivity;
import com.example.breeze.usuario.UsuarioHomeActivity;

public class MainActivity extends AppCompatActivity {



    protected EditText caja1;
    protected EditText caja2;
    protected Button boton1;

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

        gbd = new GestorBaseDatos(this);

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contenidoCaja1 = caja1.getText().toString();
                contenidoCaja2 = caja2.getText().toString();
                role = gbd.comprobarCredenciales(contenidoCaja1,contenidoCaja2);

                if (role == null) {
                    Toast.makeText(MainActivity.this,"Credenciales no válidas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Login correcto como " + role, Toast.LENGTH_SHORT).show();

                    if (role.equalsIgnoreCase("usuario")) {
                        pasarPantalla = new Intent(MainActivity.this, UsuarioHomeActivity.class);
                        finish();
                        startActivity(pasarPantalla);
                    } else if (role.equalsIgnoreCase("organizador")) {
                        pasarPantalla = new Intent(MainActivity.this, OrganizadorHomeActivity.class);
                        finish();
                        startActivity(pasarPantalla);
                    }
                }
            }
        });
    }
}