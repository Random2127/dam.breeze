package com.example.breeze.organizador.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.breeze.R;
import com.example.breeze.Utils;

public class CrearEventFragment extends Fragment {


    protected Button boton1;


    protected Intent pasarPantalla;


    public CrearEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_event, container, false);

        boton1 = (Button) view.findViewById(R.id.boton1_crear);

        Utils.cambioSizeTextViews(view, getContext());

        // "Aceptar"
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return view;
    }

}