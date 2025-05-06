package com.example.Componentes;

import com.example.modelos.MesaDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.util.Optional;

public class ButtonCellMesas extends TableCell<MesaDAO, String> {
    private final Button button;

    public ButtonCellMesas(String buttonType) {
        this.button = new Button(buttonType);
        configureButtonStyle();

        this.button.setOnAction(event -> handleButtonAction());
    }

    private void configureButtonStyle() {
        button.setStyle("-fx-font-size: 12px; -fx-padding: 5px 10px; " +
                "-fx-background-color: #e74c3c; -fx-text-fill: white;");

        button.setOnMouseEntered(e ->
                button.setStyle("-fx-font-size: 12px; -fx-padding: 5px 10px; " +
                        "-fx-background-color: #c0392b; -fx-text-fill: white;"));

        button.setOnMouseExited(e ->
                button.setStyle("-fx-font-size: 12px; -fx-padding: 5px 10px; " +
                        "-fx-background-color: #e74c3c; -fx-text-fill: white;"));
    }

    private void handleButtonAction() {
        MesaDAO mesa = getTableView().getItems().get(getIndex());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Eliminar mesa " + mesa.getNumero());
        alert.setContentText("¿Está seguro de que desea eliminar esta mesa?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            mesa.DELETE();
            getTableView().setItems(mesa.SELECT());
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(button);
        }
    }
}