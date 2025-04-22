package vistas;

import com.example.modelos.Platillo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

    public class PlatillosView {

        private VBox contenedorPrincipal;
        private FlowPane listaPlatillos;
        private Scene escena;
        private ArrayList<Platillo> platillos;

        public PlatillosView(BorderPane root) {
            platillos = cargarPlatillos();
            listaPlatillos = new FlowPane();
            listaPlatillos.setPadding(new Insets(10));
            listaPlatillos.setHgap(15);
            listaPlatillos.setVgap(15);

            for (Platillo p : platillos) {
                listaPlatillos.getChildren().add(crearBotonPlatillo(p, root));
            }

            root.setCenter(listaPlatillos);
        }

        private ArrayList<Platillo> cargarPlatillos() {
            ArrayList<Platillo> lista = new ArrayList<>();
            lista.add(new Platillo("Milanesa de pollo", 50.00, "1 pz milanesa de pollo, 100gr papas fritas, 50gr ensalada", "/Image/milanesa.jpg"));
            lista.add(new Platillo("Chorizo en salsa", 40.00, "150gr de chorizo", "/Image/chorizo.jpg"));
            lista.add(new Platillo("Verduras salteadas", 35.00, "50gr zanahoria, papa, chayote, cebolla, jitomate", "/Image/verduras.jpg"));
            // Agrega los demás platillos de forma similar...
            return lista;
        }

        private VBox crearBotonPlatillo(Platillo p, BorderPane root) {
            ImageView img = new ImageView(new Image(getClass().getResourceAsStream(p.getImagen())));
            img.setFitHeight(80);
            img.setFitWidth(80);

            Text nombre = new Text(p.getNombre());
            Text precio = new Text(String.format("$%.2f", p.getPrecio()));

            VBox box = new VBox(img, nombre, precio);
            box.setAlignment(Pos.CENTER);
            box.setSpacing(5);
            box.setStyle("-fx-border-color: gray; -fx-border-radius: 10; -fx-padding: 5; -fx-background-radius: 10;");

            box.setOnMouseClicked(e -> mostrarDetallePlatillo(p, root));

            return box;
        }

        private void mostrarDetallePlatillo(Platillo p, BorderPane root) {
            VBox detalle = new VBox();
            detalle.setPadding(new Insets(20));
            detalle.setSpacing(15);
            detalle.setAlignment(Pos.CENTER);

            ImageView img = new ImageView(new Image(getClass().getResourceAsStream(p.getImagen())));
            img.setFitHeight(150);
            img.setFitWidth(150);

            Text nombre = new Text(p.getNombre());
            Text descripcion = new Text(p.getDescripcion());
            Text precioBase = new Text(String.format("Precio: $%.2f", p.getPrecio()));
            Text total = new Text(String.format("Total: $%.2f", p.getPrecio()));

            Button btnMenos = new Button("-");
            Button btnMas = new Button("+");
            Text cantidad = new Text("1");

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
            controles.setSpacing(10);
            controles.setAlignment(Pos.CENTER);

            detalle.getChildren().addAll(img, nombre, descripcion, precioBase, controles, total);

            root.setCenter(detalle);
        }
    }

