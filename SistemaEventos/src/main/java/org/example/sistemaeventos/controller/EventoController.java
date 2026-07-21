package org.example.sistemaeventos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.sistemaeventos.dao.EventoDAO;
import org.example.sistemaeventos.model.Evento;

import java.util.List;

public class EventoController {

    @FXML private TableView<Evento> tblEventos;
    @FXML private TableColumn<Evento, String> colNombre;
    @FXML private TableColumn<Evento, String> colTipo;
    @FXML private TableColumn<Evento, String> colInicio;
    @FXML private TableColumn<Evento, String> colFin;
    @FXML private TableColumn<Evento, String> colFecha;
    @FXML private TableColumn<Evento, String> colEstado;

    @FXML private Label lblNombre;
    @FXML private Label lblTipo;
    @FXML private Label lblInicio;
    @FXML private Label lblFin;
    @FXML private Label lblFecha;
    @FXML private Label lblEstado;

    private final EventoDAO eventoDAO = new EventoDAO();
    private Evento eventoSeleccionado;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colInicio.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        colFin.setCellValueFactory(new PropertyValueFactory<>("fin"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cargarEventos();

        tblEventos.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, seleccionado) -> mostrarDetalle(seleccionado)
        );
    }

    private void cargarEventos() {
        List<Evento> lista = eventoDAO.listar();
        ObservableList<Evento> datos = FXCollections.observableArrayList(lista);
        tblEventos.setItems(datos);
    }

    private void mostrarDetalle(Evento evento) {
        eventoSeleccionado = evento;

        if (evento == null) {
            lblNombre.setText("-");
            lblTipo.setText("-");
            lblInicio.setText("-");
            lblFin.setText("-");
            lblFecha.setText("-");
            lblEstado.setText("-");
            return;
        }

        lblNombre.setText(evento.getNombre());
        lblTipo.setText(evento.getTipo());
        lblInicio.setText(evento.getInicio());
        lblFin.setText(evento.getFin());
        lblFecha.setText(evento.getFecha());
        lblEstado.setText(evento.getEstado());
    }

    @FXML
    private void confirmarEvento() {
        cambiarEstado("confirmado");
    }

    @FXML
    private void cancelarEvento() {
        cambiarEstado("cancelado");
    }

    private void cambiarEstado(String nuevoEstado) {
        if (eventoSeleccionado == null) {
            mostrarAlerta("Selecciona un evento de la tabla primero.");
            return;
        }

        boolean exito = eventoDAO.actualizarEstado(eventoSeleccionado.getId(), nuevoEstado);

        if (exito) {
            cargarEventos();
            lblEstado.setText(nuevoEstado);
        } else {
            mostrarAlerta("No se pudo actualizar el estado del evento.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void actualizarLista() {
        cargarEventos();
    }

    @FXML
    private void volverMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/org/example/sistemaeventos/view/login.fxml")
            );
            Parent root = loader.load();
            Stage stage = (Stage) tblEventos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema de Eventos");
            stage.centerOnScreen();
        } catch (Exception e) {
            mostrarAlerta("Error al volver al login: " + e.getMessage());
        }
    }
}