package vistas;

import com.example.modelos.DetalleOrdenDAO;
import com.example.modelos.OrdenDAO;
import com.example.modelos.Platillo;
import com.example.modelos.ProductoDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class PlatillosView {

    public static int IdOrden;

    public PlatillosView(BorderPane root) {
        // Configurar el fondo con imagen
        root.getStyleClass().add("background-with-image");

        // Crear contenedor principal
        VBox mainContainer = new VBox();
        mainContainer.getStyleClass().add("main-container");
        mainContainer.setSpacing(20);

        // Botón de regresar
        Button btnRegresar = new Button("Regresar");
        btnRegresar.getStyleClass().add("back-button");
        btnRegresar.setOnAction(e -> {
            // Limpiar el estilo de fondo al regresar
            root.getStyleClass().remove("background-with-image");
            new Inicio();
            ((Stage) root.getScene().getWindow()).close();
        });

        ArrayList<Platillo> platillos = cargarPlatillos();
        FlowPane listaPlatillos = new FlowPane();
        listaPlatillos.setPadding(new Insets(10));
        listaPlatillos.setHgap(15);
        listaPlatillos.setVgap(15);

        for (Platillo p : platillos) {
            listaPlatillos.getChildren().add(crearBotonPlatillo(p, root));
        }

        // Configurar ScrollPane
        ScrollPane scrollPane = new ScrollPane(listaPlatillos);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Solo scroll vertical
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        mainContainer.getChildren().addAll(btnRegresar, scrollPane);
        root.setCenter(mainContainer);
    }

    private ArrayList<Platillo> cargarPlatillos() {
        ArrayList<Platillo> lista = new ArrayList<>();
        lista.add(new Platillo("Milanesa de pollo", 50.00, "1 pz milanesa de pollo, 100gr papas fritas, 50gr ensalada", "/Image/milanesa.jpg"));
        lista.add(new Platillo("Chorizo en salsa", 40.00, "150gr de chorizo", "/Image/chorizo.jpg"));
        lista.add(new Platillo("Verduras salteadas", 35.00, "50gr zanahoria, papa, chayote, cebolla, jitomate", "/Image/verduras.jpg"));

        lista.add(new Platillo("Pasta Alfredo", 55.00, "Pasta con salsa alfredo y pollo", "/Image/pasta.jpg"));
        lista.add(new Platillo("Enchiladas rojas", 45.00, "Tortillas rellenas bañadas en salsa roja", "/Image/enchiladas.jpg"));
        lista.add(new Platillo("Hamburguesa clásica", 60.00, "Carne de res, queso, lechuga, tomate", "/Image/hamburguesa.jpg"));
        lista.add(new Platillo("Sopa de tortilla", 30.00, "Sopa con tiras de tortilla, aguacate y queso", "/Image/sopa.jpg"));
        lista.add(new Platillo("Pizza margarita", 70.00, "Pizza con salsa de tomate, mozzarella y albahaca", "/Image/pizza.jpg"));
        return lista;
    }

    private VBox crearBotonPlatillo(Platillo p, BorderPane root) {
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream(p.getImagen())));
        img.setFitHeight(80);
        img.setFitWidth(80);
        img.getStyleClass().add("item-image");

        Text nombre = new Text(p.getNombre());
        nombre.getStyleClass().add("item-name");

        Text precio = new Text(String.format("$%.2f", p.getPrecio()));
        precio.getStyleClass().add("item-price");

        VBox box = new VBox(img, nombre, precio);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(5);
        box.getStyleClass().add("item-container");

        box.setOnMouseClicked(e -> mostrarDetallePlatillo(p, root));

        return box;
    }

    private void mostrarDetallePlatillo(Platillo p, BorderPane root) {
        VBox detalle = new VBox();
        detalle.setPadding(new Insets(20));
        detalle.setSpacing(15);
        detalle.setAlignment(Pos.CENTER);
        detalle.getStyleClass().add("detail-container");

        ImageView img = new ImageView(new Image(getClass().getResourceAsStream(p.getImagen())));
        img.setFitHeight(150);
        img.setFitWidth(150);
        img.getStyleClass().add("item-image");

        Text nombre = new Text(p.getNombre());
        nombre.getStyleClass().add("item-name");

        Text descripcion = new Text(p.getDescripcion());
        descripcion.getStyleClass().add("item-description");

        Text precioBase = new Text(String.format("Precio: $%.2f", p.getPrecio()));
        precioBase.getStyleClass().add("item-price");

        Text total = new Text(String.format("Total: $%.2f", p.getPrecio()));
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
                total.setText(String.format("Total: $%.2f", p.getPrecio() * cantidadActual[0]));
            }
        });

        btnMas.setOnAction(e -> {
            cantidadActual[0]++;
            cantidad.setText(String.valueOf(cantidadActual[0]));
            total.setText(String.format("Total: $%.2f", p.getPrecio() * cantidadActual[0]));
        });

        HBox controles = new HBox(btnMenos, cantidad, btnMas);
        controles.getStyleClass().add("quantity-controls");

        Button btnOrdenar = new Button("Ordenar");
        btnOrdenar.getStyleClass().add("order-button");
        btnOrdenar.setOnAction(e -> {
            System.out.println("Platillo ordenado: " + p.getNombre() + " x" + cantidadActual[0]);
            int IdPlatillo = new ProductoDAO().IdProduct(p.getNombre());
            new DetalleOrdenDAO().INSERT(IdOrden,IdPlatillo,cantidadActual[0]);

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

        Button btnRegresar = new Button("Regresar");
        btnRegresar.getStyleClass().add("back-button");
        btnRegresar.setOnAction(e -> {
            root.getStyleClass().remove("background-with-image");
            new Inicio();
            ((Stage) root.getScene().getWindow()).close();
        });

        ArrayList<Platillo> platillos = cargarPlatillos();
        FlowPane listaPlatillos = new FlowPane();
        listaPlatillos.setPadding(new Insets(10));
        listaPlatillos.setHgap(15);
        listaPlatillos.setVgap(15);

        for (Platillo p : platillos) {
            listaPlatillos.getChildren().add(crearBotonPlatillo(p, root));
        }

        // Configurar ScrollPane para la vista principal también
        ScrollPane scrollPane = new ScrollPane(listaPlatillos);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        mainContainer.getChildren().addAll(btnRegresar, scrollPane);
        return mainContainer;
    }
}