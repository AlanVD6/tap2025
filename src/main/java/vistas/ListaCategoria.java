package vistas;

import com.example.Componentes.ButtonCelCategoria;
import com.example.modelos.CategoriaDAO;
import com.example.modelos.ClientesDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ListaCategoria {

    private ToolBar tlbMenu;
    private TableView<CategoriaDAO> tbvCategoria;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaCategoria() {
        CrearUI();
    }

    private void CrearUI() {
        tbvCategoria = new TableView<>();

        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Categoria(tbvCategoria, null));
        ImageView imv = new ImageView(getClass().getResource("/Image/next.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);

        btnAgregar.setGraphic(imv);

        tlbMenu = new ToolBar();
        CreateTable();

        vBox = new VBox(tlbMenu, tbvCategoria);
        escena = new Scene(vBox, 800, 600);
    }

    private void CreateTable() {
        CategoriaDAO objC = new CategoriaDAO();
        TableColumn<CategoriaDAO, String> tbcCategoria = new TableColumn<>("Categoria");

        TableColumn<CategoriaDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<CategoriaDAO, String>, TableCell<CategoriaDAO, String>>() {
            @Override
            public TableCell<CategoriaDAO, String> call(TableColumn<CategoriaDAO, String> param) {
                return new ButtonCelCategoria("Editar");
            }
        });
        tbvCategoria.getColumns().addAll(tbcCategoria, tbcEditar);
    }
}
