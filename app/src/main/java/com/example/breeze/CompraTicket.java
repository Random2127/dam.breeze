package com.example.breeze;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
    protected GestorBaseDatos gbd;

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

        // REcogemos datos del intent
        String nombre = getIntent().getStringExtra("nombre");
        String descripcion = getIntent().getStringExtra("descripcion");
        String fecha = getIntent().getStringExtra("fecha");
        String hora = getIntent().getStringExtra("hora");
        String ubicacion = getIntent().getStringExtra("ubicacion");
        double precio = getIntent().getDoubleExtra("precio", 0.0);

        // declaramos
        TextView textNombre = findViewById(R.id.text_nombre);
        TextView textDescripcion = findViewById(R.id.text_descripcion);
        TextView textFechaHora = findViewById(R.id.text_fecha_hora);
        TextView textUbicacion = findViewById(R.id.text_ubicacion);
        TextView textPrecio = findViewById(R.id.text_precio);
        Button btnComprar = findViewById(R.id.btn_comprar);

        // datos a exponer
        textNombre.setText(nombre);
        textDescripcion.setText(descripcion);
        textFechaHora.setText("Fecha: " + fecha + "  Hora: " + hora);
        textUbicacion.setText("Ubicación: " + ubicacion);
        textPrecio.setText("Precio: €" + precio);


        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int clientID = prefs.getInt("user_id", -1);

                int eventoID = getIntent().getIntExtra("eventoID", -1); // Make sure you pass this!
                String fechaCompra = Utils.getCurrentDate(); // You'll need to create this helper

                if (eventoID == -1 || clientID == -1) {
                    Toast.makeText(CompraTicket.this, "Error: datos incorrectos", Toast.LENGTH_SHORT).show();
                    return;
                }

                gbd = new GestorBaseDatos(CompraTicket.this);
                SQLiteDatabase db = gbd.getWritableDatabase();
                // Mandamos el ticket a la DB
                String sql = "INSERT INTO ticket (eventoID, clienteID, fechaCompra) VALUES (?, ?, ?)";
                db.execSQL(sql, new Object[]{eventoID, clientID, fechaCompra});

                Toast.makeText(CompraTicket.this, "Ticket Comprado!", Toast.LENGTH_SHORT).show();
                finish(); // volver a la pantalla anterior
            }
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
