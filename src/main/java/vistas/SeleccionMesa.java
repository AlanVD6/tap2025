package vistas;

import com.example.modelos.MesaDAO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.sql.SQLException;

public class SeleccionMesa extends Stage {
    private MesaDAO mesaSeleccionada;
    private TableView<MesaDAO> tvMesas;

    public SeleccionMesa() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f8f9fa;");

        Label lblTitulo = new Label("SELECCIONAR MESA");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #343a40;");

        tvMesas = new TableView<>();
        configurarTabla();

        Button btnSeleccionar = new Button("Seleccionar");
        btnSeleccionar.setStyle("-fx-font-size: 16px; -fx-background-color: #28a745; -fx-text-fill: white;");
        btnSeleccionar.setOnAction(e -> seleccionarMesa());

        root.getChildren().addAll(lblTitulo, tvMesas, btnSeleccionar);

        Scene scene = new Scene(root, 400, 500);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        this.setScene(scene);
        this.setTitle("Selección de Mesa");
    }

    private void configurarTabla() {
        TableColumn<MesaDAO, Integer> colNumero = new TableColumn<>("Número");
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNumero.setStyle("-fx-alignment: CENTER;");

        TableColumn<MesaDAO, Integer> colCapacidad = new TableColumn<>("Capacidad");
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        colCapacidad.setStyle("-fx-alignment: CENTER;");

        TableColumn<MesaDAO, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEstado.setStyle("-fx-alignment: CENTER;");

        tvMesas.getColumns().addAll(colNumero, colCapacidad, colEstado);

        try {
            tvMesas.setItems(MesaDAO.obtenerTodas());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar mesas: " + e.getMessage()).show();
        }
    }

    private void seleccionarMesa() {
        mesaSeleccionada = tvMesas.getSelectionModel().getSelectedItem();
        if (mesaSeleccionada != null) {
            this.close();
        } else {
            new Alert(Alert.AlertType.WARNING, "Seleccione una mesa").show();
        }
    }

    public MesaDAO getMesaSeleccionada() {
        return mesaSeleccionada;
    }
}