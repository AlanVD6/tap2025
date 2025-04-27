package vistas;

import com.example.Componentes.ButtonCellInsumos;
import com.example.Componentes.ButtonCellOrden;
import com.example.modelos.InsumosDAO;
import com.example.modelos.OrdenDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaOrden extends Stage {

    private ToolBar tlbMenu;
    private TableView<OrdenDAO> tbvOrden;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaOrden(){

        CrearUI();
        this.setTitle("Agregar Órden");
        this.setScene(escena);
        this.show();

    }

    public void CrearUI(){

        tbvOrden = new TableView<>();

        btnAgregar = new Button();
        btnAgregar.setOnAction( event -> new Orden(tbvOrden, null));

        ImageView imv = new ImageView(getClass().getResource("/Image/next.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);

        btnAgregar.setGraphic(imv);

        tlbMenu = new ToolBar();
        CreateTable();

        vBox = new VBox(tlbMenu, tbvOrden);
        escena = new Scene(vBox, 800, 600);

    }

    public void CreateTable() {

        OrdenDAO objC = new OrdenDAO();

        TableColumn<OrdenDAO, String> tbcOrden = new TableColumn<>("Orden");

        TableColumn<OrdenDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<OrdenDAO, String>, TableCell<OrdenDAO, String>>() {
            @Override
            public TableCell<OrdenDAO, String> call(TableColumn<OrdenDAO, String> param) {
                return new ButtonCellOrden("Editar");
            }
        });

        TableColumn<OrdenDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<OrdenDAO, String>, TableCell<OrdenDAO, String>>() {
            @Override
            public TableCell<OrdenDAO, String> call(TableColumn<OrdenDAO, String> param) {
                return new ButtonCellOrden("Eliminar");
            }
        });

        tbvOrden.getColumns().addAll(tbcOrden, tbcEditar, tbcEliminar);
        tbvOrden.setItems(objC.SELECT());
    }
}
