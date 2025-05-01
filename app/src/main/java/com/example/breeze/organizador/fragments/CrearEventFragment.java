package com.example.breeze.organizador.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

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


    protected Intent pasarPantalla;
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
                String nombre = caja1.getText().toString().trim();
                String descripcion = caja2.getText().toString().trim();
                String fecha = caja3.getText().toString().trim();
                String hora = caja4.getText().toString().trim();
                String ubicacion = caja5.getText().toString().trim();
                String capacidad = caja6.getText().toString().trim();
                String precioText = caja7.getText().toString().trim();
                double precio = Double.parseDouble(precioText);


                SQLiteDatabase db = gbd.getWritableDatabase();
                String sql = "INSERT INTO evento (nombre, descripcion, fecha, hora, ubicacion, capacidad, precio, organizadorID) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                db.execSQL(sql, new Object[]{nombre, descripcion, fecha, hora, ubicacion, capacidad, precio, 1});

            }
        });
        return view;
    }

}