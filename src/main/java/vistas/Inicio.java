package vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Inicio {

    public Inicio() {
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        HBox topBar = new HBox(20); // Espacio entre botones
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #f0f0f0;");

        // Crear los botones con imagen
        Button btnPlatillos = crearBotonConImagen("Platillos", "/Image/plato.png");
        Button btnBebidas = crearBotonConImagen("Bebidas", "/Image/bebidas.png");
        Button btnMesa = crearBotonConImagen("Mesa", "/Image/mesa.png");
        Button btnTicket = crearBotonConImagen("Ticket", "/Image/ticket.png");
        Button btnAdmin = crearBotonConImagen("Admin", "/Image/administrador.png");

        btnPlatillos.setOnAction(e -> new PlatillosView(root));

        // Agregar botones a la barra superior
        topBar.getChildren().addAll(btnPlatillos, btnBebidas, btnMesa, btnTicket, btnAdmin);

        // Agregar la barra al layout principal
        root.setTop(topBar);

        Scene escena = new Scene(root, 800, 600);
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
        boton.setStyle("-fx-font-size: 14px;");
        return boton;
    }
}


