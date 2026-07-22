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

    @FXML private VBox panelResumen;
    @FXML private Label lblTotalEventos;
    @FXML private Label lblPendientes;
    @FXML private Label lblConfirmados;
    @FXML private Label lblCancelados;

    private final EventoDAO eventoDAO = new EventoDAO();
    private Usuario usuarioActivo;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colInicio.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        colFin.setCellValueFactory(new PropertyValueFactory<>("fin"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioActivo = usuario;

        lblUsuarioActivo.setText(usuario.getUsuario());
        lblRolActivo.setText(usuario.getRol());

        boolean esAdmin = "admin".equalsIgnoreCase(usuario.getRol());
        boolean esRecepcionista = "recepcionista".equalsIgnoreCase(usuario.getRol());
        boolean esSupervisor = !esAdmin && !esRecepcionista;

        panelFormulario.setVisible(esAdmin);
        panelFormulario.setManaged(esAdmin);

        panelAcciones.setVisible(esRecepcionista);
        panelAcciones.setManaged(esRecepcionista);

        boolean puedeGestionarReservas = esAdmin || esRecepcionista;
        btnReservas.setVisible(puedeGestionarReservas);
        btnReservas.setManaged(puedeGestionarReservas);

        panelResumen.setVisible(esSupervisor);
        panelResumen.setManaged(esSupervisor);

        cargarEventos();

        if (esSupervisor) {
            cargarResumen();
        }
    }

    private void cargarEventos() {
        List<Evento> lista = eventoDAO.listar();
        ObservableList<Evento> datos = FXCollections.observableArrayList(lista);
        tblEventos.setItems(datos);
    }

    private void cargarResumen() {
        int total = eventoDAO.listar().size();
        int pendientes = eventoDAO.contarPorEstado("pendiente");
        int confirmados = eventoDAO.contarPorEstado("confirmado");
        int cancelados = eventoDAO.contarPorEstado("cancelado");

        lblTotalEventos.setText(String.valueOf(total));
        lblPendientes.setText(String.valueOf(pendientes));
        lblConfirmados.setText(String.valueOf(confirmados));
        lblCancelados.setText(String.valueOf(cancelados));
    }

    @FXML
    private void verDetalle() {
        Evento seleccionado = tblEventos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un evento de la tabla primero.");
            return;
        }

        lblNombre.setText(seleccionado.getNombre());
        lblTipo.setText(seleccionado.getTipo());
        lblInicio.setText(seleccionado.getInicio());
        lblFin.setText(seleccionado.getFin());
        lblFecha.setText(seleccionado.getFecha());
        lblEstado.setText(seleccionado.getEstado());

        if ("admin".equalsIgnoreCase(usuarioActivo.getRol())) {
            txtNombre.setText(seleccionado.getNombre());
            txtTipo.setText(seleccionado.getTipo());
            txtInicio.setText(seleccionado.getInicio());
            txtFin.setText(seleccionado.getFin());

            if (seleccionado.getFecha() != null && !seleccionado.getFecha().isEmpty()) {
                dpFecha.setValue(LocalDate.parse(seleccionado.getFecha()));
            } else {
                dpFecha.setValue(null);
            }
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
    private void actualizar() {
        Evento seleccionado = tblEventos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un evento de la tabla primero.");
            return;
        }

        seleccionado.setNombre(txtNombre.getText());
        seleccionado.setTipo(txtTipo.getText());
        seleccionado.setInicio(txtInicio.getText());
        seleccionado.setFin(txtFin.getText());

        if (dpFecha.getValue() != null) {
            seleccionado.setFecha(dpFecha.getValue().toString());
        } else {
            seleccionado.setFecha("");
        }

        eventoDAO.actualizar(seleccionado);

        limpiarCampos();
        cargarEventos();
    }

    @FXML
    private void eliminar() {
        Evento seleccionado = tblEventos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un evento de la tabla primero.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Seguro que deseas eliminar el evento \"" + seleccionado.getNombre() + "\"?");

        ButtonType resultado = confirmacion.showAndWait().orElse(null);

        if (resultado == ButtonType.OK) {
            eventoDAO.eliminar(seleccionado.getId());
            limpiarCampos();
            cargarEventos();
        }
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
        Evento seleccionado = tblEventos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecciona un evento de la tabla primero.");
            return;
        }

        boolean exito = eventoDAO.actualizarEstado(seleccionado.getId(), nuevoEstado);

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
}