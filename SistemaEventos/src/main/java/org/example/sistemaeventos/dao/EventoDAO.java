package org.example.sistemaeventos.dao;

import org.example.sistemaeventos.db.ConexionBD;
import org.example.sistemaeventos.model.Evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO implements CRUD<Evento> {

    @Override
    public void guardar(Evento nuevoEvento) {
        String sql = "INSERT INTO eventos (nombre, tipo, inicio, fin, fecha) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEvento.getNombre());
            ps.setString(2, nuevoEvento.getTipo());
            ps.setString(3, nuevoEvento.getInicio());
            ps.setString(4, nuevoEvento.getFin());
            ps.setString(5, nuevoEvento.getFecha());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al insertar evento: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Evento evento) {
        String sql = "UPDATE eventos SET nombre = ?, tipo = ?, inicio = ?, fin = ?, fecha = ? WHERE id = ?";

        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, evento.getNombre());
            ps.setString(2, evento.getTipo());
            ps.setString(3, evento.getInicio());
            ps.setString(4, evento.getFin());
            ps.setString(5, evento.getFecha());
            ps.setInt(6, evento.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar evento: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM eventos WHERE id = ?";

        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar evento: " + e.getMessage());
        }
    }

    @Override
    public List<Evento> listar() {
        List<Evento> lista = new ArrayList<>();
        String sql = "SELECT * FROM eventos";

        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Evento evento = new Evento(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getString("inicio"),
                        rs.getString("fin"),
                        rs.getString("fecha"),
                        rs.getString("estado")
                );
                lista.add(evento);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar eventos: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE eventos SET estado = ? WHERE id = ?";

        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar estado: " + e.getMessage());
            return false;
        }
    }

    public int contarPorEstado(String estado) {
        String sql = "SELECT COUNT(*) FROM eventos WHERE estado = ?";

        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al contar eventos por estado: " + e.getMessage());
        }

        return 0;
    }
}