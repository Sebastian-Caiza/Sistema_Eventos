package org.example.sistemaeventos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static ConexionBD instancia;

    private static final String URL = "jdbc:postgresql://localhost:5432/eventos";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";

    private ConexionBD() {
    }

    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection con = ConexionBD.getInstancia().conectar()) {
            System.out.println("Conexion exitosa");
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }
}