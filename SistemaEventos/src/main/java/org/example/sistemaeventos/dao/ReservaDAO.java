package org.example.sistemaeventos.dao;

import org.example.sistemaeventos.db.ConexionBD;
import org.example.sistemaeventos.model.Reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO implements CRUD<Reserva> {

    @Override
    public void guardar(Reserva reserva) {
        String sql = "INSERT INTO reservas (usuario_id, evento_id, cantidad) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reserva.getUsuarioId());
            ps.setInt(2, reserva.getEventoId());
            ps.setInt(3, reserva.getCantidad());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar reserva: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Reserva reserva) {
        String sql = "UPDATE reservas SET usuario_id = ?, evento_id = ?, cantidad = ? WHERE id = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reserva.getUsuarioId());
            ps.setInt(2, reserva.getEventoId());
            ps.setInt(3, reserva.getCantidad());
            ps.setInt(4, reserva.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar reserva: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM reservas WHERE id = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar reserva: " + e.getMessage());
        }
    }

    @Override
    public List<Reserva> listar() {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reservas";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reserva reserva = new Reserva(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("evento_id"),
                        rs.getInt("cantidad")
                );
                lista.add(reserva);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar reservas: " + e.getMessage());
        }

        return lista;
    }
}