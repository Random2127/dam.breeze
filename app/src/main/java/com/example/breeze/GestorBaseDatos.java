package com.example.breeze;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GestorBaseDatos extends SQLiteOpenHelper {

    public GestorBaseDatos(@Nullable Context context) {
        super(context, "breezeDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, email TEXT, password TEXT, role TEXT)");
        db.execSQL("INSERT INTO user VALUES (null, 'userAdmin','user@prueba.com', 'password', 'usuario')");
        db.execSQL("INSERT INTO user VALUES (null, 'orgAdmin','orga@prueba.com', 'password', 'organizador')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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

    // insertuser()
    // checkUser()
    // getRole()

}

