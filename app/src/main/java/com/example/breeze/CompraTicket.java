package com.example.breeze;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.breeze.usuario.ClienteActivity;

public class CompraTicket extends AppCompatActivity {

    protected Button botonVolver;
    protected Intent pasarPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compra_ticket);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        botonVolver= (Button) findViewById(R.id.btn_volver);

        // Get data from intent
        String nombre = getIntent().getStringExtra("nombre");
        String descripcion = getIntent().getStringExtra("descripcion");
        String fecha = getIntent().getStringExtra("fecha");
        String hora = getIntent().getStringExtra("hora");
        String ubicacion = getIntent().getStringExtra("ubicacion");
        double precio = getIntent().getDoubleExtra("precio", 0.0);

        // Bind views
        TextView textNombre = findViewById(R.id.text_nombre);
        TextView textDescripcion = findViewById(R.id.text_descripcion);
        TextView textFechaHora = findViewById(R.id.text_fecha_hora);
        TextView textUbicacion = findViewById(R.id.text_ubicacion);
        TextView textPrecio = findViewById(R.id.text_precio);
        Button btnComprar = findViewById(R.id.btn_comprar);

        // Set data
        textNombre.setText(nombre);
        textDescripcion.setText(descripcion);
        textFechaHora.setText("Fecha: " + fecha + "  Hora: " + hora);
        textUbicacion.setText("Ubicación: " + ubicacion);
        textPrecio.setText("Precio: €" + precio);

        // Handle button click
        btnComprar.setOnClickListener(v -> {
            Toast.makeText(this, "Ticket Comprado!", Toast.LENGTH_LONG).show();
            finish(); // go back to previous screen
        });

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(CompraTicket.this, ClienteActivity.class);
                startActivity(pasarPantalla);
            }
        });
    }
}
