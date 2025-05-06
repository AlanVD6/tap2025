package vistas;

import com.example.modelos.Mesa;
import com.example.modelos.MesaDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MesaView {
    private List<Mesa> mesas;
    private BorderPane root;

    public MesaView(BorderPane root) {
        this.root = root;
        initializeUI();
    }

    private void initializeUI() {
        root.getStyleClass().add("background-with-image");
        VBox mainContainer = createMainContainer();
        root.setCenter(mainContainer);
    }

    private VBox createMainContainer() {
        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().add("main-container");
        mainContainer.setSpacing(20);
        mainContainer.setAlignment(Pos.TOP_CENTER);

        Button btnRegresar = createBackButton();
        mesas = obtenerMesasDeBD();
        FlowPane contenedorMesas = createMesasContainer();
        ScrollPane scrollPane = createScrollPane(contenedorMesas);

        mainContainer.getChildren().addAll(btnRegresar, scrollPane);
        return mainContainer;
    }

    private Button createBackButton() {
        Button btnRegresar = new Button("Regresar");
        btnRegresar.getStyleClass().add("back-button");
        btnRegresar.setOnAction(e -> {
            root.getStyleClass().remove("background-with-image");
            new Inicio();
            ((Stage) root.getScene().getWindow()).close();
        });
        return btnRegresar;
    }

    private List<Mesa> obtenerMesasDeBD() {
        List<Mesa> listaMesas = new ArrayList<>();
        MesaDAO mesaDAO = new MesaDAO();

        for (MesaDAO mesaBD : mesaDAO.SELECT()) {
            Mesa mesa = new Mesa(mesaBD.getNumero());
            mesa.setCapacidad(mesaBD.getCapacidad());

            if ("ocupada".equalsIgnoreCase(mesaBD.getEstado())) {
                mesa.ocupar();
            } else if ("reservada".equalsIgnoreCase(mesaBD.getEstado())) {
                mesa.reservar();
            }

            listaMesas.add(mesa);
        }

        return listaMesas;
    }

    private FlowPane createMesasContainer() {
        FlowPane contenedorMesas = new FlowPane();
        contenedorMesas.setPadding(new Insets(20));
        contenedorMesas.setHgap(20);
        contenedorMesas.setVgap(20);
        contenedorMesas.setAlignment(Pos.CENTER);

        for (Mesa mesa : mesas) {
            contenedorMesas.getChildren().add(crearBotonMesa(mesa));
        }

        return contenedorMesas;
    }

    private VBox crearBotonMesa(Mesa mesa) {
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/Image/mesa.png")));
        img.setFitHeight(80);
        img.setFitWidth(80);
        img.getStyleClass().add("item-image");

        Text numero = new Text("Mesa " + mesa.getNumero());
        numero.getStyleClass().add("item-name");

        Text capacidad = new Text("Capacidad: " + mesa.getCapacidad());
        capacidad.getStyleClass().add("item-detail");

        VBox box = new VBox(img, numero, capacidad);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.getStyleClass().add("item-container");
        box.setMinWidth(120);
        box.setMinHeight(120);

        if (mesa.isOcupada()) {
            applyOccupiedStyle(box, "Ocupada");
        } else if (mesa.isReservada()) {
            applyReservedStyle(box, "Reservada");
        }

        box.setOnMouseClicked(e -> {
            if (!mesa.isOcupada() && !mesa.isReservada()) {
                mostrarDetalleMesa(mesa);
            }
        });

        return box;
    }

    private void applyOccupiedStyle(VBox box, String estado) {
        box.setDisable(true);
        box.setStyle("-fx-opacity: 0.6;");
        Text estadoText = new Text(estado);
        estadoText.getStyleClass().add("occupied-text");
        box.getChildren().add(estadoText);
    }

    private void applyReservedStyle(VBox box, String estado) {
        box.setDisable(true);
        box.setStyle("-fx-opacity: 0.6;");
        Text estadoText = new Text(estado);
        estadoText.getStyleClass().add("occupied-text");
        estadoText.setStyle("-fx-fill: #f39c12;");
        box.getChildren().add(estadoText);
    }

    private ScrollPane createScrollPane(FlowPane content) {
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setMinViewportWidth(800);
        return scrollPane;
    }

    private void mostrarDetalleMesa(Mesa mesa) {
        VBox detalle = new VBox();
        detalle.setPadding(new Insets(20));
        detalle.setSpacing(15);
        detalle.setAlignment(Pos.CENTER);
        detalle.getStyleClass().add("detail-container");

        ImageView img = createMesaImage(120, 120);
        Text numero = createMesaNumberText(mesa);
        Text capacidad = createCapacityText(mesa);

        Button btnSeleccionar = createSelectButton(mesa);
        Button btnReservar = createReserveButton(mesa);
        Button btnRegresar = createBackButton();

        detalle.getChildren().addAll(img, numero, capacidad, btnSeleccionar, btnReservar, btnRegresar);
        root.setCenter(detalle);
    }

    private ImageView createMesaImage(double height, double width) {
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/Image/mesa.png")));
        img.setFitHeight(height);
        img.setFitWidth(width);
        img.getStyleClass().add("item-image");
        return img;
    }

    private Text createMesaNumberText(Mesa mesa) {
        Text numero = new Text("Mesa " + mesa.getNumero());
        numero.getStyleClass().add("item-name");
        return numero;
    }

    private Text createCapacityText(Mesa mesa) {
        Text capacidad = new Text("Capacidad: " + mesa.getCapacidad() + " personas");
        capacidad.getStyleClass().add("item-detail");
        return capacidad;
    }

    private Button createSelectButton(Mesa mesa) {
        Button btnSeleccionar = new Button("Seleccionar Mesa");
        btnSeleccionar.getStyleClass().add("order-button");
        btnSeleccionar.setOnAction(e -> {
            updateMesaStatus(mesa, "ocupada");
            refreshView();
        });
        return btnSeleccionar;
    }

    private Button createReserveButton(Mesa mesa) {
        Button btnReservar = new Button("Reservar Mesa");
        btnReservar.getStyleClass().add("reserve-button");
        btnReservar.setOnAction(e -> {
            updateMesaStatus(mesa, "reservada");
            refreshView();
        });
        return btnReservar;
    }

    private void updateMesaStatus(Mesa mesa, String estado) {
        MesaDAO mesaDAO = new MesaDAO();
        mesaDAO.setNumero(mesa.getNumero());
        mesaDAO.setEstado(estado);

        // Usar el método específico para actualizar solo el estado
        mesaDAO.UPDATEestado();

        if ("ocupada".equals(estado)) {
            mesa.ocupar();
        } else if ("reservada".equals(estado)) {
            mesa.reservar();
        }
    }

    private void refreshView() {
        mesas = obtenerMesasDeBD();
        root.setCenter(createMainContainer());
    }
}