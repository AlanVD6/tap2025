package vistas;

import com.example.Componentes.ButtonCellInsumos;
import com.example.modelos.InsumosDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaInsumos extends Stage {

    private ToolBar tlbMenu;
    private TableView<InsumosDAO> tbvInsumos;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaInsumos(){

        CrearUI();
        this.setTitle("Lista de Insumos");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI(){

        tbvInsumos = new TableView<>();

        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Insumos(tbvInsumos, null));

        ImageView imv = new ImageView(getClass().getResource("/Image/next.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);

        tlbMenu = new ToolBar(btnAgregar);

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
