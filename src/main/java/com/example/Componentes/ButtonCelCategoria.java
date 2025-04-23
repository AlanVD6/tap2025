package com.example.Componentes;
import com.example.modelos.CategoriaDAO;
import vistas.Categoria;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import java.util.Optional;

public class ButtonCelCategoria extends TableCell<CategoriaDAO, String> {  // Añadida herencia

    private Button btnCelda;
    private String strLabelBtn;

    // Constructor corregido (nombre coincide con la clase)
    public ButtonCelCategoria(String label) {
        strLabelBtn = label;
        btnCelda = new Button(strLabelBtn);
        btnCelda.setOnAction(event -> {
            CategoriaDAO objC = getTableView().getItems().get(getIndex());
            if(strLabelBtn.equals("Editar")) {
                new Categoria(getTableView(), objC);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Mensaje del Sistema :)");
                alert.setContentText("¿Deseas eliminar el registro seleccionado?");
                Optional<ButtonType> opcion = alert.showAndWait();
                if(opcion.get() == ButtonType.OK) {
                    objC.DELETE();
                }
            }
            getTableView().setItems(objC.SELECT());
            getTableView().refresh();
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty) {
            this.setGraphic(btnCelda);
        } else {
            this.setGraphic(null);
        }
    }
}