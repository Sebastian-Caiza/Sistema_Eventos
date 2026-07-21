package org.example.sistemaeventos.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.sistemaeventos.db.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistroController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasenia;
    @FXML private ComboBox<String> cmbRol;

    @FXML
    private void initialize() {
        if (cmbRol != null) {
            cmbRol.getItems().addAll("admin", "recepcionista", "supervisor");
        }
    }

    @FXML
    public void registro() {
        String usuario = txtUsuario.getText().trim();
        String contrasenia = txtContrasenia.getText().trim();
        String rol = cmbRol.getValue();

        if (usuario.isEmpty() || contrasenia.isEmpty() || rol == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "llene los campos");
            return;
        }

        try (Connection conn = ConexionBD.getInstancia().conectar()) {
            if (existeUsuario(conn, usuario)) {
                mostrarAlerta(Alert.AlertType.WARNING, "Usuario ya registrado", "ya existe");
                return;
            }

            if (registrarUsuario(conn, usuario, contrasenia, rol)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Listo", "El registro se hizo correctamente");

                inicioSesion(usuario, rol);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", "Hubo un problema al procesar la solicitud.");
        }
    }

    private boolean existeUsuario(Connection conn, String usuario) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, usuario);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    private boolean registrarUsuario(Connection conn, String usuario, String contrasenia, String rol) throws SQLException {
        String sql = "INSERT INTO usuarios (usuario, contrasenia, rol) VALUES (?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, usuario);
            pst.setString(2, contrasenia);
            pst.setString(3, rol);

            int filasAfectadas = pst.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    private void inicioSesion(String usuario, String rol) {
        System.out.println("Cargando menú para el usuario: " + usuario + " con rol: " + rol);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}