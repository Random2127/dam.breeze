package com.example.breeze.usuario.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.breeze.GestorBaseDatos;
import com.example.breeze.R;
import com.example.breeze.Utils;

import java.util.ArrayList;

public class HomeClienteFragment extends Fragment {

    protected ArrayList<String> evento = new ArrayList<String>();
    protected ArrayAdapter<String> adaptador;
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

        listaEvents = (ListView) view.findViewById(R.id.list_cli_home);
        // Method in Gestor to retreive all data in eventos
        gbd = new GestorBaseDatos(requireContext());

        //recogemos un array con daros del evento
        evento = gbd.leerListaEventos();
        adaptador = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, evento);
        listaEvents.setAdapter(adaptador);

        return view;
    }
}