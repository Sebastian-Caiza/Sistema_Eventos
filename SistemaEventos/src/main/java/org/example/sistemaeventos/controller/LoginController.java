package org.example.sistemaeventos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.sistemaeventos.db.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    @FXML
    public void iniciarSesion() {

        String usuario = txtUsuario.getText();
        String clave = txtPassword.getText();

        if (usuario.isEmpty() || clave.isEmpty()) {
            lblMensaje.setText("Complete los campos");
            return;
        }

        String rol = validarUsuario(usuario, clave);

        if (rol == null) {
            lblMensaje.setText("Usuario o contraseña incorrectos");
            return;
        }

        if (rol.equals("admin")) {
            abrirVentana("/org/example/sistemaeventos/view/menu_admin.fxml", "Gestión De Estudiantes");
        } else if (rol.equals("recepcionista")) {
            abrirVentana("/org/example/sistemaeventos/view/eventos.fxml", "Sistema de Eventos");
        } else {
            lblMensaje.setText("Rol de usuario no reconocido");
        }
    }

    private String validarUsuario(String usuario, String clave) {
        String sql = "SELECT rol FROM usuarios WHERE usuario = ? AND contrasenia = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, clave);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("rol");
                }
            }

        } catch (SQLException e) {
            lblMensaje.setText("Error de conexión: " + e.getMessage());
        }

        return null;
    }

    private void abrirVentana(String rutaFxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            Parent root = loader.load();
            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.centerOnScreen();
        } catch (Exception e) {
            lblMensaje.setText("Error al cargar la ventana: " + e.getMessage());
        }
    }

    @FXML
    public void abrirRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/org/example/sistemaeventos/view/registro.fxml")
            );
            Parent root = loader.load();
            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            lblMensaje.setText("Error al abrir el registro: " + e.getMessage());
        }
    }
}