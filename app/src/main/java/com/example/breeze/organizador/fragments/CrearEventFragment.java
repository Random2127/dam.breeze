package com.example.breeze.organizador.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

    protected Button btnNext;
    protected Button btnBack;

    protected GestorBaseDatos gbd;
    protected Intent pasarPantalla;
    protected View step1;
    protected View step2;
    protected View step3;
    protected int currentStep = 1;

    protected ImageView imagenEvento;
    protected Button btnSeleccionarImg;
    protected Uri imagenSeleccioanda;


    protected ActivityResultLauncher<Intent> pickImageLauncher;
    protected Uri imagenSeleccionadaUri;



    public CrearEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_event, container, false);

        boton1 = (Button) view.findViewById(R.id.boton1_crear);
        caja1 = (EditText) view.findViewById(R.id.caja1_crear);
        caja2 = (EditText) view.findViewById(R.id.caja2_crear);
        caja3 = (EditText) view.findViewById(R.id.caja3_crear);
        caja4 = (EditText) view.findViewById(R.id.caja4_crear);
        caja5 = (EditText) view.findViewById(R.id.caja5_crear);
        caja6 = (EditText) view.findViewById(R.id.caja6_crear);
        caja7 = (EditText) view.findViewById(R.id.caja7_crear);

        btnNext = (Button) view.findViewById(R.id.btnNext);
        btnBack = (Button) view.findViewById(R.id.btnBack);
        imagenEvento = (ImageView) view.findViewById(R.id.imagenPreview);
        btnSeleccionarImg = (Button) view.findViewById(R.id.btnSeleccionarImagen);
        gbd = new GestorBaseDatos(getContext());
        Utils.cambioSizeTextViews(view, getContext());

        //"paginas" del fragmento
        step1 = (View) view.findViewById(R.id.step1);
        step2 = (View) view.findViewById(R.id.step2);
        step3 = (View) view.findViewById(R.id.step3);

        // Muestro solo el primer paso
        step1.setVisibility(View.VISIBLE);
        step2.setVisibility(View.GONE);
        step3.setVisibility(View.GONE);

        btnBack.setVisibility(View.GONE);
        boton1.setVisibility(View.GONE);

        // Inicializamos selector de imagenes todo guiado aqui GPT
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        imagenSeleccionadaUri = result.getData().getData();
                        imagenEvento.setImageURI(imagenSeleccionadaUri); // set the image preview
                    }
                }
        );

        //Boton para cargar imagenes
        btnSeleccionarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pasarPantalla = new Intent(Intent.ACTION_PICK);
                pasarPantalla.setType("image/*");
                pickImageLauncher.launch(pasarPantalla);
            }
        });


        // Como se cargan los botones y al final el de aceptar creare evento
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep == 1) {
                    step1.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                    btnBack.setVisibility(View.VISIBLE);
                    currentStep = 2;
                } else if (currentStep == 2) {
                    step2.setVisibility(View.GONE);
                    step3.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.GONE);
                    btnBack.setVisibility(View.VISIBLE);
                    boton1.setVisibility(View.VISIBLE);
                    currentStep = 3;
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep == 3) {
                    step3.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                    boton1.setVisibility(View.GONE);
                    btnNext.setVisibility(View.VISIBLE);
                    currentStep = 2;
                } else if (currentStep == 2) {
                    step2.setVisibility(View.GONE);
                    step1.setVisibility(View.VISIBLE);
                    btnBack.setVisibility(View.VISIBLE);
                    currentStep = 1;
                }
            }
        });
        // "Aceptar" y guardar datos en DB
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Convertir la Uri a String si hay
                String uriImagenTexto = imagenSeleccionadaUri != null ? imagenSeleccionadaUri.toString() : "";
                // Cojo el id de sharedPrefs
                SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int organizerId = prefs.getInt("user_id", -1);

                Event evento = new Event(
                        organizerId,
                        caja1.getText().toString().trim(), // nombre
                        caja2.getText().toString().trim(), // descripcion
                        caja3.getText().toString().trim(), // fecha
                        caja4.getText().toString().trim(), // hora
                        caja5.getText().toString().trim(), // ubicacion
                        Integer.parseInt(caja6.getText().toString().trim()), // capacidad
                        Double.parseDouble(caja7.getText().toString().trim()), // precio
                       uriImagenTexto
                );

                SQLiteDatabase db = gbd.getWritableDatabase();

                String sql = "INSERT INTO evento (nombre, descripcion, fecha, hora, ubicacion, capacidad, precio, urlImagen, organizadorID) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                db.execSQL(sql, new Object[]{
                        evento.getNombre(),
                        evento.getDescripcion(),
                        evento.getFecha(),
                        evento.getHora(),
                        evento.getUbicacion(),
                        evento.getCapacidad(),
                        evento.getPrecio(),
                        evento.getUrlImagen(),
                        organizerId
                });
                Toast.makeText(requireContext(), "Evento creado correctamente", Toast.LENGTH_SHORT).show();
                // Esto no ha valido (no se porqué)
                //requireActivity().getSupportFragmentManager().popBackStack();

                // Esto te devuelve al fragmento home tras añadir evento
                Fragment homeFragment = new HomeOrgFragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, homeFragment)
                        .addToBackStack(null) // Optional: allows user to go back to CrearEventFragment
                        .commit();
            }
        });
        return view;
    }
}