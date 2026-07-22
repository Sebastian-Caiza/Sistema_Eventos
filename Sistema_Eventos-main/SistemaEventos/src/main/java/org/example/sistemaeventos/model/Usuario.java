package org.example.sistemaeventos.model;

public class Usuario extends Persona {

    private String contrasenia;
    private String rol;

    public Usuario() {
        super();
    }

    public Usuario(int id, String usuario, String contrasenia, String rol) {
        super(id, usuario);
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return getUsuario() + " (" + rol + ")";
    }
}