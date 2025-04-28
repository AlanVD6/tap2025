package vistas;

import com.example.Componentes.ButtonCellReservacion;
import com.example.modelos.ReservacionDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaReservacion extends Stage {

    private ToolBar tlbMenu;
    private TableView<ReservacionDAO> tbvReservacion;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    public ListaReservacion(){

        CrearUI();
        this.setTitle("Lista de Reservaciones");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI(){

        tbvReservacion = new TableView<>();

        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> new Reservacion(tbvReservacion, null));

        ImageView imv = new ImageView(getClass().getResource("/Image/next.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);

        btnAgregar.setGraphic(imv);

        tlbMenu = new ToolBar();
        CreateTable();

        vBox = new VBox(tlbMenu, tbvReservacion);
        escena = new Scene(vBox, 800, 600);
    }

    public void CreateTable() {

        ReservacionDAO objC = new ReservacionDAO();

        TableColumn<ReservacionDAO, String> tbcNombreCte = new TableColumn<>("Nombre");
        tbcNombreCte.setCellValueFactory(new PropertyValueFactory<>("nomCte"));
        TableColumn<ReservacionDAO, String> tbcFecha = new TableColumn<>("Fecha");
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        TableColumn<ReservacionDAO, String> tbcHora = new TableColumn<>("Hora");
        tbcHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        TableColumn<ReservacionDAO, String> tbcPersonas = new TableColumn<>("Personas");
        tbcPersonas.setCellValueFactory(new PropertyValueFactory<>("personas"));
        TableColumn<ReservacionDAO, String> tbcTelefono = new TableColumn<>("Telefono");
        tbcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<ReservacionDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<ReservacionDAO, String>, TableCell<ReservacionDAO, String>>() {
            @Override
            public TableCell<ReservacionDAO, String> call(TableColumn<ReservacionDAO, String> param) {
                return new ButtonCellReservacion("Editar");
            }
        });

        TableColumn<ReservacionDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ReservacionDAO, String>, TableCell<ReservacionDAO, String>>() {
            @Override
            public TableCell<ReservacionDAO, String> call(TableColumn<ReservacionDAO, String> param) {
                return new ButtonCellReservacion("Eliminar");
            }
        });

        tbvReservacion.getColumns().addAll(tbcNombreCte, tbcFecha, tbcHora, tbcPersonas, tbcTelefono, tbcEditar, tbcEliminar);
        tbvReservacion.setItems(objC.SELECT());
    }
}
