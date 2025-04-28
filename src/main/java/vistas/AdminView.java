package vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminView extends Stage {
    public AdminView() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Barra superior con título y botón de salir
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #2c3e50;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        Text title = new Text("Panel de Administración");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnSalir = new Button("Salir");
        btnSalir.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSalir.setOnAction(e -> this.close());

        topBar.getChildren().addAll(title, spacer, btnSalir);
        root.setTop(topBar);

        // Contenido central con botones
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(30);
        grid.setPadding(new Insets(40));

        // Botones existentes
        Button btnEmpleados = crearBotonAdmin("Empleados", "/Image/empleados.png");
        btnEmpleados.setOnAction(e -> {
            ListaEmpleado listaempleado = new ListaEmpleado();
            listaempleado.show();
        });
        grid.add(btnEmpleados, 0, 0);

        Button btnCategoria = crearBotonAdmin("Categoría", "/Image/categoria.png");
        btnCategoria.setOnAction(e -> {
            ListaCategoria listacategoria = new ListaCategoria();
            listacategoria.show();
        });
        grid.add(btnCategoria, 1, 0);

        Button btnVentas = crearBotonAdmin("Ventas", "/Image/ventas.png");
        btnVentas.setOnAction(e -> {
            // Lógica de ventas
            System.out.println("Abriendo reporte de ventas");
        });
        grid.add(btnVentas, 2, 0);

        Button btnProveedor = crearBotonAdmin("Proveedor", "/Image/proveedor.png");
        btnProveedor.setOnAction(e -> {
            ListaProveedor listaproveedor = new ListaProveedor();
            listaproveedor.show();
        });
        grid.add(btnProveedor, 0, 1);

        Button btnInsumos = crearBotonAdmin("Insumos", "/Image/insumos.png");
        btnInsumos.setOnAction(e -> {
            ListaInsumos listainsumos = new ListaInsumos();
            listainsumos.show();
        });
        grid.add(btnInsumos, 1, 1);

        Button btnProducto = crearBotonAdmin("Producto", "/Image/producto.png");
        btnProducto.setOnAction(e -> {
            ListaProducto listaProducto = new ListaProducto();
            listaProducto.show();
        });
        grid.add(btnProducto, 2, 1);


        Button btnClientes = crearBotonAdmin("Clientes", "/Image/clientes.png");
        btnClientes.setOnAction(e -> {
            ListaClientes listaClientes = new ListaClientes();
            listaClientes.show();
        });
        grid.add(btnClientes, 0, 2);

        Button btnMesas = crearBotonAdmin("Mesas", "/Image/mesa.png");
        btnMesas.setOnAction(e -> {
            ListaMesas listamesas = new ListaMesas();
            listamesas.show();
        });
        grid.add(btnMesas, 1, 2);

        Button btnReservaciones = crearBotonAdmin("Reservaciones", "/Image/reservar.png");
        btnReservaciones.setOnAction(e -> {
            ListaReservacion listaReservacion = new ListaReservacion();
            listaReservacion.show();
        });
        grid.add(btnReservaciones, 2, 2);

        root.setCenter(grid);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/estilo.css").toExternalForm());
        this.setScene(scene);
        this.setTitle("Panel de Administración");
        this.show();
    }

    private Button crearBotonAdmin(String texto, String rutaImagen) {
        Image img = new Image(getClass().getResourceAsStream(rutaImagen));
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);

        Button boton = new Button(texto, imageView);
        boton.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        boton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; " +
                "-fx-padding: 15px 25px; -fx-background-color: #ffffff; " +
                "-fx-border-color: #bdc3c7; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        boton.setOnMouseEntered(e -> {
            boton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; " +
                    "-fx-padding: 15px 25px; -fx-background-color: #ecf0f1; " +
                    "-fx-border-color: #3498db; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        });

        boton.setOnMouseExited(e -> {
            boton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; " +
                    "-fx-padding: 15px 25px; -fx-background-color: #ffffff; " +
                    "-fx-border-color: #bdc3c7; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        });

        return boton;
    }
}
