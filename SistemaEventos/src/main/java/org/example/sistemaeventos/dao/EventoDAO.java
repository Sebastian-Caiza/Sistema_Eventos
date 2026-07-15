package org.example.sistemaeventos.dao;

import org.example.sistemaeventos.db.ConexionBD; // Tu clase de conexión
import org.example.sistemaeventos.model.Evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    public boolean insertar(Evento nuevoEvento) {
        String sql = "INSERT INTO eventos (nombre, tipo, inicio, fin, fecha) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEvento.getNombre());
            ps.setString(2, nuevoEvento.getTipo());
            ps.setString(3, nuevoEvento.getInicio());
            ps.setString(4, nuevoEvento.getFin());
            ps.setString(5, nuevoEvento.getFecha());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se guardó con éxito

        } catch (SQLException e) {
            System.out.println("Error al insertar evento: " + e.getMessage());
            return false;
        }
    }

    public List<Evento> listarEventos() {
        List<Evento> lista = new ArrayList<>();
        String sql = "SELECT * FROM eventos"; // Cambia 'eventos' por el nombre de tu tabla en PostgreSQL

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Evento evento = new Evento(
                        rs.getInt("id"), // Columna ID de tu BD
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getString("inicio"),
                        rs.getString("fin"),
                        rs.getString("fecha")
                );
                lista.add(evento);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar eventos: " + e.getMessage());
        }

        return lista;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM eventos WHERE id = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar evento: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Evento evento) {
        String sql = "UPDATE eventos SET nombre = ?, tipo = ?, inicio = ?, fin = ?, fecha = ? WHERE id = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, evento.getNombre());
            ps.setString(2, evento.getTipo());
            ps.setString(3, evento.getInicio());
            ps.setString(4, evento.getFin());
            ps.setString(5, evento.getFecha());
            ps.setInt(6, evento.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar evento: " + e.getMessage());
            return false;
        }
    }
}
