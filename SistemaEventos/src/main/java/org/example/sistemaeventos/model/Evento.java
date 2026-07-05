package org.example.sistemaeventos.model;

import java.time.LocalDate;

public class Evento {

    private int id;
    private String nombre;
    private String lugar;
    private LocalDate fecha;
    private double precio;

    public Evento() {
    }

    public Evento(int id, String nombre, String lugar, LocalDate fecha, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.lugar = lugar;
        this.fecha = fecha;
        this.precio = precio;
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

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}