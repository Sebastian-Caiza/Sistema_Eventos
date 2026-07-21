package org.example.sistemaeventos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.sistemaeventos.dao.EventoDAO;
import org.example.sistemaeventos.model.Evento;
import org.example.sistemaeventos.model.Usuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DashboardController {

    @FXML private Label lblUsuarioActivo;
    @FXML private Label lblRolActivo;

    @FXML private VBox panelFormulario;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTipo;
    @FXML private TextField txtInicio;
    @FXML private TextField txtFin;
    @FXML private DatePicker dpFecha;

    @FXML private TableView<Evento> tblEventos;
    @FXML private TableColumn<Evento, String> colNombre;
    @FXML private TableColumn<Evento, String> colTipo;
    @FXML private TableColumn<Evento, String> colInicio;
    @FXML private TableColumn<Evento, String> colFin;
    @FXML private TableColumn<Evento, String> colFecha;
    @FXML private TableColumn<Evento, String> colEstado;

    @FXML private VBox panelDetalle;
    @FXML private Label lblNombre;
    @FXML private Label lblTipo;
    @FXML private Label lblInicio;
    @FXML private Label lblFin;
    @FXML private Label lblFecha;
    @FXML private Label lblEstado;
    @FXML private HBox panelAcciones;
    @FXML private Button btnReservas;

    private final EventoDAO eventoDAO = new EventoDAO();
    private Evento eventoSeleccionado;
    private Usuario usuarioActivo;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colInicio.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        colFin.setCellValueFactory(new PropertyValueFactory<>("fin"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tblEventos.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, seleccionado) -> mostrarDetalle(seleccionado)
        );
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioActivo = usuario;

        lblUsuarioActivo.setText(usuario.getUsuario());
        lblRolActivo.setText(usuario.getRol());

        boolean esAdmin = "admin".equalsIgnoreCase(usuario.getRol());
        boolean esRecepcionista = "recepcionista".equalsIgnoreCase(usuario.getRol());

        panelFormulario.setVisible(esAdmin);
        panelFormulario.setManaged(esAdmin);

        panelAcciones.setVisible(esRecepcionista);
        panelAcciones.setManaged(esRecepcionista);
        boolean puedeGestionarReservas = esAdmin || esRecepcionista;
        btnReservas.setVisible(puedeGestionarReservas);
        btnReservas.setManaged(puedeGestionarReservas);

        cargarEventos();
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

        if ("admin".equalsIgnoreCase(usuarioActivo.getRol())) {
            txtNombre.setText(evento.getNombre());
            txtTipo.setText(evento.getTipo());
            txtInicio.setText(evento.getInicio());
            txtFin.setText(evento.getFin());
            dpFecha.setValue(evento.getFecha() != null && !evento.getFecha().isEmpty()
                    ? LocalDate.parse(evento.getFecha()) : null);
        }
    }

    @FXML
    private void registrar() {
        String nombre = txtNombre.getText();
        String tipo = txtTipo.getText();
        String inicio = txtInicio.getText();
        String fin = txtFin.getText();
        LocalDate fecha = dpFecha.getValue();

        if (nombre.isEmpty() || tipo.isEmpty() || fecha == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Por favor llena los campos obligatorios.");
            return;
        }

        Evento nuevoEvento = new Evento(nombre, tipo, inicio, fin, fecha.toString());
        eventoDAO.guardar(nuevoEvento);

        limpiarCampos();
        cargarEventos();
    }

    @FXML
    private void eliminar() {
        if (eventoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un evento de la tabla primero.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Seguro que deseas eliminar el evento \"" + eventoSeleccionado.getNombre() + "\"?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            eventoDAO.eliminar(eventoSeleccionado.getId());
            limpiarCampos();
            cargarEventos();
        }
    }

    @FXML
    private void actualizar() {
        if (eventoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un evento de la tabla primero.");
            return;
        }

        eventoSeleccionado.setNombre(txtNombre.getText());
        eventoSeleccionado.setTipo(txtTipo.getText());
        eventoSeleccionado.setInicio(txtInicio.getText());
        eventoSeleccionado.setFin(txtFin.getText());
        eventoSeleccionado.setFecha(dpFecha.getValue() != null ? dpFecha.getValue().toString() : "");

        eventoDAO.actualizar(eventoSeleccionado);

        limpiarCampos();
        cargarEventos();
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
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un evento de la tabla primero.");
            return;
        }

        boolean exito = eventoDAO.actualizarEstado(eventoSeleccionado.getId(), nuevoEstado);

        if (exito) {
            cargarEventos();
            lblEstado.setText(nuevoEstado);
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "No se pudo actualizar el estado del evento.");
        }
    }

    @FXML
    private void actualizarLista() {
        cargarEventos();
    }

    @FXML
    private void cerrarSesion() {
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
            mostrarAlerta(Alert.AlertType.ERROR, "Error al cerrar sesión: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtTipo.clear();
        txtInicio.clear();
        txtFin.clear();
        dpFecha.setValue(null);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void irAReservas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/org/example/sistemaeventos/view/reservas.fxml")
            );
            Parent root = loader.load();

            ReservaController controller = loader.getController();
            controller.setUsuario(usuarioActivo);

            Stage stage = (Stage) tblEventos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema de Eventos - Reservas");
            stage.centerOnScreen();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al abrir reservas: " + e.getMessage());
        }
    }
}