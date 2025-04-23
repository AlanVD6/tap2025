package vistas;

import com.example.modelos.CategoriaDAO;
import com.mysql.cj.xdevapi.Table;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Categoria {

    private Button btnGuardar;
    private TextField txtCategoria;
    private VBox vBox;
    private Categoria objC;
    private Scene escena;
    private TableView<CategoriaDAO> tbvClientes;

    public Cliente (TableView<CategoriaDAO> tbvCat, CategoriaDAO obj){

        this.tbvClientes = tbvCat;
        CrearUI();

        if (obj == null){

            new CategoriaDAO();
        } else {

            objC = obj;

            txtCategoria.setText(objC.getCategoria());
        }

        this.setTitle("Añadir categoría");
        this.setScene(escena);
        this.show();

    }
}
