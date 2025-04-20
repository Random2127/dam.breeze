package com.example.breeze.usuario.fragments;

import android.content.SharedPreferences;
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
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.breeze.R;

public class SettingsUserFragment extends Fragment {

    protected TextView texto1;
    protected Switch switchDark;
    protected Spinner spinner1;
    protected TextView texto2;
    protected Button boton1;
    protected Button boton2;


    public SettingsUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_user, container, false);
    }

    // carga despues de onCreate cuando esta listo
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchDark = view.findViewById(R.id.switch_dark_mode);
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getContext());
        switchDark.setChecked(pref.getBoolean("dark_mode", false));
        switchDark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pref.edit().putBoolean("dark_mode", isChecked).apply();
            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        });


        spinner1 = view.findViewById(R.id.spinner1_user_settings);
        String [] sizes = {"Pequeña", "Mediana", "Grande"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sizes);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adaptador);

        int indice = pref.getInt("font_size_index", 1); // mediano por defecto
        spinner1.setSelection(indice);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pref.edit().putInt("font_size_index", position).apply();

                //Seleccion de tamaños de letra
                float sizeSp;
                switch (position) {
                    case 0:
                        sizeSp = 12f;
                        break;
                    case 1:
                        sizeSp = 16f;
                        break;
                    case 2:
                        sizeSp = 20f;
                        break;
                    default:
                        sizeSp = 16f;
                        break;
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}