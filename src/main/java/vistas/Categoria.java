package vistas;

import com.example.modelos.CategoriaDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Categoria extends Stage {  // Heredar de Stage para usar métodos como setTitle y show

    private Button btnGuardar;
    private TextField txtCategoria;
    private VBox vBox;
    private CategoriaDAO objC;  // Cambiado de Categoria a CategoriaDAO
    private Scene escena;
    private TableView<CategoriaDAO> tbvClientes;

    public Categoria(TableView<CategoriaDAO> tbvCat, CategoriaDAO obj) {  // Corregido el nombre del constructor
        this.tbvClientes = tbvCat;
        CrearUI();

        if (obj == null) {
            this.objC = new CategoriaDAO();  // Asignar el nuevo objeto a objC
        } else {
            objC = obj;
            txtCategoria.setText(objC.getCategoria());
        }

        this.setTitle("Añadir categoría");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        vBox = new VBox();
        txtCategoria = new TextField();
        btnGuardar = new Button("Guardar");
        btnGuardar.setPrefWidth(300);
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

        vBox.getChildren().addAll(txtCategoria, btnGuardar);
        escena = new Scene(vBox, 400, 300);
    }
}
