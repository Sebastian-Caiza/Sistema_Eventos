package org.example.sistemaeventos.model;

public class Evento {

    private int id;
    private String nombre;
    private String tipo;
    private String inicio;
    private String fin;
    private String fecha;

    // Constructor vacío
    public Evento() {
    }

    public Evento(int id, String nombre, String tipo, String inicio, String fin, String fecha) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.inicio = inicio;
        this.fin = fin;
        this.fecha = fecha;
    }

    public Evento(String nombre, String tipo, String inicio, String fin, String fecha) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.inicio = inicio;
        this.fin = fin;
        this.fecha = fecha;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}