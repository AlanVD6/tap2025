package vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Bienvenida extends Stage {

    public Bienvenida() {
        // Configurar el diseño principal
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #4ca1af);");

        // Crear contenedor para el contenido central
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(40));

        // Logo o imagen del restaurante
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/Image/restaurante.jpeg")));
        logo.setFitHeight(150);
        logo.setFitWidth(150);
        logo.setPreserveRatio(true);

        // Mensaje de bienvenida
        Text welcomeText = new Text("¡Bienvenido a RESTAURANTEC!");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);

        Text slogan = new Text("Donde cada bocado es una experiencia única\n"
                + "y cada visita un momento memorable.");
        slogan.setFont(Font.font("Arial", 16));
        slogan.setFill(Color.LIGHTGRAY);
        slogan.setTextAlignment(TextAlignment.CENTER);

        // Botones
        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button btnRegistrarse = new Button("Registrarse");
        btnRegistrarse.getStyleClass().add("login-button");
        btnRegistrarse.setPrefWidth(200);
        btnRegistrarse.setOnAction(e -> {
            new Cliente(null, null); // Abre el formulario de registro de clientes
            this.close();
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.getStyleClass().add("back-button");
        btnCancelar.setPrefWidth(200);
        btnCancelar.setOnAction(e -> this.close());

        buttonBox.getChildren().addAll(btnRegistrarse, btnCancelar);

        // Agregar elementos al contenedor central
        centerBox.getChildren().addAll(logo, welcomeText, slogan, buttonBox);

        // Configurar el layout
        root.setCenter(centerBox);

        // Crear la escena
        Scene scene = new Scene(root, 600, 500);

        // Cargar estilos CSS (asegúrate de tener el archivo estilo.css en tu proyecto)
        scene.getStylesheets().add(getClass().getResource("/styles/estilo.css").toExternalForm());

        // Configurar la ventana
        this.setScene(scene);
        this.setTitle("Bienvenido a RESTAURANTEC");
        this.setResizable(false);
        this.show();
    }
}