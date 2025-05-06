package vistas;

import com.example.Componentes.ButtonCellMesas;
import com.example.modelos.MesaDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaMesas extends Stage {
    private TableView<MesaDAO> tbvMesas;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaMesas() {
        CrearUI();
        this.setTitle("Listado de Mesas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvMesas = new TableView<>();
        btnAgregar = new Button("Agregar Mesa");
        btnAgregar.setOnAction(event -> mostrarDialogoAgregarMesa());

        ToolBar tlbMenu = new ToolBar(btnAgregar);
        CreateTable();

        vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(tlbMenu, tbvMesas);
        escena = new Scene(vBox, 900, 650);
    }

    private void mostrarDialogoAgregarMesa() {
        Dialog<MesaDAO> dialog = new Dialog<>();
        dialog.setTitle("Agregar Nueva Mesa");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Configurar validación
        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);

        // Campos del formulario
        TextField txtNumero = new TextField();
        txtNumero.setPromptText("Número de mesa");
        TextField txtCapacidad = new TextField();
        txtCapacidad.setPromptText("Capacidad");

        // Validación en tiempo real
        txtNumero.textProperty().addListener((obs, oldVal, newVal) ->
                validarCampos(okButton, txtNumero, txtCapacidad));
        txtCapacidad.textProperty().addListener((obs, oldVal, newVal) ->
                validarCampos(okButton, txtNumero, txtCapacidad));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.add(new Label("Número:"), 0, 0);
        grid.add(txtNumero, 1, 0);
        grid.add(new Label("Capacidad:"), 0, 1);
        grid.add(txtCapacidad, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                MesaDAO nuevaMesa = new MesaDAO();
                nuevaMesa.setNumero(Integer.parseInt(txtNumero.getText()));
                nuevaMesa.setCapacidad(Integer.parseInt(txtCapacidad.getText()));
                nuevaMesa.setEstado("disponible");
                return nuevaMesa;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(mesa -> {
            mesa.INSERT();
            tbvMesas.setItems(mesa.SELECT());
        });
    }

    private void validarCampos(Button okButton, TextField txtNumero, TextField txtCapacidad) {
        boolean camposValidos = !txtNumero.getText().isEmpty() &&
                !txtCapacidad.getText().isEmpty() &&
                esNumero(txtNumero.getText()) &&
                esNumero(txtCapacidad.getText());

        okButton.setDisable(!camposValidos);
    }

    private boolean esNumero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void CreateTable() {
        MesaDAO objM = new MesaDAO();

        TableColumn<MesaDAO, Integer> tbcNumero = new TableColumn<>("Número");
        tbcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<MesaDAO, Integer> tbcCapacidad = new TableColumn<>("Capacidad");
        tbcCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));

        TableColumn<MesaDAO, String> tbcEstado = new TableColumn<>("Estado");
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        TableColumn<MesaDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellMesas("Eliminar"));

        tbvMesas.getColumns().addAll(tbcNumero, tbcCapacidad, tbcEstado, tbcEliminar);
        tbvMesas.setItems(objM.SELECT());
    }
}