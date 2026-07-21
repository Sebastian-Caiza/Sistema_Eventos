package org.example.sistemaeventos.dao;

import org.example.sistemaeventos.db.ConexionBD;
import org.example.sistemaeventos.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements CRUD<Usuario> {

    @Override
    public void guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, correo, contrasena, rol) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContrasena());
            ps.setString(5, usuario.getRol());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, correo = ?, contrasena = ?, rol = ? WHERE id = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getContrasena());
            ps.setString(5, usuario.getRol());
            ps.setInt(6, usuario.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("contrasena"),
                        rs.getString("rol")
                );
                lista.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }
}