package com.example.breeze;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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
                if(s.length()>0){
                    caja1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                contenido1 = caja1.getText().toString().trim();
                                if (!contenido1.isEmpty()) {
                                    boolean existe = gbd.existeUser(contenido1);
                                    if (existe) {
                                        caja1.setError("El nombre ya existe");
                                        caja1.setBackgroundColor(getResources().getColor(R.color.colorNoMatch));
                                    } else {
                                        caja1.setError(null);
                                        // enter in database

                                    }
                                }
                            }
                        }
                    });
                    caja1.setBackgroundColor(getResources().getColor(R.color.colorCompletado));
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
                if(mailEsValido(contenido2)){
                    caja2.setBackgroundColor(getResources().getColor(R.color.colorCompletado));
                } else {
                    caja2.setError("Introduce un formato válido de email");
                    caja2.setBackgroundColor(getResources().getColor(R.color.colorVacio));
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
    }

    private void compararPass() {
        contenido3 = caja3.getText().toString().trim();
        contenido4 = caja4.getText().toString().trim();

        if(contenido3.equals(contenido4)&& contenido3 != null){
            caja3.setBackgroundColor(getResources().getColor(R.color.colorMatch));
            caja4.setBackgroundColor(getResources().getColor(R.color.colorMatch));
        } else {
            caja3.setBackgroundColor(getResources().getColor(R.color.colorNoMatch));
            caja4.setBackgroundColor(getResources().getColor(R.color.colorNoMatch));
        }
    }

    private boolean mailEsValido(String email) {
        // Comprobador standard de email incluido en android
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}