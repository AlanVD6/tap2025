package vistas;

import com.example.modelos.InsumosDAO;
import com.example.modelos.OrdenDAO;
import com.google.protobuf.ValueOrBuilder;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Orden extends Stage {

    private Button btnGuardar;
    private TextField txtNoOrden, txtFecha, txtHora;

    private VBox vBox;
    private OrdenDAO objC;
    private Scene escena;
    private TableView<OrdenDAO> tbvOrden;

    public Orden(TableView<OrdenDAO> tbvIns, OrdenDAO obj) {

        this.tbvOrden = tbvIns;

        CrearUI();

        if (obj == null) {

            objC = new OrdenDAO();
        } else {

            objC = obj;

            txtNoOrden.setText(String.valueOf(objC.getNoOrden()));
            txtFecha.setText(objC.getFecha());
            txtHora.setText(objC.getHora());

        }

        this.setTitle("Ingresar Órden");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI(){

        vBox = new VBox();

        txtNoOrden = new TextField();
        txtFecha = new TextField();
        txtHora = new TextField();

        btnGuardar = new Button();
        btnGuardar.setOnAction(event -> {

            try {
                int noOrden = Integer.parseInt(txtNoOrden.getText());
                if (noOrden < 1) {

                    mostrarAlerta("Error de entrada", "El número de órden no puede ser menor a 1.");
                    return;
                } else {

                    objC.setNoOrden(noOrden);
                }

            } catch (NumberFormatException e) {

                mostrarAlerta("Error de formato", "El sueldo debe ser un número entero válido.");
                return;
            }
            objC.setFecha(txtFecha.getText());
            objC.setHora(txtHora.getText());

            if (objC.getIdOrd() > 0) {

                objC.UPDATE();

            } else {

                objC.INSERT();
            }

            tbvOrden.setItems(objC.SELECT());
            tbvOrden.refresh();
            this.close();

        });

        vBox = new VBox(txtNoOrden, txtFecha, txtHora, btnGuardar);
        escena = new Scene(vBox, 120, 150);
    }

    private void mostrarAlerta(String titulo, String mensaje) {

        Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
