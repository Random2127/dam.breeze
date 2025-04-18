package com.example.breeze;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GestorBaseDatos extends SQLiteOpenHelper {

    public GestorBaseDatos(@Nullable Context context) {
        super(context, "breezeDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, email TEXT, password TEXT, role TEXT)");
        db.execSQL("INSERT INTO user VALUES (null, 'userAdmin','user@prueba.com', 'password', 'cliente')");
        db.execSQL("INSERT INTO user VALUES (null, 'orgAdmin','orga@prueba.com', 'password', 'organizador')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion<2) {
            db.execSQL("CREATE TABLE evento(eventoID INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, fecha TEXT, ubicacion TEXT, capacidad INTEGER, organizadorID INTEGER, FOREIGN KEY (organizadorID) REFERENCES user(id))");
            db.execSQL("CREATE TABLE ticket(ticketID INTEGER PRIMARY KEY AUTOINCREMENT, eventoID INTEGER, clienteID INTEGER, precio REAL, fechaCompra TEXT, FOREIGN KEY (eventoID) REFERENCES event(eventoID), FOREIGN KEY (clienteID) REFERENCES user(id))");
            db.execSQL("CREATE TABLE feedback(feedbackID INTEGER PRIMARY KEY AUTOINCREMENT, clienteID INTEGER, eventoID INTEGER, comentario TEXT, rating INTEGER, FOREIGN KEY (clienteID) REFERENCES user(id), FOREIGN KEY (eventoID) REFERENCES event(eventoID))");
            // Tablas de cliente y organizador para datos personales y extra. No necesarias por el momento, necesitariamos hacer DB ver 3.
            //db.execSQL("CREATE TABLE organizador_perfil (userID INTEGER PRIMARY KEY, empresa TEXT, bio TEXT, FOREIGN KEY (userID) REFERENCES user(id))");
            //db.execSQL(" CREATE TABLE cliente_perfil (userID INTEGER PRIMARY KEY, preferencias TEXT, FOREIGN KEY (userID) REFERENCES user(id))");
        }
    }

    public String comprobarCredenciales(String nombre, String pass) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM user WHERE nombre= ? AND password= ?", new String[]{nombre, pass});

        if(cur.moveToFirst()){
            int roleIndex = cur.getColumnIndex("role");
            String role = cur.getString(roleIndex);
            return role;
        } else {
            return null;
        }
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

