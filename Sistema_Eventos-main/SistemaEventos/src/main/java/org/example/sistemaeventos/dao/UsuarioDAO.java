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
        String sql = "INSERT INTO usuarios (usuario, contrasenia, rol) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContrasenia());
            ps.setString(3, usuario.getRol());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET usuario = ?, contrasenia = ?, rol = ? WHERE id = ?";

        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContrasenia());
            ps.setString(3, usuario.getRol());
            ps.setInt(4, usuario.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection con = ConexionBD.getInstancia().conectar();
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

        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("contrasenia"),
                        rs.getString("rol")
                );
                lista.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }

    public Usuario buscarPorCredenciales(String usuario, String contrasenia) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasenia = ?";

        try (Connection con = ConexionBD.getInstancia().conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasenia);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("usuario"),
                            rs.getString("contrasenia"),
                            rs.getString("rol")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
        }

        return null;
    }
}