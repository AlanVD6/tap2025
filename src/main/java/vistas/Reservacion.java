package vistas;

import com.example.modelos.ReservacionDAO;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Reservacion extends Stage {

    private Button btnGuardar;

    private TextField txtNomCte, txtFecha, txtHora, txtPersonas, txtTelefono;

    private VBox vBox;
    private ReservacionDAO objC;
    private Scene escena;
    private TableView<ReservacionDAO> tbvReservacion;

    public Reservacion(TableView<ReservacionDAO> tbvRes, ReservacionDAO obj){

        this.tbvReservacion = tbvRes;

        CrearUI();

        if (obj == null) {

            objC = new ReservacionDAO();
        } else {

            objC = obj;

            txtNomCte.setText(objC.getNomCte());
            txtFecha.setText(objC.getFecha());
            txtHora.setText(objC.getHora());
            txtPersonas.setText(String.valueOf(objC.getPersonas()));
            txtTelefono.setText(objC.getTelefono());

        }

        this.setTitle("Registro de Reservaciones");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI(){

        vBox = new VBox();

        txtNomCte = new TextField();
        txtFecha = new TextField();
        txtHora = new TextField();
        txtPersonas = new TextField();
        txtTelefono = new TextField();

        btnGuardar = new Button();
        btnGuardar.setOnAction(event -> {

            objC.setNomCte(txtNomCte.getText());
            objC.setFecha(txtFecha.getText());
            objC.setHora(txtHora.getText());
            try {

                int personas = Integer.parseInt(txtPersonas.getText());
                if (personas < 1) {

                    mostrarAlerta("Error de entrada.", " El número de personas por mesa no puede ser menor a 1.");
                    return;
                } else {

                    objC.setPersonas(personas);
                }
            } catch (NumberFormatException e) {

                mostrarAlerta("Error de formato.", "El número de personas debe ser un número entero válido.");
                return;
            }
            objC.setTelefono(txtTelefono.getText());

            if (objC.getIdReserva() > 0) {

                objC.UPDATE();
            } else {

                objC.INSERT();
            }

            tbvReservacion.setItems(objC.SELECT());
            tbvReservacion.refresh();
            this.close();
        });

        vBox = new VBox(txtNomCte, txtFecha, txtHora, txtPersonas, txtTelefono, btnGuardar);
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
