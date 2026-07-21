package org.example.sistemaeventos.model;

public abstract class Persona {

    private int id;
    private String usuario;

    public Persona() {
    }

    public Persona(int id, String usuario) {
        this.id = id;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}