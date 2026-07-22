package org.example.sistemaeventos.model;

public class Reserva {

    private int id;
    private int usuarioId;
    private int eventoId;
    private int cantidad;
    private String nombreEvento;
    private String nombreUsuario;

    public Reserva() {
    }

    public Reserva(int id, int usuarioId, int eventoId, int cantidad) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public String toString() {
        return "Reserva #" + getId() + " (Usuario ID: " + usuarioId + " | Evento ID: " + eventoId + ")";
    }
}