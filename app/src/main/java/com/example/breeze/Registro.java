package com.example.breeze;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Registro extends AppCompatActivity {

    protected EditText caja1;
    protected String contenido1;
    protected EditText caja2;
    protected String contenido2;
    protected EditText caja3;
    protected String contenido3;
    protected EditText caja4;
    protected String contenido4;

    protected Button boton1;
    protected Button boton2;
    protected Switch userType;
    protected Intent pasarPantalla;
    protected GestorBaseDatos gbd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        caja1 = (EditText) findViewById(R.id.caja1_registro);
        caja2 = (EditText) findViewById(R.id.caja2_registro);
        caja3 = (EditText) findViewById(R.id.caja3_registro);
        caja4 = (EditText) findViewById(R.id.caja4_registro);
        userType = (Switch) findViewById(R.id.switch1_registro);
        boton1 = (Button) findViewById(R.id.boton1_registro);
        boton2 = (Button) findViewById(R.id.boton2_registro);

        gbd = new GestorBaseDatos(this);

        caja1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    caja1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                contenido1 = caja1.getText().toString().trim();
                                if (!contenido1.isEmpty()) {
                                    boolean existe = gbd.existeUser(contenido1);
                                    if (existe) {
                                        caja1.setError("El nombre ya existe");
                                        caja1.setBackgroundColor(ContextCompat.getColor(Registro.this, R.color.colorNoMatch));
                                    } else {
                                        caja1.setError(null);
                                        // enter in database

                                    }
                                }
                            }
                        }
                    });
                    caja1.setBackgroundColor(ContextCompat.getColor(Registro.this, R.color.colorCompletado));
                }
            }
        });


        caja2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                contenido2 = s.toString().trim();
                if (mailEsValido(contenido2)) {
                    caja2.setBackgroundColor(ContextCompat.getColor(Registro.this, R.color.colorCompletado));
                } else {
                    caja2.setError("Introduce un formato válido de email");
                    caja2.setBackgroundColor(ContextCompat.getColor(Registro.this, R.color.colorVacio));
                }
            }
        });

        caja3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                compararPass();
            }
        });

        caja4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                compararPass();
            }
        });

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(Registro.this, MainActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Valores a registrar
                String nombre = caja1.getText().toString().trim();
                String email = caja2.getText().toString().trim();
                String password = caja3.getText().toString().trim();
                String passwordConfirm = caja4.getText().toString().trim();
                String role = userType.isChecked() ? "organizador" : "cliente";

                // Comprobamos los campos antes de enviar a BD
                if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                    Toast.makeText(Registro.this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(passwordConfirm)) {
                    Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!mailEsValido(email)) {
                    Toast.makeText(Registro.this, "Email no válido", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (gbd.existeUser(nombre)) {
                    Toast.makeText(Registro.this, "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase db = gbd.getWritableDatabase();
                String sql = "INSERT INTO user (nombre, email, password, role) " + "VALUES (?, ?, ?, ?)";

                // COmprobación de que ha entrado y toast para confirmar registro
                try {
                    db.execSQL(sql, new Object[]{nombre, email, password, role});
                    Toast.makeText(getApplicationContext(), "Registro completado", Toast.LENGTH_SHORT).show();
                    //Volvemos a pantalla principal si funciona
                    pasarPantalla = new Intent(Registro.this, MainActivity.class);
                    startActivity(pasarPantalla);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error al registrar: " + e.getMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    db.close();
                }
            }
        });
    }

    private void compararPass() {
        // Comparo que paswords coincidan
        contenido3 = caja3.getText().toString().trim();
        contenido4 = caja4.getText().toString().trim();

        if (contenido3.equals(contenido4) && contenido3 != null) {
            caja3.setBackgroundColor(ContextCompat.getColor(Registro.this, R.color.colorMatch));
            caja4.setBackgroundColor(ContextCompat.getColor(Registro.this, R.color.colorMatch));
        } else {
            caja3.setBackgroundColor(ContextCompat.getColor(Registro.this, R.color.colorNoMatch));
            caja4.setBackgroundColor(ContextCompat.getColor(Registro.this, R.color.colorNoMatch));
        }
    }

    private boolean mailEsValido(String email) {
        // Comprobador standard de email incluido en android
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}