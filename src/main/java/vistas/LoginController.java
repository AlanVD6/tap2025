package vistas;

import com.example.modelos.EmpleadoDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class LoginController {
    private final Stage stage;

    public LoginController(Stage primaryStage) {
        this.stage = primaryStage;
        crearUI();
    }

    private void crearUI() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");

        Label lblTitulo = new Label("Sistema Restaurante");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField tfUsuario = new TextField();
        tfUsuario.setPromptText("Usuario");
        tfUsuario.setMaxWidth(250);

        PasswordField pfContraseña = new PasswordField();
        pfContraseña.setPromptText("Contraseña");
        pfContraseña.setMaxWidth(250);

        Button btnLogin = new Button("Ingresar");
        btnLogin.setDefaultButton(true);
        btnLogin.getStyleClass().addAll("btn", "btn-primary");
        btnLogin.setOnAction(e -> autenticarUsuario(tfUsuario.getText(), pfContraseña.getText()));

        root.getChildren().addAll(lblTitulo, tfUsuario, pfContraseña, btnLogin);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 350, 250);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setScene(scene);
        stage.setTitle("Login - Sistema Restaurante");
        stage.show();
    }

    private void autenticarUsuario(String usuario, String contraseña) {
        try {
            EmpleadoDAO empleado = EmpleadoDAO.autenticar(usuario, contraseña);

            if (empleado != null) {
                if (empleado.isEsAdmin()) {
                    new AdminPanel().show();
                } else {
                    new PantallaPrincipalRestaurante().show();
                }
                stage.close();
            } else {
                mostrarError("Credenciales incorrectas");
            }
        } catch (Exception e) {
            mostrarError("Error de conexión: " + e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Autenticación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}