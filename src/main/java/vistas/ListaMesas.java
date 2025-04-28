package vistas;

import com.example.Componentes.ButtonCellMesas;
import com.example.modelos.MesaDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaMesas extends Stage {

    private ToolBar tlbMenu;
    private TableView<MesaDAO> tbvMesas;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaMesas() {
        CrearUI();
        this.setTitle("Listado de Mesas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvMesas = new TableView<>();

        btnAgregar = new Button("Agregar Mesa");
        btnAgregar.setOnAction(event -> {
            MesaDAO nuevaMesa = new MesaDAO();
            nuevaMesa.setNumero(0);
            nuevaMesa.setCapacidad(4);
            nuevaMesa.setEstado("Disponible");
            nuevaMesa.INSERT();
            tbvMesas.setItems(nuevaMesa.SELECT());
            tbvMesas.refresh();
        });

        ImageView imv = new ImageView(getClass().getResource("/Image/next.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);

        tlbMenu = new ToolBar(btnAgregar);

        CreateTable();

        vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(tlbMenu, tbvMesas);
        escena = new Scene(vBox, 900, 650);
    }

    private void CreateTable() {
        MesaDAO objM = new MesaDAO();

        TableColumn<MesaDAO, Integer> tbcNumero = new TableColumn<>("Número");
        tbcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<MesaDAO, Integer> tbcCapacidad = new TableColumn<>("Capacidad");
        tbcCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));

        TableColumn<MesaDAO, String> tbcEstado = new TableColumn<>("Estado");
        tbcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        TableColumn<MesaDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<MesaDAO, String>, TableCell<MesaDAO, String>>() {
            @Override
            public TableCell<MesaDAO, String> call(TableColumn<MesaDAO, String> param) {
                return new ButtonCellMesas("Editar");
            }
        });

        TableColumn<MesaDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<MesaDAO, String>, TableCell<MesaDAO, String>>() {
            @Override
            public TableCell<MesaDAO, String> call(TableColumn<MesaDAO, String> param) {
                return new ButtonCellMesas("Eliminar");
            }
        });

        tbvMesas.getColumns().addAll(tbcNumero, tbcCapacidad, tbcEstado, tbcEditar, tbcEliminar);
        tbvMesas.setItems(objM.SELECT());
    }
}
