package vistas;

import com.example.Componentes.ButtonCellProveedor;
import com.example.modelos.ProveedorDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaProveedor extends Stage {

    private ToolBar tlbMenu;
    private TableView<ProveedorDAO> tbvProveedor;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaProveedor(){

        CrearUI();
        this.setTitle("Lista de Proveedores");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI(){

        tbvProveedor = new TableView<>();

        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Proveedor(tbvProveedor, null));

        ImageView imv = new ImageView(getClass().getResource("/Image/next.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);

        tlbMenu = new ToolBar(btnAgregar);

        CreateTable();

        vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(tlbMenu, tbvProveedor);

        escena = new Scene(vBox, 900, 650);
    }

    public void CreateTable(){

        ProveedorDAO objC = new ProveedorDAO();

        TableColumn<ProveedorDAO, String> tbcProveedor = new TableColumn<>("Proveedor");
        tbcProveedor.setCellValueFactory(new PropertyValueFactory<>("razonSocial"));

        TableColumn<ProveedorDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<ProveedorDAO, String>, TableCell<ProveedorDAO, String>>() {
            @Override
            public TableCell<ProveedorDAO, String> call(TableColumn<ProveedorDAO, String> param) {
                return new ButtonCellProveedor("Editar");
            }
        });

        TableColumn<ProveedorDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ProveedorDAO, String>, TableCell<ProveedorDAO, String>>() {
            @Override
            public TableCell<ProveedorDAO, String> call(TableColumn<ProveedorDAO, String> param) {
                return new ButtonCellProveedor("Eliminar");
            }
        });

        tbvProveedor.getColumns().addAll(tbcProveedor, tbcEditar, tbcEliminar);
        tbvProveedor.setItems(objC.SELECT());    }
}
