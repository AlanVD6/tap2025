package vistas;

import com.example.modelos.Bebida;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

        // Botón de regresar
        Button btnRegresar = new Button("Regresar");
        btnRegresar.getStyleClass().add("back-button");
        btnRegresar.setOnAction(e -> {
            // Limpiar el estilo de fondo al regresar
            root.getStyleClass().remove("background-with-image");
            new Inicio();
            ((Stage) root.getScene().getWindow()).close();
        });

        ArrayList<Bebida> bebidas = cargarBebidas();
        FlowPane contenedor = new FlowPane();
        contenedor.setPadding(new Insets(10));
        contenedor.setHgap(15);
        contenedor.setVgap(15);

        for (Bebida b : bebidas) {
            contenedor.getChildren().add(crearBotonBebida(b, root));
        }

        mainContainer.getChildren().addAll(btnRegresar, contenedor);
        root.setCenter(mainContainer);
    }

    private ArrayList<Bebida> cargarBebidas() {
        ArrayList<Bebida> lista = new ArrayList<>();
        lista.add(new Bebida("Agua Natural", 10.00, "Botella 600ml", "/Image/agua.png"));
        lista.add(new Bebida("Jugo de Naranja", 15.00, "Jugo natural 350ml", "/Image/jugo.png"));
        lista.add(new Bebida("Refresco Coca-Cola", 18.00, "Botella 600ml", "/Image/coca.png"));
        return lista;
    }

    private VBox crearBotonBebida(Bebida b, BorderPane root) {
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream(b.getImagen())));
        img.setFitHeight(80);
        img.setFitWidth(80);
        img.getStyleClass().add("item-image");

        Text nombre = new Text(b.getNombre());
        nombre.getStyleClass().add("item-name");

        Text precio = new Text(String.format("$%.2f", b.getPrecio()));
        precio.getStyleClass().add("item-price");

        VBox box = new VBox(img, nombre, precio);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(5);
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

        Button btnRegresar = new Button("Regresar");
        btnRegresar.getStyleClass().add("back-button");
        btnRegresar.setOnAction(e -> {
            root.getStyleClass().remove("background-with-image");
            new Inicio();
            ((Stage) root.getScene().getWindow()).close();
        });

        ArrayList<Bebida> bebidas = cargarBebidas();
        FlowPane contenedor = new FlowPane();
        contenedor.setPadding(new Insets(10));
        contenedor.setHgap(15);
        contenedor.setVgap(15);

        for (Bebida b : bebidas) {
            contenedor.getChildren().add(crearBotonBebida(b, root));
        }

        mainContainer.getChildren().addAll(btnRegresar, contenedor);
        return mainContainer;
    }
}

