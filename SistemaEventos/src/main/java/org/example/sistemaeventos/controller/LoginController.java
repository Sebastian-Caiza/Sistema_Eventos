package org.example.sistemaeventos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

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
        }

        if (usuario.equals("admin") && clave.equals("admin")) {
            abrirVentanaPrincipal();
        } else {
            lblMensaje.setText("Usuario o contraseña incorrectos");
        }
    }

    private void abrirVentanaPrincipal() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/sistemaeventos/view/menu_admin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestion De Estudiantes");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}