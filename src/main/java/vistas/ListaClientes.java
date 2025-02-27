package vistas;

import com.modelos.ClientesDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ListaClientes extends Stage {

    private ToolBar tlbMenu;
    private TableView<ClientesDAO> tbvClientes;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;

    private void CrearUI() {
        ImageView imv = new ImageView(getClass().getResource("/Image/next.png").toString());
        btnAgregar = new Button();
        imv.setFitHeight(20);
        imv.setFitWidth(20);
        btnAgregar.setGraphic(imv);
        tlbMenu = new ToolBar(btnAgregar);
        tbvClientes = new TableView<>();
        vBox = new VBox(tlbMenu,tbvClientes);
        escena = new Scene(vBox,800,600);
    }
    public ListaClientes(){
        CrearUI();
        this.setTitle("Listado de Clientes :)");
        this.setScene(escena);
        this.show();
    }


}

