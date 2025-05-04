package com.example.breeze.usuario.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.breeze.CompraTicket;
import com.example.breeze.Event;
import com.example.breeze.EventAdapter;
import com.example.breeze.GestorBaseDatos;
import com.example.breeze.R;
import com.example.breeze.Utils;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    protected GestorBaseDatos gbd;
    protected ListView listaEvents;
    protected Intent pasarPantalla;
    protected ArrayList<Event> eventosActuales = new ArrayList<>();



    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Utils.cambioSizeTextViews(view, getContext());

        listaEvents = view.findViewById(R.id.lista_search);
        gbd = new GestorBaseDatos(requireContext());

        EditText searchInput = view.findViewById(R.id.buscar_ubicacion);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ubicacion = s.toString().trim();
                eventosActuales = gbd.buscaPorUbicacion(ubicacion);
                EventAdapter adaptador = new EventAdapter(getContext(), eventosActuales);
                listaEvents.setAdapter(adaptador);

                // Levamos valores a la pag de compra
                listaEvents.setOnItemClickListener((parent, view1, position, id) -> {
                    Event eventoClicado = eventosActuales.get(position);

                    Intent pasarPantalla = new Intent(getActivity(), CompraTicket.class);
                    pasarPantalla.putExtra("nombre", eventoClicado.getNombre());
                    pasarPantalla.putExtra("descripcion", eventoClicado.getDescripcion());
                    pasarPantalla.putExtra("fecha", eventoClicado.getFecha());
                    pasarPantalla.putExtra("hora", eventoClicado.getHora());
                    pasarPantalla.putExtra("ubicacion", eventoClicado.getUbicacion());
                    pasarPantalla.putExtra("capacidad", eventoClicado.getCapacidad());
                    pasarPantalla.putExtra("precio", eventoClicado.getPrecio());
                    pasarPantalla.putExtra("urlImagen", eventoClicado.getUrlImagen());

                    startActivity(pasarPantalla);
                });

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        return view;
    }
}