package com.example.breeze;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GestorBaseDatos extends SQLiteOpenHelper {

    public GestorBaseDatos(@Nullable Context context) {
        super(context, "breezeDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, email TEXT, password TEXT, role TEXT)");
        db.execSQL("CREATE TABLE evento(eventoID INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, fecha TEXT, hora TEXT, ubicacion TEXT, capacidad INTEGER, precio REAL, organizadorID INTEGER, FOREIGN KEY (organizadorID) REFERENCES user(id))");
        db.execSQL("CREATE TABLE ticket(ticketID INTEGER     PRIMARY KEY AUTOINCREMENT, eventoID INTEGER, clienteID INTEGER, fechaCompra TEXT, FOREIGN KEY (eventoID) REFERENCES evento(eventoID), FOREIGN KEY (clienteID) REFERENCES user(id))");
        db.execSQL("CREATE TABLE feedback(feedbackID INTEGER PRIMARY KEY AUTOINCREMENT, clienteID INTEGER, eventoID INTEGER, comentario TEXT, rating INTEGER, FOREIGN KEY (clienteID) REFERENCES user(id), FOREIGN KEY (eventoID) REFERENCES event(eventoID))");

        db.execSQL("INSERT INTO user VALUES (null, 'org','orga@prueba.com', 'org', 'organizador')");
        db.execSQL("INSERT INTO user VALUES (null, 'client','user@prueba.com', 'client', 'cliente')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion<2) {

            // Tablas de cliente y organizador para datos personales y extra. No necesarias por el momento, necesitariamos hacer DB ver 3.
            //db.execSQL("CREATE TABLE organizador_perfil (userID INTEGER PRIMARY KEY, empresa TEXT, bio TEXT, FOREIGN KEY (userID) REFERENCES user(id))");
            //db.execSQL(" CREATE TABLE cliente_perfil (userID INTEGER PRIMARY KEY, preferencias TEXT, FOREIGN KEY (userID) REFERENCES user(id))");
        }
    }

    public ArrayList<String> leerListaEventos() {
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM evento", null);

        if (cur.moveToFirst()) {
            do {
                String nombre = cur.getString(cur.getColumnIndexOrThrow("nombre"));
                String descripcion = cur.getString(cur.getColumnIndexOrThrow("descripcion"));
                String fecha = cur.getString(cur.getColumnIndexOrThrow("fecha"));
                String hora = cur.getString(cur.getColumnIndexOrThrow("hora"));
                String ubicacion = cur.getString(cur.getColumnIndexOrThrow("ubicacion"));
                String cantidad = cur.getString(cur.getColumnIndexOrThrow("capacidad"));
                lista.add(nombre + " - " + fecha + " " + hora + " @ " + ubicacion + "\n" + descripcion + "Entradas libres: " + cantidad);

            } while (cur.moveToNext());
        }
            return lista;
    }

    public String comprobarCredenciales(String nombre, String pass, Context context) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM user WHERE nombre= ? AND password= ?", new String[]{nombre, pass});

        String role = null;
        if(cur.moveToFirst()){
            int roleIndex = cur.getColumnIndex("role");
            int idIndex = cur.getColumnIndex("id");
            int emailIndex = cur.getColumnIndex("email");

            //Compruebo que existe
            if (roleIndex !=-1 && idIndex !=-1 && emailIndex != -1){
                int id = cur.getInt(idIndex);
                String email = cur.getString(emailIndex);
                role = cur.getString(roleIndex);

                SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("user_id", id);
                editor.putString("name", nombre);
                editor.putString("email", email);
                editor.putString("role", role);
                editor.apply();
                return cur.getString(roleIndex).trim();
            }
        }
        return role;
    }

    public boolean existeUser(String nombre) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM user WHERE nombre= ?",new String[]{nombre});
        boolean existe = false;
        if(cur != null) {
            if(cur.moveToFirst()) {
                existe = true;
            }
        }
        return existe;
    }


    // insertuser()
    // checkUser()
    // getRole()

}

