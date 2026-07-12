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

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    @FXML
    public void iniciarSesion(){

        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        if(usuario.isEmpty() || password.isEmpty()){
            lblMensaje.setText("Complete todos los campos");
        }else{
            lblMensaje.setText("Validando usuario...");
        }
    }

    @FXML
    public void abrirRegistro(){

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