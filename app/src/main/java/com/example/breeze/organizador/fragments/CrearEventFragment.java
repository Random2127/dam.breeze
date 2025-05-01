package com.example.breeze.organizador.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.breeze.Event;
import com.example.breeze.GestorBaseDatos;
import com.example.breeze.R;
import com.example.breeze.Utils;

public class CrearEventFragment extends Fragment {

    protected EditText caja1;
    protected EditText caja2;
    protected EditText caja3;
    protected EditText caja4;
    protected EditText caja5;
    protected EditText caja6;
    protected EditText caja7;
    protected Button boton1;

    protected GestorBaseDatos gbd;


    public CrearEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_event, container, false);

        boton1 = (Button) view.findViewById(R.id.boton1_crear);
        caja1 = view.findViewById(R.id.caja1_crear);
        caja2 = view.findViewById(R.id.caja2_crear);
        caja3 = view.findViewById(R.id.caja3_crear);
        caja4 = view.findViewById(R.id.caja4_crear);
        caja5 = view.findViewById(R.id.caja5_crear);
        caja6 = view.findViewById(R.id.caja6_crear);
        caja7 = view.findViewById(R.id.caja7_crear);

        Utils.cambioSizeTextViews(view, getContext());

        // "Aceptar" y guardar datos en DB
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Event evento = new Event(
                        caja1.getText().toString().trim(), // nombre
                        caja2.getText().toString().trim(), // descripcion
                        caja3.getText().toString().trim(), // fecha
                        caja4.getText().toString().trim(), // hora
                        caja5.getText().toString().trim(), // ubicacion
                        Integer.parseInt(caja6.getText().toString().trim()), // capacidad
                        Double.parseDouble(caja7.getText().toString().trim()), // precio
                        "" // urlImagen aún no se implementa, puedes dejarlo vacío o usar null
                );


                SQLiteDatabase db = gbd.getWritableDatabase();
                String sql = "INSERT INTO evento (nombre, descripcion, fecha, hora, ubicacion, capacidad, precio, organizadorID) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                db.execSQL(sql, new Object[]{
                        evento.getNombre(),
                        evento.getDescripcion(),
                        evento.getFecha(),
                        evento.getHora(),
                        evento.getUbicacion(),
                        evento.getCapacidad(),
                        evento.getPrecio(),
                        1 // organizadorID fijo por ahora
                });
                Toast.makeText(requireContext(), "Evento creado correctamente", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}