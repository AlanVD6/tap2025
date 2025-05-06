package vistas;

import com.example.modelos.ProductoDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Producto extends Stage {

    private Button btnGuardar;

    private TextField txtProducto, txtPrecio, txtCosto, txtIdCat;

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
            txtCosto.setText(String.valueOf(objC.getCosto()));
            txtIdCat.setText(String.valueOf(objC.getIdCat()));

        }

        this.setTitle("Ingresar Productos");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI(){

        vBox = new VBox();

        Label lblProducto = new Label("Producto");
        txtProducto = new TextField();

        Label lblPrecio = new Label("Precio");
        txtPrecio = new TextField();

        Label lblCosto = new Label("Costo");
        txtCosto = new TextField();

        Label lblCat = new Label("ID Categoria");
        txtIdCat = new TextField();

        btnGuardar = new Button("Guardar");
        btnGuardar.setPrefWidth(100);
        btnGuardar.setOnAction(event -> {

            objC.setProducto(txtProducto.getText());
            try {
                float precio = Float.parseFloat(txtPrecio.getText());
                if (precio < 0) {

                    mostrarAlerta("Error de formato", "El precio de los productos no puede ser menor a 0.");
                    return;
                } else {

                    objC.setPrecio(precio);
                }

            } catch (NumberFormatException e) {
                mostrarAlerta("Error de formato", "El precio debe ser un número decimal válido.");
                return;
            }

            try {
                float costo = Float.parseFloat(txtCosto.getText());
                if (costo < 0) {

                    mostrarAlerta("Error de formato", "El costo de los productos no puede ser menor a 0.");
                    return;
                } else {

                    objC.setCosto(costo);
                }

            } catch (NumberFormatException e) {
                mostrarAlerta("Error de formato", "El costo debe ser un número decimal válido.");
                return;
            }

            try {
                int idCat = Integer.parseInt(txtIdCat.getText());
                if (idCat < 1) {

                    mostrarAlerta("Error de registro", "No existen registros negativos.");
                    return;
                } else {

                    objC.setIdCat(idCat);
                }

            } catch (NumberFormatException e) {
                mostrarAlerta("Error de formato", "El ID debe ser un número entero válido.");
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

        vBox = new VBox(lblProducto, txtProducto, lblPrecio, txtPrecio, lblCosto, txtCosto, lblCat, txtIdCat, btnGuardar);
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