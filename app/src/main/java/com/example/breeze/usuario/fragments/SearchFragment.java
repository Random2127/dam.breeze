package com.example.breeze.usuario.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.breeze.Event;
import com.example.breeze.EventAdapter;
import com.example.breeze.GestorBaseDatos;
import com.example.breeze.R;
import com.example.breeze.Utils;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    protected GestorBaseDatos gbd;
    protected ListView listaEvents;


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
                ArrayList<Event> filtrados = gbd.buscaPorUbicacion(ubicacion);
                EventAdapter nuevoAdaptador = new EventAdapter(getContext(), filtrados);
                listaEvents.setAdapter(nuevoAdaptador);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });






        return view;
    }
}