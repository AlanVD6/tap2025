package vistas;

import com.example.modelos.ClientesDAO;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Cliente extends Stage {

    private Button btnGuardar;
    private TextField txtNomCte, txtDireccion, txtTelCte, txtEmail;
    private VBox vBox;
    private Scene escena;
    private ClientesDAO objC;
    private TableView<ClientesDAO> tbvClientes;

    public Cliente(TableView<ClientesDAO> tbvCte, ClientesDAO obj) {
        this.tbvClientes = tbvCte;
        CrearUI();
        if (obj == null) {
            objC = new ClientesDAO();
        } else {
            objC = obj;
            txtNomCte.setText(objC.getNomCte());
            txtDireccion.setText(objC.getDireccion());
            txtEmail.setText(objC.getEmailCte());
            txtTelCte.setText(objC.getTelCte());
        }
        this.setTitle("Registrar Cliente");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        Label lblNomCte = new Label("Nombre del Cliente:");
        txtNomCte = new TextField();

        Label lblDireccion = new Label("Dirección:");
        txtDireccion = new TextField();

        Label lblTelCte = new Label("Teléfono:");
        txtTelCte = new TextField();

        Label lblEmail = new Label("Email:");
        txtEmail = new TextField();

        btnGuardar = new Button("Guardar");

        btnGuardar.setOnAction(event -> {
            // Validar que no haya campos vacíos
            if (txtNomCte.getText().isEmpty() || txtDireccion.getText().isEmpty() ||
                    txtTelCte.getText().isEmpty() || txtEmail.getText().isEmpty()) {

                Alert alerta = new Alert(AlertType.WARNING);
                alerta.setTitle("Campos Vacíos");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor, llena todos los campos antes de guardar.");
                alerta.showAndWait();
                return;

            }

            if (!txtNomCte.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {

                Alert alerta = new Alert(AlertType.WARNING);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText("El nombre solo debe contener letras y espacios.");
                alerta.showAndWait();
                return;

            } else {

                objC.setNomCte(txtNomCte.getText());
            }

            objC.setDireccion(txtDireccion.getText());

            if (!txtTelCte.getText().matches("\\d+")) {
                Alert alerta = new Alert(AlertType.WARNING);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor ingrese un número de teléfono válido.");
                alerta.showAndWait();
                return;

            } else if (txtTelCte.getText().length() < 10) {

                Alert alerta = new Alert(AlertType.WARNING);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor ingrese un número de teléfono válido.");
                alerta.showAndWait();
                return;

            }  else {

                objC.setTelCte(txtTelCte.getText());
            }

            if (!txtEmail.getText().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {

                Alert alerta = new Alert(AlertType.WARNING);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText("Por favor ingrese un correo electrónico válido.");
                alerta.showAndWait();
                return;

            } else {

                objC.setEmailCte(txtEmail.getText());
            }


            if (objC.getIdCte() > 0)
                objC.UPDATE();
            else
                objC.INSERT();

            if (tbvClientes != null) {
                tbvClientes.setItems(objC.SELECT());
                tbvClientes.refresh();
            }
            new Inicio();
            this.close();
        });

        vBox = new VBox(10, lblNomCte, txtNomCte, lblDireccion, txtDireccion, lblTelCte, txtTelCte, lblEmail, txtEmail, btnGuardar);
        escena = new Scene(vBox, 400, 400);
    }
}
