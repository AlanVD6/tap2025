package vistas;

import com.example.modelos.ClientesDAO;
import com.example.modelos.MesaDAO;
import com.example.modelos.ReservacionDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.List;

public class ReservacionView {
    private BorderPane root;
    private TextField tfNombre, tfTelefono, tfDireccion, tfEmail;
    private DatePicker dpFecha;
    private Spinner<Integer> spPersonas;
    private ComboBox<String> cbHora, cbMesa;
    private List<MesaDAO> mesasRegistradas;

    public ReservacionView(BorderPane root) {
        this.root = root;
        this.mesasRegistradas = obtenerMesasRegistradas();
        mostrarFormularioReservacion();
    }

    private List<MesaDAO> obtenerMesasRegistradas() {
        MesaDAO mesaDAO = new MesaDAO();
        return mesaDAO.SELECT();
    }

    private void mostrarFormularioReservacion() {
        ScrollPane scrollPrincipal = new ScrollPane();
        scrollPrincipal.setFitToWidth(true);
        scrollPrincipal.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox formulario = new VBox(15);
        formulario.setPadding(new Insets(20));
        formulario.setAlignment(Pos.TOP_CENTER);
        formulario.getStyleClass().add("detail-container");

        Text titulo = new Text("Reservación");
        titulo.getStyleClass().add("item-name");
        titulo.setStyle("-fx-font-size: 24px; -fx-fill: #2c3e50;");

        Text seccionCliente = new Text("Datos del Cliente");
        seccionCliente.setStyle("-fx-font-size: 18px; -fx-fill: #3498db; -fx-font-weight: bold;");

        GridPane gridCliente = new GridPane();
        gridCliente.setHgap(15);
        gridCliente.setVgap(10);
        gridCliente.setPadding(new Insets(10));
        gridCliente.setAlignment(Pos.CENTER);

        tfNombre = new TextField();
        tfNombre.setPromptText("Ej: Juan Pérez");
        tfTelefono = new TextField();
        tfTelefono.setPromptText("Ej: 5512345678");
        tfDireccion = new TextField();
        tfDireccion.setPromptText("Ej: Av. Principal #123");
        tfEmail = new TextField();
        tfEmail.setPromptText("Ej: cliente@ejemplo.com");

        gridCliente.add(new Label("Nombre completo:"), 0, 0);
        gridCliente.add(tfNombre, 1, 0);
        gridCliente.add(new Label("Teléfono:"), 0, 1);
        gridCliente.add(tfTelefono, 1, 1);
        gridCliente.add(new Label("Dirección:"), 0, 2);
        gridCliente.add(tfDireccion, 1, 2);
        gridCliente.add(new Label("Email (opcional):"), 0, 3);
        gridCliente.add(tfEmail, 1, 3);

        Text seccionReserva = new Text("Detalles de la Reservación");
        seccionReserva.setStyle("-fx-font-size: 18px; -fx-fill: #3498db; -fx-font-weight: bold;");

        GridPane gridReserva = new GridPane();
        gridReserva.setHgap(15);
        gridReserva.setVgap(10);
        gridReserva.setPadding(new Insets(10));
        gridReserva.setAlignment(Pos.CENTER);

        dpFecha = new DatePicker();
        dpFecha.setValue(LocalDate.now().plusDays(1));

        cbHora = new ComboBox<>();
        cbHora.getItems().addAll(
                "10:00", "11:00", "12:00", "13:00", "14:00",
                "15:00", "16:00", "17:00", "18:00", "19:00",
                "20:00", "21:00", "22:00"
        );
        cbHora.setValue("19:00");

        spPersonas = new Spinner<>(1, 20, 2);
        spPersonas.setEditable(true);

        gridReserva.add(new Label("Fecha de reservación:"), 0, 0);
        gridReserva.add(dpFecha, 1, 0);
        gridReserva.add(new Label("Hora de reservación:"), 0, 1);
        gridReserva.add(cbHora, 1, 1);
        gridReserva.add(new Label("Número de personas:"), 0, 2);
        gridReserva.add(spPersonas, 1, 2);

        Text seccionMesa = new Text("Selección de Mesa");
        seccionMesa.setStyle("-fx-font-size: 18px; -fx-fill: #3498db; -fx-font-weight: bold;");

        VBox contenedorMesa = new VBox(10);
        contenedorMesa.setAlignment(Pos.CENTER_LEFT);
        contenedorMesa.setPadding(new Insets(10));

        cbMesa = new ComboBox<>();
        for (MesaDAO mesa : mesasRegistradas) {
            if ("disponible".equalsIgnoreCase(mesa.getEstado())) {
                cbMesa.getItems().add("Mesa " + mesa.getNumero() + " (Capacidad: " + mesa.getCapacidad() + ")");
            }
        }
        cbMesa.setPromptText("Seleccione una mesa disponible");

        contenedorMesa.getChildren().addAll(
                new Label("Seleccione una mesa disponible:"),
                cbMesa,
                new Label("Nota: Solo se muestran mesas con disponibilidad")
        );

        HBox botones = new HBox(20);
        botones.setAlignment(Pos.CENTER);
        botones.setPadding(new Insets(20, 0, 0, 0));

        Button btnGuardar = new Button("Confirmar Reservación");
        btnGuardar.getStyleClass().add("order-button");
        btnGuardar.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");
        btnGuardar.setOnAction(e -> guardarReservacion());

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.getStyleClass().add("back-button");
        btnCancelar.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");
        btnCancelar.setOnAction(e -> new Inicio());

        botones.getChildren().addAll(btnGuardar, btnCancelar);

        formulario.getChildren().addAll(
                titulo,
                new Separator(),
                seccionCliente,
                gridCliente,
                new Separator(),
                seccionReserva,
                gridReserva,
                new Separator(),
                seccionMesa,
                contenedorMesa,
                botones
        );

        scrollPrincipal.setContent(formulario);
        root.setCenter(scrollPrincipal);
    }

