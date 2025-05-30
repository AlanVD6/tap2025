package vistas;

import com.example.modelos.CategoriaDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Categoria extends Stage {

    private Button btnGuardar;
    private TextField txtCategoria;
    private VBox vBox;
    private CategoriaDAO objC;
    private Scene escena;
    private TableView<CategoriaDAO> tbvClientes;

    public Categoria(TableView<CategoriaDAO> tbvCat, CategoriaDAO obj) {
        this.tbvClientes = tbvCat;
        CrearUI();

        if (obj == null) {
            this.objC = new CategoriaDAO();
        } else {
            objC = obj;
            txtCategoria.setText(objC.getCategoria());
        }

        this.setTitle("Añadir categoría");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        vBox = new VBox(10);
        vBox.setPadding(new Insets(15));
        Label lblCategoria = new Label("Categoria");
        txtCategoria = new TextField();
        btnGuardar = new Button("Guardar");
        btnGuardar.setPrefWidth(200);
        btnGuardar.setOnAction(event -> {

            objC.setCategoria(txtCategoria.getText());

            if (objC.getIdCat() > 0) {

                objC.UPDATE();
            } else {

                objC.INSERT();
            }

            tbvClientes.setItems(objC.SELECT());
            tbvClientes.refresh();

            this.close();
        });

        vBox.getChildren().addAll(lblCategoria, txtCategoria, btnGuardar);
        escena = new Scene(vBox, 300, 150);
    }
}
