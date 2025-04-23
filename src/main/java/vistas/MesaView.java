package vistas;

import com.example.modelos.Mesa;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MesaView {
    private List<Mesa> mesas;

    public MesaView(BorderPane root) {
        // Configurar el fondo
        root.getStyleClass().add("background-with-image");

        // Crear contenedor principal
        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().add("main-container");
        mainContainer.setSpacing(20);

        // Botón de regresar
        Button btnRegresar = new Button("Regresar");
        btnRegresar.getStyleClass().add("back-button");
        btnRegresar.setOnAction(e -> {
            root.getStyleClass().remove("background-with-image");
            new Inicio();
            ((Stage) root.getScene().getWindow()).close();
        });

        mesas = crearMesas();
        FlowPane contenedorMesas = new FlowPane();
        contenedorMesas.setPadding(new Insets(10));
        contenedorMesas.setHgap(15);
        contenedorMesas.setVgap(15);

        for (Mesa mesa : mesas) {
            contenedorMesas.getChildren().add(crearBotonMesa(mesa, root));
        }

        mainContainer.getChildren().addAll(btnRegresar, contenedorMesas);
        root.setCenter(mainContainer);
    }

    private List<Mesa> crearMesas() {
        List<Mesa> listaMesas = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            listaMesas.add(new Mesa(i));
        }
        return listaMesas;
    }

    private VBox crearBotonMesa(Mesa mesa, BorderPane root) {
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/Image/mesa.png")));
        img.setFitHeight(60);
        img.setFitWidth(60);
        img.getStyleClass().add("item-image");

        Text numero = new Text("Mesa " + mesa.getNumero());
        numero.getStyleClass().add("item-name");

        VBox box = new VBox(img, numero);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(5);
        box.getStyleClass().add("item-container");

        if (mesa.isOcupada()) {
            box.setStyle("-fx-opacity: 0.6;");
            numero.setText("Mesa " + mesa.getNumero() + " (Ocupada)");
        }

        box.setOnMouseClicked(e -> {
            if (!mesa.isOcupada()) {
                mostrarDetalleMesa(mesa, root);
            }
        });

        return box;
    }

    private void mostrarDetalleMesa(Mesa mesa, BorderPane root) {
        VBox detalle = new VBox();
        detalle.setPadding(new Insets(20));
        detalle.setSpacing(15);
        detalle.setAlignment(Pos.CENTER);
        detalle.getStyleClass().add("detail-container");

        ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/Image/mesa.png")));
        img.setFitHeight(120);
        img.setFitWidth(120);
        img.getStyleClass().add("item-image");

        Text numero = new Text("Mesa " + mesa.getNumero());
        numero.getStyleClass().add("item-name");

        Button btnSeleccionar = new Button("Seleccionar");
        btnSeleccionar.getStyleClass().add("order-button");
        btnSeleccionar.setOnAction(e -> {
            mesa.ocupar();
            root.setCenter(createMainView(root));
        });

        Button btnRegresar = new Button("Regresar");
        btnRegresar.getStyleClass().add("back-button");
        btnRegresar.setOnAction(e -> {
            root.setCenter(createMainView(root));
        });

        detalle.getChildren().addAll(img, numero, btnSeleccionar, btnRegresar);
        root.setCenter(detalle);
    }

    private VBox createMainView(BorderPane root) {
        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().add("main-container");
        mainContainer.setSpacing(20);

        Button btnRegresar = new Button("Regresar");
        btnRegresar.getStyleClass().add("back-button");
        btnRegresar.setOnAction(e -> {
            root.getStyleClass().remove("background-with-image");
            new Inicio();
            ((Stage) root.getScene().getWindow()).close();
        });

        FlowPane contenedorMesas = new FlowPane();
        contenedorMesas.setPadding(new Insets(10));
        contenedorMesas.setHgap(15);
        contenedorMesas.setVgap(15);

        for (Mesa mesa : mesas) {
            contenedorMesas.getChildren().add(crearBotonMesa(mesa, root));
        }

        mainContainer.getChildren().addAll(btnRegresar, contenedorMesas);
        return mainContainer;
    }
}