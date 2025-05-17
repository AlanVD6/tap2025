package vistas;

import com.example.modelos.EmpleadoDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

        if (obj == null) {

            objC = new EmpleadoDAO();
        } else {

            objC = obj;

            txtNombre.setText(objC.getNombre());
            txtNacimiento.setText(objC.getNacimiento());
            txtCURP.setText(objC.getCURP());
            txtTelefono.setText(objC.getTelefono());
            txtSueldo.setText(String.valueOf(objC.getSueldo()));
            txtUsuario.setText(objC.getUsuario());
            txtContrasena.setText(objC.getContrasena());
        }

        this.setTitle("Registrar Empleado");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {

        vBox = new VBox(10);
        vBox.setPadding(new Insets(15));

        Label lblNombre =new Label("Nombre Completo");
        txtNombre = new TextField();

        Label lblNacimiento =new Label("Fecha de Nacimiento");
        txtNacimiento = new TextField();

        Label lblCurp =new Label("Curp");
        txtCURP = new TextField();

        Label lblTelefono =new Label("Telefono");
        txtTelefono = new TextField();

        Label lblSueldo =new Label("Sueldo de Empleado");
        txtSueldo = new TextField();

        Label lblUsuario =new Label("Usuario");
        txtUsuario = new TextField();

        Label lblContrasena =new Label("Contraseña Usuario");
        txtContrasena = new TextField();

        btnGuardar = new Button("Guardar");
        btnGuardar.setPrefWidth(100);
        btnGuardar.setOnAction(event -> {

            objC.setNombre(txtNombre.getText());
            objC.setNacimiento(txtNacimiento.getText());
            objC.setCURP(txtCURP.getText());
            objC.setTelefono(txtTelefono.getText());
            try {
                float sueldo = Float.parseFloat(txtSueldo.getText());
                objC.setSueldo(sueldo);
            } catch (NumberFormatException e) {
                mostrarAlerta("Error de formato", "El sueldo debe ser un número decimal válido.");
                return;
            }
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

        vBox.getChildren().addAll(
                lblNombre, txtNombre,
                lblNacimiento, txtNacimiento,
                lblCurp, txtCURP,
                lblTelefono, txtTelefono,
                lblSueldo, txtSueldo,
                lblUsuario, txtUsuario,
                lblContrasena, txtContrasena,
                btnGuardar
        );
        escena = new Scene(vBox, 400, 500);
    }

    private void mostrarAlerta(String titulo, String mensaje) {

        Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
