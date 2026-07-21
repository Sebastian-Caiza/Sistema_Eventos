package org.example.sistemaeventos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.sistemaeventos.dao.EventoDAO;
import org.example.sistemaeventos.dao.ReservaDAO;
import org.example.sistemaeventos.model.Evento;
import org.example.sistemaeventos.model.Reserva;
import org.example.sistemaeventos.model.Usuario;

import java.util.List;
import java.util.Optional;

public class ReservaController {

    @FXML private TableView<Reserva> tblReservas;
    @FXML private TableColumn<Reserva, String> colEvento;
    @FXML private TableColumn<Reserva, String> colUsuario;
    @FXML private TableColumn<Reserva, Integer> colCantidad;

    @FXML private ComboBox<Evento> cmbEvento;
    @FXML private TextField txtCantidad;

    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final EventoDAO eventoDAO = new EventoDAO();

    private Usuario usuarioActivo;
    private Reserva reservaSeleccionada;

    @FXML
    public void initialize() {
        colEvento.setCellValueFactory(new PropertyValueFactory<>("nombreEvento"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        tblReservas.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, seleccionada) -> reservaSeleccionada = seleccionada
        );
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioActivo = usuario;
        cargarEventos();
        cargarReservas();
    }

    private void cargarEventos() {
        List<Evento> lista = eventoDAO.listar();
        cmbEvento.setItems(FXCollections.observableArrayList(lista));
    }

    private void cargarReservas() {
        List<Reserva> lista = reservaDAO.listarConDetalle();
        ObservableList<Reserva> datos = FXCollections.observableArrayList(lista);
        tblReservas.setItems(datos);
    }

    @FXML
    private void registrarReserva() {
        Evento eventoSeleccionado = cmbEvento.getValue();
        String textoCantidad = txtCantidad.getText();

        if (eventoSeleccionado == null || textoCantidad.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un evento e ingresa la cantidad.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(textoCantidad);
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "La cantidad debe ser un número.");
            return;
        }

        if (cantidad <= 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "La cantidad debe ser mayor a cero.");
            return;
        }

        Reserva nuevaReserva = new Reserva(0, usuarioActivo.getId(), eventoSeleccionado.getId(), cantidad);
        reservaDAO.guardar(nuevaReserva);

        txtCantidad.clear();
        cmbEvento.setValue(null);
        cargarReservas();
    }

    @FXML
    private void eliminarReserva() {
        if (reservaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona una reserva de la tabla primero.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Seguro que deseas eliminar esta reserva?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            reservaDAO.eliminar(reservaSeleccionada.getId());
            cargarReservas();
        }
    }

    @FXML
    private void volverDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/org/example/sistemaeventos/view/dashboard.fxml")
            );
            Parent root = loader.load();

            DashboardController controller = loader.getController();
            controller.setUsuario(usuarioActivo);

            Stage stage = (Stage) tblReservas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema de Eventos");
            stage.centerOnScreen();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al volver: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}