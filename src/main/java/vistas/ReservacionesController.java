package vistas;

import com.example.modelos.ReservacionDAO;
import com.example.modelos.MesaDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleIntegerProperty;

public class ReservacionesController {

    @FXML private DatePicker dpFecha;
    @FXML private ComboBox<LocalTime> cbHora;
    @FXML private TextField tfNombreCliente;
    @FXML private TextField tfTelefono;
    @FXML private ComboBox<MesaDAO> cbMesa;
    @FXML private TableView<ReservacionDAO> tvReservaciones;

    public void initialize() {
        // Configurar ComboBox de horas (cada 30 minutos)
        for (int h = 10; h <= 22; h++) {
            cbHora.getItems().add(LocalTime.of(h, 0));
            cbHora.getItems().add(LocalTime.of(h, 30));
        }

        // Configurar tabla de reservaciones
        TableColumn<ReservacionDAO, String> colNombre = new TableColumn<>("Cliente");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));

        TableColumn<ReservacionDAO, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<ReservacionDAO, LocalDate> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<ReservacionDAO, LocalTime> colHora = new TableColumn<>("Hora");
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));

        TableColumn<ReservacionDAO, Integer> colMesa = new TableColumn<>("Mesa");
        colMesa.setCellValueFactory(cellData -> {
            MesaDAO mesa = cellData.getValue().getMesa();
            return (ObservableValue<Integer>) new SimpleIntegerProperty(mesa.getNumero()).asObject();
        });

        tvReservaciones.getColumns().addAll(colNombre, colTelefono, colFecha, colHora, colMesa);

        cargarMesasDisponibles();
        cargarReservaciones();
    }

    private void cargarMesasDisponibles() {
        try {
            ObservableList<MesaDAO> mesas = MesaDAO.obtenerMesasDisponibles();
            cbMesa.setItems(mesas);
        } catch (Exception e) {
            mostrarError("Error al cargar mesas: " + e.getMessage());
        }
    }

    private void cargarReservaciones() {
        try {
            ObservableList<ReservacionDAO> reservaciones = ReservacionDAO.obtenerTodas();
            tvReservaciones.setItems(reservaciones);
        } catch (Exception e) {
            mostrarError("Error al cargar reservaciones: " + e.getMessage());
        }
    }

    @FXML
    private void guardarReservacion() {
        if (validarCampos()) {
            ReservacionDAO reservacion = new ReservacionDAO();
            reservacion.setNombreCliente(tfNombreCliente.getText());
            reservacion.setTelefono(tfTelefono.getText());
            reservacion.setFecha(dpFecha.getValue());
            reservacion.setHora(cbHora.getValue());
            reservacion.setMesa(cbMesa.getValue());

            try {
                reservacion.insertar();
                mostrarMensaje("Reservación guardada exitosamente");
                limpiarCampos();
                cargarReservaciones();
                cargarMesasDisponibles(); // Actualizar mesas disponibles
            } catch (Exception e) {
                mostrarError("Error al guardar reservación: " + e.getMessage());
            }
        } else {
            mostrarError("Complete todos los campos requeridos");
        }
    }

    private boolean validarCampos() {
        if (dpFecha.getValue() == null) {
            mostrarError("Seleccione una fecha");
            return false;
        }
        if (cbHora.getValue() == null) {
            mostrarError("Seleccione una hora");
            return false;
        }
        if (tfNombreCliente.getText().isEmpty()) {
            mostrarError("Ingrese el nombre del cliente");
            return false;
        }
        if (cbMesa.getValue() == null) {
            mostrarError("Seleccione una mesa");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        tfNombreCliente.clear();
        tfTelefono.clear();
        dpFecha.setValue(null);
        cbHora.setValue(null);
        cbMesa.setValue(null);
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }
}