package org.example.sistemaeventos.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    public static Connection getConnection;
    private static String URL = "jdbc:postgresql://localhost:5432/Eventos";
    private static String USER = "postgres";
    private static String PASSWORD = "123456";

    public static Connection conectar() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args){

        try(Connection con = conectar()){
            System.out.println("Conexion exitosa");
        }
        catch (SQLException e){
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }

}