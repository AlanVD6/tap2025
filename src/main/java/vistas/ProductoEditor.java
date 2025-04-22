package vistas;

import com.example.modelos.ProductoDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Files;

    public class ProductoEditor extends Stage {

        public ProductoEditor(TableView<ProductoDAO> tabla, ProductoDAO productoExistente) {
            VBox root = new VBox(10);
            root.setPadding(new Insets(20));

            TextField txtNombre = new TextField();
            TextField txtPrecio = new TextField();
            Button btnImagen = new Button("Seleccionar Imagen");
            ImageView imgPreview = new ImageView();
            imgPreview.setFitHeight(100);
            imgPreview.setFitWidth(100);

            Button btnGuardar = new Button("Guardar");

            // Lógica para cargar imagen
            btnImagen.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
                );
                File file = fileChooser.showOpenDialog(this);
                if (file != null) {
                    try {
                        byte[] imagenBytes = Files.readAllBytes(file.toPath());
                        // Guardar bytes en el objeto producto
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            root.getChildren().addAll(
                    new Label("Nombre:"), txtNombre,
                    new Label("Precio:"), txtPrecio,
                    btnImagen, imgPreview, btnGuardar
            );

            this.setScene(new Scene(root, 300, 400));
            this.show();
        }
    }