package vistas;

import com.example.Componentes.ButtonCellEmpleado;
import com.example.modelos.EmpleadoDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
public class ListaEmpleado extends Stage {

    private ToolBar tlbMenu;
    private TableView<EmpleadoDAO> tbvEmpleado;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaEmpleado() {
        CrearUI();
        this.setTitle("Lista de Empleados");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvEmpleado = new TableView<>();

        btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(event -> new Empleado(tbvEmpleado, null));

        ImageView imv = new ImageView(getClass().getResource("/Image/next.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);

        tlbMenu = new ToolBar(btnAgregar);

        CreateTable();

        vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(tlbMenu, tbvEmpleado);

        escena = new Scene(vBox, 900, 650);
    }

    public void CreateTable() {
        EmpleadoDAO objC = new EmpleadoDAO();

        TableColumn<EmpleadoDAO, String> tbcNombre = new TableColumn<>("Nombre");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<EmpleadoDAO, String> tbcNacimiento = new TableColumn<>("Nacimiento");
        tbcNacimiento.setCellValueFactory(new PropertyValueFactory<>("nacimiento"));

        TableColumn<EmpleadoDAO, String> tbcCurp = new TableColumn<>("CURP");
        tbcCurp.setCellValueFactory(new PropertyValueFactory<>("CURP"));

        TableColumn<EmpleadoDAO, String> tbcTelefono = new TableColumn<>("Telefono");
        tbcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<EmpleadoDAO, Float> tbcSueldo = new TableColumn<>("Sueldo");
        tbcSueldo.setCellValueFactory(new PropertyValueFactory<>("sueldo"));

        TableColumn<EmpleadoDAO, String> tbcUsuario = new TableColumn<>("Usuario");
        tbcUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        TableColumn<EmpleadoDAO, String> tbcContrasena = new TableColumn<>("Contraseña");
        tbcContrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));

        TableColumn<EmpleadoDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCellEmpleado("Editar"));

        TableColumn<EmpleadoDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellEmpleado("Eliminar"));

        tbvEmpleado.getColumns().addAll(
                tbcNombre, tbcNacimiento, tbcCurp, tbcTelefono,
                tbcSueldo, tbcUsuario, tbcContrasena,tbcEliminar,tbcEditar
        );
        tbvEmpleado.setItems(objC.SELECT());

    }
}