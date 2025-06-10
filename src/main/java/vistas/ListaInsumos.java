package vistas;

import com.example.Componentes.ButtonCellInsumos;
import com.example.modelos.InsumosDAO;
import com.example.modelos.ProveedorDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ListaInsumos extends Stage {

    private ToolBar tlbMenu;
    private TableView<InsumosDAO> tbvInsumos;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;
    private ComboBox<ProveedorDAO> cbxProveedores;
    private ObservableList<ProveedorDAO> listaProveedores;

    public ListaInsumos(){
        listaProveedores = new ProveedorDAO().SELECT();
        CrearUI();
        this.setTitle("Lista de Insumos");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI(){
        tbvInsumos = new TableView<>();

        // Configuración del ComboBox de proveedores
        cbxProveedores = new ComboBox<>(listaProveedores);
        cbxProveedores.setPromptText("Seleccione proveedor");
        cbxProveedores.setConverter(new StringConverter<ProveedorDAO>() {
            @Override
            public String toString(ProveedorDAO proveedor) {
                return proveedor == null ? "" : proveedor.getRazonSocial();
            }

            @Override
            public ProveedorDAO fromString(String string) {
                return null;
            }
        });

        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> {
            ProveedorDAO proveedorSeleccionado = cbxProveedores.getSelectionModel().getSelectedItem();
            if(proveedorSeleccionado != null) {
                InsumosDAO nuevoInsumo = new InsumosDAO();
                nuevoInsumo.setIdProv(proveedorSeleccionado.getIdProv());
                new Insumos(tbvInsumos, nuevoInsumo);
                cbxProveedores.getSelectionModel().clearSelection();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("Proveedor no seleccionado");
                alert.setContentText("Por favor seleccione un proveedor antes de agregar un insumo");
                alert.showAndWait();
            }
        });

        ImageView imv = new ImageView(getClass().getResource("/Image/next.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);

        // Organizar controles en la barra de herramientas
        HBox hboxControles = new HBox(10);
        hboxControles.getChildren().addAll(
                new Label("Proveedor:"),
                cbxProveedores,
                btnAgregar
        );

        tlbMenu = new ToolBar(hboxControles);
        CreateTable();

        vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(tlbMenu, tbvInsumos);
        escena = new Scene(vBox, 900, 650);
    }

    public void CreateTable() {
        InsumosDAO objC = new InsumosDAO();

        TableColumn<InsumosDAO, String> tbcInsumo = new TableColumn<>("Insumo");
        tbcInsumo.setCellValueFactory(new PropertyValueFactory<>("insumo"));

        TableColumn<InsumosDAO, Float> tbcCantidad = new TableColumn<>("Cantidad");
        tbcCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<InsumosDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<InsumosDAO, String>, TableCell<InsumosDAO, String>>() {
            @Override
            public TableCell<InsumosDAO, String> call(TableColumn<InsumosDAO, String> param) {
                return new ButtonCellInsumos("Editar");
            }
        });

        TableColumn<InsumosDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<InsumosDAO, String>, TableCell<InsumosDAO, String>>() {
            @Override
            public TableCell<InsumosDAO, String> call(TableColumn<InsumosDAO, String> param) {
                return new ButtonCellInsumos("Eliminar");
            }
        });

        tbvInsumos.getColumns().addAll(tbcInsumo, tbcCantidad, tbcEditar, tbcEliminar);
        tbvInsumos.setItems(objC.SELECT());
    }
}