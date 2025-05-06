package com.example.breeze.organizador.fragments;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breeze.Event;
import com.example.breeze.EventAdapter;
import com.example.breeze.GestorBaseDatos;
import com.example.breeze.R;
import com.example.breeze.Utils;

import java.util.ArrayList;

public class HomeOrgFragment extends Fragment {

    protected GestorBaseDatos gbd;

    protected ListView listaEvents;


    public HomeOrgFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orghome, container, false);
        Utils.cambioSizeTextViews(view, getContext());

        listaEvents = view.findViewById(R.id.lista_org_home);
        gbd = new GestorBaseDatos(requireContext());

        ArrayList<Event> eventos = new ArrayList<>();

        SQLiteDatabase db = gbd.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT eventoID, nombre, descripcion, fecha, hora, ubicacion, capacidad, precio, urlImagen FROM evento", null);

        if (cursor.moveToFirst()) {
            do {
                Event evento = new Event(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6),
                        cursor.getDouble(7),
                        cursor.getString(8)
                );
                eventos.add(evento);
            } while (cursor.moveToNext());
        }

        EventAdapter adaptador = new EventAdapter(getContext(), eventos);
        listaEvents.setAdapter(adaptador);
        return view;
    }


}