package vistas;

import com.example.modelos.InsumosDAO;
import com.example.modelos.ProductoDAO;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Producto extends Stage {

    private Button btnGuardar;

    private TextField txtProducto, txtPrecio;

    private VBox vBox;
    private ProductoDAO objC;
    private Scene escena;
    private TableView<ProductoDAO> tbvProducto;

    public Producto(TableView<ProductoDAO> tbvProd, ProductoDAO obj){

        this.tbvProducto = tbvProd;

        CrearUI();

        if (obj == null) {

            objC = new ProductoDAO();
        } else {

            objC = obj;

            txtProducto.setText(objC.getProducto());
            txtPrecio.setText(String.valueOf(objC.getPrecio()));

        }

        this.setTitle("Ingresar Insumos");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI(){

        vBox = new VBox();

        txtProducto = new TextField();
        txtPrecio = new TextField();

        btnGuardar = new Button();
        btnGuardar.setOnAction(event -> {

            objC.setProducto(txtProducto.getText());
            try {
                float precio = Float.parseFloat(txtPrecio.getText());
                if (precio < 0) {

                    mostrarAlerta("Error de formato", "El costo de los insumos no puede ser menor a 0.");
                    return;
                } else {

                    objC.setPrecio(precio);
                }

            } catch (NumberFormatException e) {
                mostrarAlerta("Error de formato", "El costo debe ser un número decimal válido.");
                return;
            }

            if (objC.getIdProd() > 0) {

                objC.UPDATE();
            } else {

                objC.INSERT();
            }

            tbvProducto.setItems(objC.SELECT());
            tbvProducto.refresh();
            this.close();
        });
    }

    private void mostrarAlerta(String titulo, String mensaje) {

        Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
