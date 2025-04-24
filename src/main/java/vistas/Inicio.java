package vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Inicio {

    public Inicio() {
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        HBox topBar = new HBox(20); // Espacio entre botones
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getStyleClass().add("top-bar");

        // Crear los botones con imagen
        Button btnPlatillos = crearBotonConImagen("Platillos", "/Image/plato.png");
        Button btnBebidas = crearBotonConImagen("Bebidas", "/Image/bebidas.png");
        Button btnMesa = crearBotonConImagen("Mesa", "/Image/mesa.png");
        Button btnTicket = crearBotonConImagen("Ticket", "/Image/ticket.png");
        Button btnAdmin = crearBotonConImagen("Admin", "/Image/admin.png");

        btnPlatillos.setOnAction(e -> new PlatillosView(root));
        btnBebidas.setOnAction(e -> new BebidasView(root));
        btnMesa.setOnAction(e -> new MesaView(root));
        btnAdmin.setOnAction(e -> new LoginView());

        // Agregar botones a la barra superior
        topBar.getChildren().addAll(btnPlatillos, btnBebidas, btnMesa, btnTicket, btnAdmin);

        // Agregar la barra al layout principal
        root.setTop(topBar);

        // Crear el área central con la imagen del restaurante
        StackPane centerPane = new StackPane();
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setPadding(new Insets(20));

        // Cargar la imagen del restaurante
        ImageView restaurantImage = new ImageView(new Image(getClass().getResourceAsStream("/Image/restaurante.png")));
        restaurantImage.setPreserveRatio(true);
        restaurantImage.setFitWidth(500); // Ajustar según necesidad

        centerPane.getChildren().add(restaurantImage);
        root.setCenter(centerPane);

        Scene escena = new Scene(root, 1000, 700);
        escena.getStylesheets().add(getClass().getResource("/styles/estilo.css").toExternalForm());

        stage.setScene(escena);
        stage.setTitle("Interfaz Restaurante");
        stage.show();
    }

    private Button crearBotonConImagen(String texto, String rutaImagen) {
        Image img = new Image(getClass().getResourceAsStream(rutaImagen));
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);

        Button boton = new Button(texto, imageView);
        boton.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        boton.getStyleClass().add("button");
        return boton;
    }
}