    private void guardarReservacion() {
        if (tfNombre.getText().isEmpty() || tfTelefono.getText().isEmpty() || cbMesa.getValue() == null) {
            mostrarAlerta("Error", "Los campos marcados con * son obligatorios");
            return;
        }

        String mesaSeleccionadaStr = cbMesa.getValue();
        String numeroTexto = mesaSeleccionadaStr.split(" ")[1];
        int numeroMesa = Integer.parseInt(numeroTexto);

        MesaDAO mesaSeleccionada = null;
        for (MesaDAO mesa : mesasRegistradas) {
            if (mesa.getNumero() == numeroMesa) {
                mesaSeleccionada = mesa;
                break;
            }
        }

        if (mesaSeleccionada == null) {
            mostrarAlerta("Error", "No se encontró la mesa seleccionada");
            return;
        }

        if (spPersonas.getValue() > mesaSeleccionada.getCapacidad()) {
            mostrarAlerta("Error", "El número de personas excede la capacidad de la mesa seleccionada");
            return;
        }

        ClientesDAO cliente = new ClientesDAO();
        cliente.setNomCte(tfNombre.getText());
        cliente.setTelCte(tfTelefono.getText());
        cliente.setDireccion(tfDireccion.getText());
        cliente.setEmailCte(tfEmail.getText());
        cliente.INSERT();

        ReservacionDAO reservacion = new ReservacionDAO();
        reservacion.setIdCte(cliente.getIdCte());
        reservacion.setNomCte(tfNombre.getText());
        reservacion.setFecha(dpFecha.getValue().toString());
        reservacion.setHora(cbHora.getValue());
        reservacion.setPersonas(spPersonas.getValue());
        reservacion.setTelefono(tfTelefono.getText());
        reservacion.setNumeroMesa(numeroMesa); // <--- AQUÍ se guarda el número de mesa

        reservacion.INSERT();

        // Actualizar estado de la mesa
        mesaSeleccionada.setEstado("reservada");
        mesaSeleccionada.UPDATE();

        mostrarAlerta("Reservación Exitosa",
                "Reservación registrada correctamente para:\n" +
                        "Mesa: " + mesaSeleccionadaStr + "\n" +
                        "Fecha: " + dpFecha.getValue() + " a las " + cbHora.getValue() + "\n" +
                        "A nombre de: " + tfNombre.getText());

        new Inicio();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
