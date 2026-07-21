package org.example.sistemaeventos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.sistemaeventos.dao.CRUD;
import org.example.sistemaeventos.dao.EventoDAO;
import org.example.sistemaeventos.model.Evento;

import java.time.LocalDate;

public class MenuController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTipo;

    @FXML
    private TextField txtInicio;

    @FXML
    private TextField txtFin;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private TableView<Evento> tblEventos;

    @FXML
    private TableColumn<Evento, String> colNombre;

    @FXML
    private TableColumn<Evento, String> colTipo;

    @FXML
    private TableColumn<Evento, String> colInicio;

    @FXML
    private TableColumn<Evento, String> colFin;

    @FXML
    private TableColumn<Evento, String> colFecha;

    private CRUD<Evento> eventoDAO = new EventoDAO();

    private ObservableList<Evento> listaEventos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colInicio.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        colFin.setCellValueFactory(new PropertyValueFactory<>("fin"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        mostrarEventos();
    }

    private void mostrarEventos() {
        listaEventos.clear();
        listaEventos.addAll(eventoDAO.listar());
        tblEventos.setItems(listaEventos);
    }

    @FXML
    public void registrar() {
        String nombre = txtNombre.getText();
        String tipo = txtTipo.getText();
        String inicio = txtInicio.getText();
        String fin = txtFin.getText();
        LocalDate fecha = dpFecha.getValue();

        if (nombre.isEmpty() || tipo.isEmpty() || fecha == null) {
            System.out.println("Por favor llena los campos obligatorios");
            return;
        }

        Evento nuevoEvento = new Evento(nombre, tipo, inicio, fin, fecha.toString());

        eventoDAO.guardar(nuevoEvento);

        limpiarCampos();
        mostrarEventos();
        System.out.println("Evento registrado con éxito.");
    }

    @FXML
    public void eliminar() {
        Evento seleccionado = tblEventos.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            eventoDAO.eliminar(seleccionado.getId());

            mostrarEventos();
            limpiarCampos();
            tblEventos.getSelectionModel().clearSelection();
            System.out.println("Evento eliminado con éxito.");
        } else {
            System.out.println("Por favor, selecciona un evento de la tabla para eliminarlo.");
        }
    }

    @FXML
    public void actualizar() {
        Evento seleccionado = tblEventos.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setTipo(txtTipo.getText());
            seleccionado.setInicio(txtInicio.getText());
            seleccionado.setFin(txtFin.getText());
            seleccionado.setFecha(dpFecha.getValue() != null ? dpFecha.getValue().toString() : "");

            eventoDAO.actualizar(seleccionado);

            mostrarEventos();
            limpiarCampos();
            tblEventos.getSelectionModel().clearSelection();
            System.out.println("Evento actualizado con éxito.");
        } else {
            System.out.println("Por favor, selecciona un evento de la tabla para poder editarlo.");
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtTipo.clear();
        txtInicio.clear();
        txtFin.clear();
        dpFecha.setValue(null);
    }
}
