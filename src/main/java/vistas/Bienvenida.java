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
        // Configuración del diseño principal
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #1a2a3a, #2c3e50);");

        // Contenedor central
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(30));
        centerBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.08); -fx-background-radius: 15;");

        // Logo del restaurante
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/Image/restaurante.jpeg")));
        logo.setFitHeight(120);
        logo.setFitWidth(120);
        logo.setPreserveRatio(true);
        logo.setEffect(new javafx.scene.effect.DropShadow(10, Color.rgb(0, 0, 0, 0.7)));

        // Título principal
        Text welcomeTitle = new Text("¡Bienvenido a RESTAURANTEC!");
        welcomeTitle.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 30));
        welcomeTitle.setFill(Color.WHITE);
        welcomeTitle.setTextAlignment(TextAlignment.CENTER);
        welcomeTitle.setEffect(new javafx.scene.effect.DropShadow(5, Color.BLACK));

        // Instrucciones detalladas
        VBox instructionsBox = new VBox(15);
        instructionsBox.setAlignment(Pos.CENTER_LEFT);
        instructionsBox.setMaxWidth(500);

        Text instructionsTitle = new Text("Cómo disfrutar de su experiencia:");
        instructionsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        instructionsTitle.setFill(Color.WHITE);

        Text step1 = createInstructionStep("1. Explora nuestro menú",
                "Selecciona el platillo que más te guste de nuestra variedad de opciones gourmet. "
                        + "Puedes ver los detalles de cada platillo haciendo clic en él.");

        Text step2 = createInstructionStep("2. Elige tu bebida favorita",
                "Acompaña tu comida con una de nuestras bebidas seleccionadas. "
                        + "Tenemos desde refrescos hasta cócteles exclusivos.");

        Text step3 = createInstructionStep("3. Selecciona tu mesa",
                "Escoge la mesa que prefieras de nuestro comedor. "
                        + "Esto lo encontraras en el apartado de mesas es importante que hagas esto primero.");

        Text step4 = createInstructionStep("4. ¿Deseas reservar?",
                "Si planeas visitarnos más tarde, ve al apartado de reservación, "
                        + "ingresa tus datos y asegura tu lugar para cuando nos visites.");

        Text finalMessage = new Text("¡Estamos emocionados de servirte y que disfrutes de una experiencia culinaria inolvidable!");
        finalMessage.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
        finalMessage.setFill(Color.LIGHTGOLDENRODYELLOW);
        finalMessage.setTextAlignment(TextAlignment.CENTER);
        finalMessage.setWrappingWidth(450);

        instructionsBox.getChildren().addAll(instructionsTitle, step1, step2, step3, step4);

        Button btnStart = new Button("¡Quiero Comenzar!");
        btnStart.getStyleClass().add("welcome-button");
        btnStart.setStyle("-fx-font-size: 18px; -fx-padding: 12px 40px; -fx-background-color: #27ae60;");
        btnStart.setOnAction(e -> {
            new Inicio();
            this.close();
        });

        btnStart.setOnMouseEntered(e -> {
            btnStart.setStyle("-fx-font-size: 18px; -fx-padding: 12px 40px; -fx-background-color: #2ecc71; "
                    + "-fx-effect: dropshadow(three-pass-box, rgba(46,204,113,0.8), 10, 0, 0, 2);");
        });
        btnStart.setOnMouseExited(e -> {
            btnStart.setStyle("-fx-font-size: 18px; -fx-padding: 12px 40px; -fx-background-color: #27ae60;");
        });
        btnStart.setOnMousePressed(e -> {
            btnStart.setStyle("-fx-font-size: 17px; -fx-padding: 12px 40px; -fx-background-color: #219653;");
        });

        centerBox.getChildren().addAll(logo, welcomeTitle, instructionsBox, finalMessage, btnStart);
        root.setCenter(centerBox);

        // Configurar la escena
        Scene scene = new Scene(root, 700, 650);
        scene.getStylesheets().add(getClass().getResource("/styles/estilo.css").toExternalForm());

        // Configurar la ventana
        this.setScene(scene);
        this.setTitle("Bienvenido a RESTAURANTEC");
        this.setResizable(false);
        this.show();
    }

    private Text createInstructionStep(String step, String description) {
        VBox stepBox = new VBox(5);

        Text stepText = new Text(step);
        stepText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        stepText.setFill(Color.LIGHTBLUE);

        Text descText = new Text(description);
        descText.setFont(Font.font("Arial", 14));
        descText.setFill(Color.WHITE);
        descText.setWrappingWidth(450);
        descText.setLineSpacing(2);

        stepBox.getChildren().addAll(stepText, descText);

        // Creamos un texto combinado para mantener la estructura simple
        Text combined = new Text(step + " - " + description);
        combined.setFont(descText.getFont());
        combined.setFill(descText.getFill());
        combined.setWrappingWidth(450);

        return combined;
    }
}