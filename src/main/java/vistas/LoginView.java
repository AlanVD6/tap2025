package vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginView extends Stage {
    private TextField tfUsuario;
    private PasswordField pfContrasena;
    private Label errorLabel;

    public LoginView() {
        // Panel principal con fondo degradado
        VBox mainPane = new VBox(20);
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setPadding(new Insets(30));
        mainPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #2c3e50, #4ca1af);");

        // Imagen de usuario
        ImageView imgUser = new ImageView(new Image(getClass().getResourceAsStream("/Image/admin.png")));
        imgUser.setFitHeight(80);
        imgUser.setFitWidth(80);

        // Título
        Text titulo = new Text("Acceso de Supervisor");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setFill(Color.WHITE);

        // Panel de formulario
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new Insets(20, 40, 20, 40));
        grid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 10;");

        Label lbUsuario = new Label("Usuario:");
        lbUsuario.setFont(Font.font(14));
        grid.add(lbUsuario, 0, 0);

        tfUsuario = new TextField();
        tfUsuario.setPromptText("Ingrese su usuario");
        tfUsuario.setStyle("-fx-pref-width: 200px; -fx-padding: 8px;");
        grid.add(tfUsuario, 1, 0);

        Label lbContrasena = new Label("Contraseña:");
        lbContrasena.setFont(Font.font(14));
        grid.add(lbContrasena, 0, 1);

        pfContrasena = new PasswordField();
        pfContrasena.setPromptText("Ingrese su contraseña");
        pfContrasena.setStyle("-fx-pref-width: 200px; -fx-padding: 8px;");
        grid.add(pfContrasena, 1, 1);

        // Mensaje de error
        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font(12));
        grid.add(errorLabel, 0, 2, 2, 1);

        // Botones
        HBox hbBtn = new HBox(15);
        hbBtn.setAlignment(Pos.CENTER);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 20px;");
        btnCancelar.setOnAction(e -> this.close());

        Button btnIngresar = new Button("Ingresar");
        btnIngresar.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 20px;");
        btnIngresar.setOnAction(e -> {
            if(validarCredenciales()) {
                new AdminView();
                this.close();
            } else {
                mostrarError("Credenciales incorrectas. Intente nuevamente.");
                limpiarCampos();
            }
        });

        hbBtn.getChildren().addAll(btnCancelar, btnIngresar);
        grid.add(hbBtn, 0, 3, 2, 1);

        // Agregar componentes al panel principal
        mainPane.getChildren().addAll(imgUser, titulo, grid);

        Scene scene = new Scene(mainPane, 450, 500);
        this.setScene(scene);
        this.setTitle("Acceso de Supervisor");
        this.setResizable(false);
        this.show();
    }

    private boolean validarCredenciales() {
        // Credenciales predeterminadas (usuario: admin, contraseña: admin123)
        return "admin".equals(tfUsuario.getText()) && "admin123".equals(pfContrasena.getText());
    }

    private void mostrarError(String mensaje) {
        errorLabel.setText(mensaje);
    }

    private void limpiarCampos() {
        tfUsuario.clear();
        pfContrasena.clear();
        tfUsuario.requestFocus();
    }
}