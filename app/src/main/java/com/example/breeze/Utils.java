package com.example.breeze;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Utils {

    // Métodos publicoa para cambiar los tamaños de la letra de manera recusrsiva desde las actividades
    public  static void cambioSizeTextViews(View root, Context context){
        // Guardo preferencias por tamaño 0,1,2 y dejo 1 por defecto
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int sizeIndice = preferences.getInt("font_size_index", 1);
        // Convertir el indice en sp
        // Cambio tamaños a algo más exagerado para poder èrcibir la diferencia facil
        float sizeInSp = (sizeIndice == 0) ? 12f : (sizeIndice == 1) ? 18f : 24f;
        // Hacemos los cambios de forma recursiva
        aplicarRecursivo(root, sizeInSp);
    }

    // Recorre todo el layout y modifica textos dentor de elementos repite proceso
    private static void aplicarRecursivo(View view, float sizeSp) {
        if (view instanceof TextView) {
            // Si es textView ->cambia tamaño
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp);
        } else if (view instanceof ViewGroup) {
            // Si es un grupo con hijos...
            ViewGroup grupo = (ViewGroup) view;
            //... recorrelo y llama esta funcion ene cada uno de los hijos
            for (int i = 0;i < grupo.getChildCount(); i++){
                aplicarRecursivo(grupo.getChildAt(i), sizeSp);
            }
        }
    }




}
