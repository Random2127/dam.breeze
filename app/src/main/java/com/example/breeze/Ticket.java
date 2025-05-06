package com.example.breeze;


// Esta clase nunca es usada aunque tenía la intencion
// Permitiría introducir y pasar la entrada como un objeto
public class Ticket {


    private String ticketId;
    private int eventID;
    private int userId;
    private boolean usado;
    private String fechacompra;
    private String nombreEvento;

    public Ticket(String ticketId, int eventID, int userId, boolean usado, String fechacompra, String nombreEvento) {
        this.ticketId = ticketId;
        this.eventID = eventID;
        this.userId = userId;
        this.usado = usado;
        this.fechacompra = fechacompra;
        this.nombreEvento = nombreEvento;
    }


    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public String getFechacompra() {
        return fechacompra;
    }

    public void setFechacompra(String fechacompra) {
        this.fechacompra = fechacompra;
    }
    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }
}
