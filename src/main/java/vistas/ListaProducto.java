package vistas;

import com.example.Componentes.ButtonCellInsumos;
import com.example.Componentes.ButtonCellProducto;
import com.example.modelos.InsumosDAO;
import com.example.modelos.ProductoDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaProducto extends Stage {

    private ToolBar tlbMenu;
    private TableView<ProductoDAO> tbvProducto;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaProducto(){

        CrearUI();
        this.setTitle("Lista de Productos");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI(){

        tbvProducto = new TableView<ProductoDAO>();

        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Producto(tbvProducto, null));

        ImageView imv = new ImageView(getClass().getResource("/Image/next.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);

        tlbMenu = new ToolBar(btnAgregar);

        CreateTable();

        vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(tlbMenu, tbvProducto);
        escena = new Scene(vBox, 900, 650);
    }

    public void CreateTable() {

        ProductoDAO objC = new ProductoDAO();

        TableColumn<ProductoDAO, String> tbcProducto = new TableColumn<>("Producto");
        tbcProducto.setCellValueFactory(new PropertyValueFactory<>("Producto"));

        TableColumn<ProductoDAO, String> tbcPrecio = new TableColumn<>("Precio");

        TableColumn<ProductoDAO, String> tbcCosto = new TableColumn<>("Costo");

        TableColumn<ProductoDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<ProductoDAO, String>, TableCell<ProductoDAO, String>>() {
            @Override
            public TableCell<ProductoDAO, String> call(TableColumn<ProductoDAO, String> param) {
                return new ButtonCellProducto("Editar");
            }
        });

        TableColumn<ProductoDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ProductoDAO, String>, TableCell<ProductoDAO, String>>() {
            @Override
            public TableCell<ProductoDAO, String> call(TableColumn<ProductoDAO, String> param) {
                return new ButtonCellProducto("Eliminar");
            }
        });

        tbvProducto.getColumns().addAll(tbcProducto, tbcPrecio, tbcCosto, tbcEditar, tbcEliminar);
        tbvProducto.setItems(objC.SELECT());
    }
}
