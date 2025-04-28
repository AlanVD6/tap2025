package vistas;

import com.example.modelos.ClientesDAO;
import com.example.modelos.ProveedorDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Proveedor extends Stage {
    private Button btnGuardar;
    private TextField txtrazonSocial;
    private VBox vBox;
    private Scene escena;
    private ProveedorDAO objC;
    private TableView<ProveedorDAO> tbvProveedor;

    public Proveedor(TableView<ProveedorDAO> tbvProveedor, ProveedorDAO obj){
        this.tbvProveedor = tbvProveedor;
        CrearUI();
        if( obj == null ){
            objC = new ProveedorDAO();
        }else{
            objC = obj;
            txtrazonSocial.setText(objC.getRazonSocial());
        }
        this.setTitle("Registrar Proovedor Por Razon Social");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        txtrazonSocial = new TextField();
        btnGuardar = new Button("Guardar");
        btnGuardar.setPrefWidth(300);
        btnGuardar.setOnAction(event -> {
            objC.setRazonSocial(txtrazonSocial.getText());
            if( objC.getIdProv() > 0 )
                objC.UPDATE();
            else
                objC.INSERT();
            tbvProveedor.setItems(objC.SELECT());
            tbvProveedor.refresh();
            this.close();
        });
        vBox = new VBox(txtrazonSocial,btnGuardar);
        escena = new Scene(vBox,400,300);
    }
}
