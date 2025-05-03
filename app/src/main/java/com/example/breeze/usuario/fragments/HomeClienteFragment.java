package com.example.breeze.usuario.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.breeze.Event;
import com.example.breeze.EventAdapter;
import com.example.breeze.GestorBaseDatos;
import com.example.breeze.R;
import com.example.breeze.Utils;

import java.util.ArrayList;

public class HomeClienteFragment extends Fragment {

    protected GestorBaseDatos gbd;

    protected ListView listaEvents;

    public HomeClienteFragment() {
        // Required empty public constructor
        super(R.layout.fragment_clientehome);
    }


    // Todos los fragmentos añaden metodo de utils para cambiar tamaño d la fuente
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clientehome, container, false);
        Utils.cambioSizeTextViews(view, getContext());

        listaEvents = view.findViewById(R.id.list_cli_home);
        gbd = new GestorBaseDatos(requireContext());

        ArrayList<Event> eventos = new ArrayList<>();

        SQLiteDatabase db = gbd.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nombre, descripcion, fecha, hora, ubicacion, capacidad, precio, urlImagen FROM evento", null);

        if (cursor.moveToFirst()) {
            do {
                Event evento = new Event(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getDouble(6),
                        cursor.getString(7)
                );
                eventos.add(evento);
            } while (cursor.moveToNext());
        }
        cursor.close();

        EventAdapter adaptador = new EventAdapter(getContext(), eventos);
        listaEvents.setAdapter(adaptador);

        return view;
    }
}