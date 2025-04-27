package vistas;

import com.example.Componentes.ButtonCellInsumos;
import com.example.modelos.InsumosDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

        tlbMenu = new ToolBar();
        CreateTable();

        vBox = new VBox(tlbMenu, tbvInsumos);
        escena = new Scene(vBox, 800, 600);
    }

    public void CreateTable() {

        InsumosDAO objC = new InsumosDAO();

        TableColumn<InsumosDAO, String> tbcInsumos = new TableColumn<>("Insumos");

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

        tbvInsumos.getColumns().addAll(tbcInsumos, tbcEditar, tbcEliminar);
        tbvInsumos.setItems(objC.SELECT());
    }
}
