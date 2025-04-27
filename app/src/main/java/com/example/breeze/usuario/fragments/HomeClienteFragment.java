package com.example.breeze.usuario.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.breeze.R;
import com.example.breeze.Utils;

public class HomeClienteFragment extends Fragment {

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
        return view;
    }
}