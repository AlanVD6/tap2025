package vistas;

import com.example.modelos.InsumosDAO;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Insumos extends Stage {

    private Button btnGuardar;

    private TextField txtInsumo, txtCantidad;

    private VBox vBox;
    private InsumosDAO objC;
    private Scene escena;
    private TableView<InsumosDAO> tbvInsumos;

    public Insumos(TableView<InsumosDAO> tbvIns, InsumosDAO obj) {

        this.tbvInsumos = tbvIns;

        CrearUI();

        if (obj == null) {

            objC = new InsumosDAO();
        } else {

            objC = obj;

            txtInsumo.setText(objC.getInsumo());
            txtCantidad.setText(String.valueOf(objC.getCantidad()));

        }

        this.setTitle("Ingresar Insumos");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI(){

        vBox = new VBox();

        txtInsumo = new TextField();
        txtCantidad = new TextField();

        btnGuardar = new Button("Guardar");
        btnGuardar.setPrefWidth(300);
        btnGuardar.setOnAction(event -> {

            objC.setInsumo(txtInsumo.getText());

            try {
                int cantidad = Integer.parseInt(txtCantidad.getText());
                if (cantidad < 0) {

                    mostrarAlerta("Error de formato", "La cantidad de insumos no puede ser menor a 0.");
                    return;
                } else {

                    objC.setCantidad(cantidad);
                }

            } catch (NumberFormatException e) {

                mostrarAlerta("Error de formato", "La cantidad debe ser un número entero válido.");
                return;
            }

            if (objC.getIdIns() > 0) {

                objC.UPDATE();
            } else {

                objC.INSERT();
            }

            tbvInsumos.setItems(objC.SELECT());
            tbvInsumos.refresh();
            this.close();
        });

        vBox = new VBox(txtInsumo, txtCantidad, btnGuardar);
        escena = new Scene(vBox, 400, 300);
    }

    private void mostrarAlerta(String titulo, String mensaje) {

        Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}