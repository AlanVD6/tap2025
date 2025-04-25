package vistas;

import com.example.Componentes.ButtonCellEmpleado;
import com.example.modelos.EmpleadoDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ListaEmpleado{

    private ToolBar tlbMenu;
    private TableView<EmpleadoDAO> tbvEmpleado;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaEmpleado() { CrearUI(); }

    private void CrearUI() {

        tbvEmpleado = new TableView<>();

        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Empleado(tbvEmpleado, null));

        ImageView imv = new ImageView(getClass().getResource("/Image/next.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);

        btnAgregar.setGraphic(imv);

        tlbMenu = new ToolBar();
        CreateTable();

        vBox = new VBox(tlbMenu, tbvEmpleado);
        escena = new Scene(vBox, 800, 600);

    }

    public void CreateTable() {

        EmpleadoDAO objC = new EmpleadoDAO();

        TableColumn<EmpleadoDAO, String> tbcEmpleado = new TableColumn<>("Empleado");

        TableColumn<EmpleadoDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<EmpleadoDAO, String>, TableCell<EmpleadoDAO, String>>() {
            @Override
            public TableCell<EmpleadoDAO, String> call(TableColumn<EmpleadoDAO, String> param) {
                return new ButtonCellEmpleado("Editar");
            }
        });

        TableColumn<EmpleadoDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEditar.setCellFactory(new Callback<TableColumn<EmpleadoDAO, String>, TableCell<EmpleadoDAO, String>>() {
            @Override
            public TableCell<EmpleadoDAO, String> call(TableColumn<EmpleadoDAO, String> param) {
                return new ButtonCellEmpleado("Eliminar");
            }
        });

        tbvEmpleado.getColumns().addAll(tbcEmpleado, tbcEditar, tbcEliminar);
        tbvEmpleado.setItems(objC.SELECT());
    }
}
