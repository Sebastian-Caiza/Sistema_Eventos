package org.example.sistemaeventos.model;

public class Usuario extends Persona {

    private String contrasena;
    private String rol;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String apellido, String correo, String contrasena, String rol) {

        super(id, nombre, apellido, correo);
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}