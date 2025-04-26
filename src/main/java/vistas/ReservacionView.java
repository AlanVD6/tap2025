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
    private ComboBox<String> cbHora;
    private ToggleGroup mesaToggleGroup;
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

        // Campos del cliente
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

        // Campos de fecha y hora
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

        // Selección de mesas
        Text tituloMesas = new Text("Seleccione una mesa:");
        tituloMesas.getStyleClass().add("item-description");

        FlowPane panelMesas = new FlowPane();
        panelMesas.setHgap(15);
        panelMesas.setVgap(15);
        panelMesas.setAlignment(Pos.CENTER);
        panelMesas.setPadding(new Insets(10));

        mesaToggleGroup = new ToggleGroup();
        for (Mesa mesa : todasLasMesas) {
            RadioButton rbMesa = new RadioButton("Mesa " + mesa.getNumero());
            rbMesa.setToggleGroup(mesaToggleGroup);
            rbMesa.setUserData(mesa);
            rbMesa.getStyleClass().add("radio-button-mesa");
            panelMesas.getChildren().add(rbMesa);
        }

        // Botones
        Button btnGuardar = new Button("Guardar Reservación");
        btnGuardar.getStyleClass().add("order-button");
        btnGuardar.setOnAction(e -> guardarReservacion());

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.getStyleClass().add("back-button");
        btnCancelar.setOnAction(e -> new Inicio());

        HBox botones = new HBox(15, btnGuardar, btnCancelar);
        botones.setAlignment(Pos.CENTER);

        // Agregar todo al formulario
        formulario.getChildren().addAll(
                titulo, gridCliente, gridReserva,
                tituloMesas, panelMesas, botones
        );

        scrollPrincipal.setContent(formulario);
        root.setCenter(scrollPrincipal);
    }

    private void guardarReservacion() {
        // Validar campos obligatorios
        if (tfNombre.getText().isEmpty() || tfTelefono.getText().isEmpty() ||
                mesaToggleGroup.getSelectedToggle() == null) {
            mostrarAlerta("Error", "Nombre, teléfono y mesa son campos obligatorios");
            return;
        }

        // Obtener la mesa seleccionada
        Mesa mesaSeleccionada = (Mesa) mesaToggleGroup.getSelectedToggle().getUserData();

        // Guardar cliente
        ClientesDAO cliente = new ClientesDAO();
        cliente.setNomCte(tfNombre.getText());
        cliente.setTelCte(tfTelefono.getText());
        cliente.setDireccion(tfDireccion.getText());
        cliente.setEmailCte(tfEmail.getText());
        cliente.INSERT();

        // Guardar reservación
        ReservacionDAO reservacion = new ReservacionDAO();
        reservacion.setNomCte(tfNombre.getText());
        reservacion.setFecha(dpFecha.getValue().toString());
        reservacion.setHora(cbHora.getValue());
        reservacion.setPersonas(spPersonas.getValue());
        reservacion.setTelefono(tfTelefono.getText());
        reservacion.setIdCte(cliente.getIdCte());
        reservacion.INSERT();

        mostrarAlerta("Éxito", "Reservación registrada correctamente para la Mesa " +
                mesaSeleccionada.getNumero());
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