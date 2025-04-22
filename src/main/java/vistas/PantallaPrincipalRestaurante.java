package vistas;

import com.example.Componentes.ProductoCard;
import com.example.modelos.CategoriaDAO;
import com.example.modelos.MesaDAO;
import com.example.modelos.OrdenDAO;
import com.example.modelos.ProductoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.sql.SQLException;

public class PantallaPrincipalRestaurante extends Stage {
    private ObservableList<ProductoDAO> productosSeleccionados = FXCollections.observableArrayList();
    private MesaDAO mesaSeleccionada;
    private TilePane productosPane;
    private Label lblMesa;
    private Circle indicadorMesa;
    private TableView<ProductoDAO> tablaOrden;
    private Label lblTotal;

    public PantallaPrincipalRestaurante() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f8f9fa;");

        // 1. ENCABEZADO
        HBox header = crearHeader();

        // 2. SECCIÓN CATEGORÍAS (IZQUIERDA)
        VBox categoriasBox = crearCategoriasBox();

        // 3. SECCIÓN PRODUCTOS (CENTRO)
        ScrollPane scrollProductos = crearScrollProductos();

        // 4. SECCIÓN ORDEN (DERECHA)
        VBox ordenBox = crearOrdenBox();

        // ENSAMBLAR LAYOUT
        root.setTop(header);
        root.setLeft(categoriasBox);
        root.setCenter(scrollProductos);
        root.setRight(ordenBox);

        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("/Styles/Main.css").toExternalForm());

        this.setScene(scene);
        this.setTitle("Sistema de Órdenes - Restaurante");
        this.setMaximized(true);
    }

    private HBox crearHeader() {
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #343a40; -fx-padding: 15px;");

        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/Images/img}.png")));
        logo.setFitHeight(50);
        logo.setFitWidth(50);

        Label titulo = new Label("RESTAURANTE TOUCH");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox mesaBox = new HBox(15);
        mesaBox.setAlignment(Pos.CENTER_RIGHT);
        indicadorMesa = new Circle(10, Color.TRANSPARENT);
        indicadorMesa.setStroke(Color.WHITE);
        lblMesa = new Label("Mesa: --");
        lblMesa.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
        mesaBox.getChildren().addAll(indicadorMesa, lblMesa);

        HBox.setHgrow(mesaBox, Priority.ALWAYS);
        header.getChildren().addAll(logo, titulo, mesaBox);

        return header;
    }

    private VBox crearCategoriasBox() {
        VBox categoriasBox = new VBox(15);
        categoriasBox.setPadding(new Insets(20));
        categoriasBox.setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        Label lblCategorias = new Label("CATEGORÍAS");
        lblCategorias.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #495057;");

        // Obtener categorías de la base de datos
        try {
            ObservableList<CategoriaDAO> categoriasDB = CategoriaDAO.obtenerTodas();
            String[] colores = {"#FF5722", "#2196F3", "#4CAF50", "#9C27B0", "#607D8B", "#795548"};
            int colorIndex = 0;

            for (CategoriaDAO categoria : categoriasDB) {
                Button btn = new Button(categoria.getNombre().toUpperCase());
                btn.setStyle(String.format(
                        "-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: %s; " +
                                "-fx-text-fill: white; -fx-padding: 12px; -fx-background-radius: 8;",
                        colores[colorIndex % colores.length]
                ));
                btn.setMaxWidth(Double.MAX_VALUE);
                btn.setOnAction(e -> cargarProductos(categoria.getNombre()));
                categoriasBox.getChildren().add(btn);
                colorIndex++;
            }
        } catch (SQLException e) {
            mostrarError("Error al cargar categorías: " + e.getMessage());
        }

        return categoriasBox;
    }

    private ScrollPane crearScrollProductos() {
        productosPane = new TilePane();
        productosPane.setHgap(20);
        productosPane.setVgap(20);
        productosPane.setPadding(new Insets(20));
        productosPane.setPrefColumns(3);

        ScrollPane scrollProductos = new ScrollPane(productosPane);
        scrollProductos.setFitToWidth(true);
        scrollProductos.setStyle("-fx-background: #f8f9fa; -fx-border-color: transparent;");

        return scrollProductos;
    }

    private VBox crearOrdenBox() {
        VBox ordenBox = new VBox(15);
        ordenBox.setPadding(new Insets(20));
        ordenBox.setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        Label lblOrden = new Label("TU ORDEN");
        lblOrden.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #343a40;");

        tablaOrden = new TableView<>();
        tablaOrden.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaOrden.setStyle("-fx-font-size: 14px;");

        TableColumn<ProductoDAO, String> colProducto = new TableColumn<>("Producto");
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colProducto.setStyle("-fx-alignment: CENTER_LEFT;");

        TableColumn<ProductoDAO, Integer> colCantidad = new TableColumn<>("Cant.");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCantidad.setStyle("-fx-alignment: CENTER;");

        TableColumn<ProductoDAO, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colPrecio.setStyle("-fx-alignment: CENTER_RIGHT;");

        tablaOrden.getColumns().addAll(colProducto, colCantidad, colPrecio);
        tablaOrden.setItems(productosSeleccionados);

        lblTotal = new Label("Total: $0.00");
        lblTotal.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Botón para asignar mesa
        Button btnAsignarMesa = new Button("ASIGNAR MESA");
        btnAsignarMesa.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #6c757d; -fx-text-fill: white; -fx-padding: 12px;");
        btnAsignarMesa.setOnAction(e -> asignarMesa());

        // Botón para finalizar orden
        Button btnFinalizar = new Button("FINALIZAR ORDEN");
        btnFinalizar.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-background-color: #28a745; -fx-text-fill: white; -fx-padding: 12px;");
        btnFinalizar.setOnAction(e -> guardarOrden());

        ordenBox.getChildren().addAll(lblOrden, tablaOrden, lblTotal, btnAsignarMesa, btnFinalizar);

        return ordenBox;
    }

    private void asignarMesa() {
        SeleccionMesa seleccion = new SeleccionMesa();
        seleccion.setOnHidden(evt -> {
            if (seleccion.getMesaSeleccionada() != null) {
                mesaSeleccionada = seleccion.getMesaSeleccionada();
                actualizarInfoMesa();
            }
        });
    }

    private void actualizarInfoMesa() {
        lblMesa.setText("Mesa: " + mesaSeleccionada.getNumero());
        indicadorMesa.setFill(mesaSeleccionada.getEstado().equals("Ocupada") ? Color.RED : Color.GREEN);
    }

    private void cargarProductos(String categoria) {
        productosPane.getChildren().clear();

        try {
            int idCategoria = CategoriaDAO.obtenerIdPorNombre(categoria);
            ObservableList<ProductoDAO> productos = ProductoDAO.SELECT_POR_CATEGORIA(idCategoria);

            for (ProductoDAO producto : productos) {
                ProductoCard card = new ProductoCard(producto);
                card.setPrefWidth(220);
                card.setOnMouseClicked(event -> agregarProductoAOrden(producto));
                productosPane.getChildren().add(card);
            }
        } catch (SQLException e) {
            mostrarError("Error al cargar productos: " + e.getMessage());
        }
    }

    private void agregarProductoAOrden(ProductoDAO producto) {
        if (mesaSeleccionada == null) {
            mostrarAdvertencia("Seleccione una mesa primero");
            return;
        }

        boolean encontrado = productosSeleccionados.stream()
                .anyMatch(p -> p.getIdProd() == producto.getIdProd());

        if (encontrado) {
            productosSeleccionados.stream()
                    .filter(p -> p.getIdProd() == producto.getIdProd())
                    .findFirst()
                    .ifPresent(p -> p.setCantidad(p.getCantidad() + 1));
        } else {
            ProductoDAO nuevoProducto = new ProductoDAO(
                    producto.getIdProd(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getIdCat()
            );
            nuevoProducto.setCantidad(1);
            productosSeleccionados.add(nuevoProducto);
        }

        tablaOrden.refresh();
        calcularTotal();
    }

    private void calcularTotal() {
        double total = productosSeleccionados.stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidad())
                .sum();

        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    private void guardarOrden() {
        if (mesaSeleccionada == null) {
            mostrarAdvertencia("Seleccione una mesa primero");
            return;
        }

        if (productosSeleccionados.isEmpty()) {
            mostrarAdvertencia("Agregue productos a la orden");
            return;
        }

        try {
            OrdenDAO nuevaOrden = new OrdenDAO();
            nuevaOrden.setMesa(mesaSeleccionada);

            if (nuevaOrden.guardarOrden(productosSeleccionados)) {
                mostrarInformacion("Orden guardada exitosamente");
                productosSeleccionados.clear();
                tablaOrden.refresh();
                calcularTotal();
                actualizarInfoMesa();
            }
        } catch (Exception e) {
            mostrarError("Error al guardar la orden: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInformacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}