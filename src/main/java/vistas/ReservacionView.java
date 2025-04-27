package vistas;

import com.example.modelos.ClientesDAO;
import com.example.modelos.Mesa;
import com.example.modelos.ReservacionDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservacionView {
    private BorderPane root;
    private TextField tfNombre, tfTelefono, tfDireccion, tfEmail;
    private DatePicker dpFecha;
    private Spinner<Integer> spPersonas;
    private ComboBox<String> cbHora, cbMesa; // Cambiado de RadioButtons a ComboBox
    private List<Mesa> todasLasMesas;

    public ReservacionView(BorderPane root) {
        this.root = root;
        this.todasLasMesas = crearTodasLasMesas();
        mostrarFormularioReservacion();
    }

    private List<Mesa> crearTodasLasMesas() {
        List<Mesa> mesas = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            mesas.add(new Mesa(i));
        }
        return mesas;
    }

    private void mostrarFormularioReservacion() {
        // Contenedor principal con ScrollPane
        ScrollPane scrollPrincipal = new ScrollPane();
        scrollPrincipal.setFitToWidth(true);
        scrollPrincipal.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox formulario = new VBox(15);
        formulario.setPadding(new Insets(20));
        formulario.setAlignment(Pos.TOP_CENTER);
        formulario.getStyleClass().add("detail-container");

        Text titulo = new Text("Nueva Reservación");
        titulo.getStyleClass().add("item-name");

        // Campos del cliente con labels descriptivos
        GridPane gridCliente = new GridPane();
        gridCliente.setHgap(10);
        gridCliente.setVgap(10);
        gridCliente.setAlignment(Pos.CENTER);

        tfNombre = new TextField();
        tfNombre.setPromptText("Nombre completo");
        tfTelefono = new TextField();
        tfTelefono.setPromptText("Teléfono");
        tfDireccion = new TextField();
        tfDireccion.setPromptText("Dirección");
        tfEmail = new TextField();
        tfEmail.setPromptText("Email");

        gridCliente.add(new Label("Nombre:"), 0, 0);
        gridCliente.add(tfNombre, 1, 0);
        gridCliente.add(new Label("Teléfono:"), 0, 1);
        gridCliente.add(tfTelefono, 1, 1);
        gridCliente.add(new Label("Dirección:"), 0, 2);
        gridCliente.add(tfDireccion, 1, 2);
        gridCliente.add(new Label("Email:"), 0, 3);
        gridCliente.add(tfEmail, 1, 3);

        // Campos de fecha y hora con labels descriptivos
        GridPane gridReserva = new GridPane();
        gridReserva.setHgap(10);
        gridReserva.setVgap(10);
        gridReserva.setAlignment(Pos.CENTER);

        dpFecha = new DatePicker();
        dpFecha.setValue(LocalDate.now().plusDays(1)); // Mañana por defecto

        cbHora = new ComboBox<>();
        cbHora.getItems().addAll(
                "10:00", "11:00", "12:00", "13:00", "14:00",
                "15:00", "16:00", "17:00", "18:00", "19:00",
                "20:00", "21:00", "22:00"
        );
        cbHora.setValue("19:00"); // Hora por defecto

        spPersonas = new Spinner<>(1, 20, 2);
        spPersonas.setEditable(true);

        gridReserva.add(new Label("Fecha:"), 0, 0);
        gridReserva.add(dpFecha, 1, 0);
        gridReserva.add(new Label("Hora:"), 0, 1);
        gridReserva.add(cbHora, 1, 1);
        gridReserva.add(new Label("Personas:"), 0, 2);
        gridReserva.add(spPersonas, 1, 2);

        // Selección de mesas como ComboBox con label descriptivo
        Text tituloMesas = new Text("Seleccione una mesa:");
        tituloMesas.getStyleClass().add("item-description");

        cbMesa = new ComboBox<>();
        for (Mesa mesa : todasLasMesas) {
            cbMesa.getItems().add("Mesa " + mesa.getNumero());
        }
        cbMesa.setPromptText("Seleccione una mesa");

        // Botones
        Button btnGuardar = new Button("Guardar Reservación");
        btnGuardar.getStyleClass().add("order-button");
        btnGuardar.setOnAction(e -> guardarReservacion());

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.getStyleClass().add("back-button");
        btnCancelar.setOnAction(e -> new Inicio());

        // Crear un HBox para poner los botones uno al lado del otro
        HBox botones = new HBox(10); // 10 píxeles de espacio entre botones
        botones.setAlignment(Pos.CENTER); // Centrar los botones
        botones.getChildren().addAll(btnGuardar, btnCancelar);

        // Agregar todo al formulario
        formulario.getChildren().addAll(
                titulo,
                new Label("Datos del Cliente:"),
                gridCliente,
                new Label("Detalles de la Reservación:"),
                gridReserva,
                tituloMesas,
                cbMesa,
                botones
        );

        scrollPrincipal.setContent(formulario);
        root.setCenter(scrollPrincipal);
    }

    private void guardarReservacion() {
        if (tfNombre.getText().isEmpty() || tfTelefono.getText().isEmpty() ||
                cbMesa.getValue() == null) {
            mostrarAlerta("Error", "Nombre, teléfono y mesa son campos obligatorios");
            return;
        }

        String mesaSeleccionadaStr = cbMesa.getValue();
        int numeroMesa = Integer.parseInt(mesaSeleccionadaStr.replace("Mesa ", ""));
        Mesa mesaSeleccionada = todasLasMesas.get(numeroMesa - 1);

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
        reservacion.INSERT();

        mesaSeleccionada.reservar();

        mostrarAlerta("Éxito", "Reservación registrada correctamente para la " + mesaSeleccionadaStr);
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

