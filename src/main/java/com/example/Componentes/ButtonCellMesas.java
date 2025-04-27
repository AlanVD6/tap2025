package com.example.Componentes;

import com.example.modelos.MesaDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.util.Optional;

public class ButtonCellMesas extends TableCell<MesaDAO, String> {

    private Button btnCelda;
    private String strLabelBtn;

    public ButtonCellMesas(String label) {
        strLabelBtn = label;
        btnCelda = new Button(strLabelBtn);
        btnCelda.setOnAction(event -> {
            MesaDAO objM = this.getTableView().getItems().get(this.getIndex());

            if (strLabelBtn.equals("Editar")) {
                objM.setEstado(objM.getEstado().equals("Disponible") ? "Ocupada" : "Disponible");
                objM.UPDATE();
            } else { // Eliminar
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje del Sistema :)");
                alert.setContentText("¿Deseas eliminar la mesa seleccionada?");
                Optional<ButtonType> opcion = alert.showAndWait();

                if (opcion.get() == ButtonType.OK) {
                    objM.DELETE();
                }
            }

            this.getTableView().setItems(objM.SELECT());
            this.getTableView().refresh();
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty)
            this.setGraphic(btnCelda);
    }
}

