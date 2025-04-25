package vistas;

import com.example.modelos.EmpleadoDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Stack;

public class Empleado extends Stage {

    private Button btnGuardar;
    private TextField txtNombre, txtNacimiento,  txtCURP, txtTelefono, txtSueldo, txtUsuario, txtContrasena;

    private VBox vBox;
    private EmpleadoDAO objC;
    private Scene escena;
    private TableView<EmpleadoDAO> tbvEmpleado;

    public Empleado(TableView<EmpleadoDAO> tbvEmp, EmpleadoDAO obj) {

        this.tbvEmpleado = tbvEmp;

        CrearUI();
    }

    private void CrearUI() {

        vBox = new VBox();

        txtNombre = new TextField();
        txtNacimiento = new TextField();
        txtCURP = new TextField();
        txtTelefono = new TextField();
        txtSueldo = new TextField();
        txtUsuario = new TextField();
        txtContrasena = new TextField();

        btnGuardar = new Button();
        btnGuardar.setOnAction(event -> {

            objC.setNombre(txtNombre.getText());
            objC.setNacimiento(txtNacimiento.getText());
            objC.setCURP(txtCURP.getText());
            objC.setTelefono(txtTelefono.getText());
            objC.setSueldo(Float.parseFloat(txtSueldo.getText()));
            objC.setUsuario(txtUsuario.getText());
            objC.setContrasena(txtContrasena.getText());

            if (objC.getIdEmp() > 0) {

                objC.UPDATE();
            } else {

                objC.INSERT();
            }

            tbvEmpleado.setItems(objC.SELECT());
            tbvEmpleado.refresh();
            this.close();

        });

        vBox = new VBox(txtNombre, txtNacimiento, txtCURP, txtTelefono, txtSueldo, txtUsuario, txtContrasena, btnGuardar);
        escena = new Scene(vBox, 120, 150);
    }

}
