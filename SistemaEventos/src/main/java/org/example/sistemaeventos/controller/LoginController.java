package org.example.sistemaeventos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.sistemaeventos.dao.UsuarioDAO;
import org.example.sistemaeventos.model.Usuario;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void iniciarSesion() {

        String usuario = txtUsuario.getText();
        String clave = txtPassword.getText();

        if (usuario.isEmpty() || clave.isEmpty()) {
            lblMensaje.setText("Complete los campos");
            return;
        }

        Usuario usuarioEncontrado = usuarioDAO.buscarPorCredenciales(usuario, clave);

        if (usuarioEncontrado == null) {
            lblMensaje.setText("Usuario o contraseña incorrectos");
            return;
        }

        abrirDashboard(usuarioEncontrado);
    }

    private void abrirDashboard(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/org/example/sistemaeventos/view/dashboard.fxml")
            );
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setUsuario(usuario);

            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema de Eventos");
            stage.centerOnScreen();
        } catch (Exception e) {
            lblMensaje.setText("Error al cargar el sistema: " + e.getMessage());
        }
    }
}