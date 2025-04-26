package vistas;

import com.example.modelos.Bebida;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BebidasView {

    public BebidasView(BorderPane root) {
        // Configurar el fondo con imagen
        root.getStyleClass().add("background-with-image");

        // Crear contenedor principal
        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().add("main-container");
        mainContainer.setSpacing(20);
        mainContainer.setAlignment(Pos.TOP_CENTER);

        // Botón de regresar
        Button btnRegresar = new Button("Regresar");
        btnRegresar.getStyleClass().add("back-button");
        btnRegresar.setOnAction(e -> {
            root.getStyleClass().remove("background-with-image");
            new Inicio();
            ((Stage) root.getScene().getWindow()).close();
        });

        ArrayList<Bebida> bebidas = cargarBebidas();
        FlowPane contenedorBebidas = new FlowPane();
        contenedorBebidas.setPadding(new Insets(20));
        contenedorBebidas.setHgap(20);
        contenedorBebidas.setVgap(20);
        contenedorBebidas.setAlignment(Pos.CENTER);

        for (Bebida b : bebidas) {
            contenedorBebidas.getChildren().add(crearBotonBebida(b, root));
        }

        // Configurar ScrollPane
        ScrollPane scrollPane = new ScrollPane(contenedorBebidas);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.getStyleClass().add("scroll-pane");

        mainContainer.getChildren().addAll(btnRegresar, scrollPane);
        root.setCenter(mainContainer);
    }

    private ArrayList<Bebida> cargarBebidas() {
        ArrayList<Bebida> lista = new ArrayList<>();
        lista.add(new Bebida("Agua Natural", 10.00, "Botella 600ml", "/Image/agua.png"));
        lista.add(new Bebida("Jugo de Naranja", 15.00, "Jugo natural 350ml", "/Image/jugo.png"));
        lista.add(new Bebida("Refresco Coca-Cola", 18.00, "Botella 600ml", "/Image/coca.png"));
        lista.add(new Bebida("Cerveza Clara", 25.00, "Botella 355ml", "/Image/cerveza.jpg"));
        lista.add(new Bebida("Té Helado", 15.00, "Vaso 500ml", "/Image/te.jpg"));
        lista.add(new Bebida("Café Americano", 20.00, "Taza 250ml", "/Image/cafe.jpg"));
        lista.add(new Bebida("Limonada", 18.00, "Vaso 500ml", "/Image/limonada.jpg"));
        lista.add(new Bebida("Agua Mineral", 12.00, "Botella 750ml", "/Image/agua_mineral.jpg"));
        return lista;
    }

    private VBox crearBotonBebida(Bebida b, BorderPane root) {
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream(b.getImagen())));
        img.setFitHeight(100);
        img.setFitWidth(100);
        img.getStyleClass().add("item-image");

        Text nombre = new Text(b.getNombre());
        nombre.getStyleClass().add("item-name");

        Text precio = new Text(String.format("$%.2f", b.getPrecio()));
        precio.getStyleClass().add("item-price");

        VBox box = new VBox(img, nombre, precio);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.setPadding(new Insets(10));
        box.getStyleClass().add("item-container");

        box.setOnMouseClicked(e -> mostrarDetalleBebida(b, root));

        return box;
    }

    private void mostrarDetalleBebida(Bebida b, BorderPane root) {
        VBox detalle = new VBox();
        detalle.setPadding(new Insets(20));
        detalle.setSpacing(15);
        detalle.setAlignment(Pos.CENTER);
        detalle.getStyleClass().add("detail-container");

        ImageView img = new ImageView(new Image(getClass().getResourceAsStream(b.getImagen())));
        img.setFitHeight(150);
        img.setFitWidth(150);
        img.getStyleClass().add("item-image");

        Text nombre = new Text(b.getNombre());
        nombre.getStyleClass().add("item-name");

        Text descripcion = new Text(b.getDescripcion());
        descripcion.getStyleClass().add("item-description");

        Text precioBase = new Text(String.format("Precio: $%.2f", b.getPrecio()));
        precioBase.getStyleClass().add("item-price");

        Text total = new Text(String.format("Total: $%.2f", b.getPrecio()));
        total.getStyleClass().add("item-price");

        Button btnMenos = new Button("-");
        btnMenos.getStyleClass().add("quantity-button");

        Button btnMas = new Button("+");
        btnMas.getStyleClass().add("quantity-button");

        Text cantidad = new Text("1");
        cantidad.getStyleClass().add("quantity-text");

        final int[] cantidadActual = {1};
        btnMenos.setOnAction(e -> {
            if (cantidadActual[0] > 1) {
                cantidadActual[0]--;
                cantidad.setText(String.valueOf(cantidadActual[0]));
                total.setText(String.format("Total: $%.2f", b.getPrecio() * cantidadActual[0]));
            }
        });

        btnMas.setOnAction(e -> {
            cantidadActual[0]++;
            cantidad.setText(String.valueOf(cantidadActual[0]));
            total.setText(String.format("Total: $%.2f", b.getPrecio() * cantidadActual[0]));
        });

        HBox controles = new HBox(btnMenos, cantidad, btnMas);
        controles.getStyleClass().add("quantity-controls");
        controles.setAlignment(Pos.CENTER);

        Button btnOrdenar = new Button("Ordenar");
        btnOrdenar.getStyleClass().add("order-button");
        btnOrdenar.setOnAction(e -> {
            System.out.println("Bebida ordenada: " + b.getNombre() + " x" + cantidadActual[0]);
        });

        Button btnRegresar = new Button("Regresar");
        btnRegresar.getStyleClass().add("back-button");
        btnRegresar.setOnAction(e -> {
            root.setCenter(createMainView(root));
        });

        detalle.getChildren().addAll(img, nombre, descripcion, precioBase, controles, total, btnOrdenar, btnRegresar);
        root.setCenter(detalle);
    }

    private VBox createMainView(BorderPane root) {
        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().add("main-container");
        mainContainer.setSpacing(20);
        mainContainer.setAlignment(Pos.TOP_CENTER);

        Button btnRegresar = new Button("Regresar");
        btnRegresar.getStyleClass().add("back-button");
        btnRegresar.setOnAction(e -> {
            root.getStyleClass().remove("background-with-image");
            new Inicio();
            ((Stage) root.getScene().getWindow()).close();
        });

        ArrayList<Bebida> bebidas = cargarBebidas();
        FlowPane contenedorBebidas = new FlowPane();
        contenedorBebidas.setPadding(new Insets(20));
        contenedorBebidas.setHgap(20);
        contenedorBebidas.setVgap(20);
        contenedorBebidas.setAlignment(Pos.CENTER);

        for (Bebida b : bebidas) {
            contenedorBebidas.getChildren().add(crearBotonBebida(b, root));
        }

        // Configurar ScrollPane
        ScrollPane scrollPane = new ScrollPane(contenedorBebidas);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.getStyleClass().add("scroll-pane");

        mainContainer.getChildren().addAll(btnRegresar, scrollPane);
        return mainContainer;
    }
}