package com.example.breeze;

public class Session {

    private static Session instance;
    private int userId;
    private String nombre;
    private String email;
    private String role;


    private Session(){

    }

    public static Session getInstance() {
        // Creamos una session singleton para que no se pueda duplicar el objeto
        if (instance == null){
            instance = new Session();
        }
        return instance;
    }

    public void setSessionData(int userId, String nombre, String email, String role) {
        this.userId = userId;
        this.nombre = nombre;
        this.email = email;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void clearSession() {
        instance = null;
    }
}
