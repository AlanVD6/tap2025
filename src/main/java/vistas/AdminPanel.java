package vistas;

import com.example.modelos.Conexion;
import com.example.modelos.ProductoDAO;
import com.example.util.TxtGenerator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AdminPanel extends Stage {

    private TableView<ProductoDAO> tablaProductos;
    private Connection conexion;

    public AdminPanel() {
        try {
            // Establecer conexión con la base de datos
            conexion = Conexion.connection;
        } catch (Exception e) {
            mostrarAlertaError("Error de conexión", "No se pudo conectar a la base de datos: " + e.getMessage());
        }

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f8f9fa;");

        // 1. BARRA SUPERIOR
        HBox topBar = crearBarraSuperior();

        // 2. BARRA DE HERRAMIENTAS
        ToolBar toolBar = crearBarraHerramientas();

        // 3. TABLA DE PRODUCTOS
        tablaProductos = new TableView<>();
        configurarTabla();

        // 4. PANEL DE GRÁFICAS (Pestañas)
        TabPane tabGraficas = crearPanelGraficas();

        // ENSAMBLAR LAYOUT
        VBox centerBox = new VBox(10, tablaProductos, tabGraficas);
        root.setTop(new VBox(10, topBar, toolBar));
        root.setCenter(centerBox);

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("/Styles/Main.css").toExternalForm());

        this.setScene(scene);
        this.setTitle("Panel de Administración");
        this.show();
    }

    private HBox crearBarraSuperior() {
        HBox topBar = new HBox(10);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #343a40; -fx-padding: 15px;");

        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/Images/logo.png")));
        icon.setFitHeight(40);
        icon.setFitWidth(40);

        Label title = new Label("PANEL ADMINISTRATIVO");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        topBar.getChildren().addAll(icon, title);
        return topBar;
    }

    private ToolBar crearBarraHerramientas() {
        ToolBar toolBar = new ToolBar();
        toolBar.setStyle("-fx-background-color: #e9ecef; -fx-padding: 10px;");

        Button btnAdd = new Button("Agregar");
        btnAdd.setStyle("-fx-font-size: 14px; -fx-background-color: #28a745; -fx-text-fill: white;");
        btnAdd.setOnAction(e -> new ProductoEditor(tablaProductos, null));

        Button btnEdit = new Button("Editar");
        btnEdit.setStyle("-fx-font-size: 14px; -fx-background-color: #17a2b8; -fx-text-fill: white;");
        btnEdit.setOnAction(e -> {
            ProductoDAO seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                new ProductoEditor(tablaProductos, seleccionado);
            } else {
                mostrarAlertaError("Error", "Seleccione un producto para editar");
            }
        });

        Button btnDelete = new Button("Eliminar");
        btnDelete.setStyle("-fx-font-size: 14px; -fx-background-color: #dc3545; -fx-text-fill: white;");
        btnDelete.setOnAction(e -> {
            ProductoDAO seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                try {
                    seleccionado.DELETE();
                    actualizarDatos();
                } catch (SQLException ex) {
                    mostrarAlertaError("Error", "No se pudo eliminar el producto: " + ex.getMessage());
                }
            } else {
                mostrarAlertaError("Error", "Seleccione un producto para eliminar");
            }
        });

        Button btnPDF = new Button("Generar PDF");
        btnPDF.setStyle("-fx-font-size: 14px; -fx-background-color: #6c757d; -fx-text-fill: white;");
        btnPDF.setOnAction(e -> generarReportePDF());

        Button btnRefresh = new Button("Actualizar");
        btnRefresh.setStyle("-fx-font-size: 14px; -fx-background-color: #007bff; -fx-text-fill: white;");
        btnRefresh.setOnAction(e -> actualizarDatos());

        toolBar.getItems().addAll(btnAdd, btnEdit, btnDelete, new Separator(), btnPDF, btnRefresh);
        return toolBar;
    }

    private TabPane crearPanelGraficas() {
        TabPane tabGraficas = new TabPane();
        tabGraficas.setStyle("-fx-padding: 15px;");

        Tab tabProductos = new Tab("Productos Más Vendidos");
        tabProductos.setContent(crearGraficaProductos());
        tabProductos.setClosable(false);

        Tab tabVentas = new Tab("Ventas por Día");
        tabVentas.setContent(crearGraficaVentas());
        tabVentas.setClosable(false);

        Tab tabEmpleados = new Tab("Empleados Destacados");
        tabEmpleados.setContent(crearGraficaEmpleados());
        tabEmpleados.setClosable(false);

        tabGraficas.getTabs().addAll(tabProductos, tabVentas, tabEmpleados);
        return tabGraficas;
    }

    private void configurarTabla() {
        TableColumn<ProductoDAO, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setStyle("-fx-alignment: CENTER_LEFT;");

        TableColumn<ProductoDAO, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colPrecio.setStyle("-fx-alignment: CENTER_RIGHT;");

        TableColumn<ProductoDAO, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(cellData.getValue().getNombreCategoria());
            } catch (Exception e) {
                return new SimpleStringProperty("Error");
            }
        });
        colCategoria.setStyle("-fx-alignment: CENTER;");

        TableColumn<ProductoDAO, String> colImagen = new TableColumn<>("Imagen");
        colImagen.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ProductoDAO, String> call(TableColumn<ProductoDAO, String> param) {
                return new TableCell<>() {
                    private final ImageView imageView = new ImageView();
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            ProductoDAO prod = getTableView().getItems().get(getIndex());
                            if (prod.getImagen() != null) {
                                imageView.setImage(new Image(new ByteArrayInputStream(prod.getImagen())));
                                imageView.setFitHeight(50);
                                imageView.setFitWidth(50);
                                setGraphic(imageView);
                            }
                        }
                    }
                };
            }
        });
        colImagen.setStyle("-fx-alignment: CENTER;");

        tablaProductos.getColumns().addAll(colNombre, colPrecio, colCategoria, colImagen);
        tablaProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        actualizarDatos();
    }

    private void actualizarDatos() {
        tablaProductos.setItems(cargarProductos());
    }

    private ObservableList<ProductoDAO> cargarProductos() {
        ObservableList<ProductoDAO> productos = FXCollections.observableArrayList();
        String query = "SELECT p.*, c.idCat FROM producto p JOIN categoria c ON p.idCat = c.idCat";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ProductoDAO producto = new ProductoDAO(
                        rs.getInt("idProd"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("idCat")
                );
                producto.setImagen(rs.getBytes("imagen"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            mostrarAlertaError("Error de BD", "No se pudieron cargar los productos: " + e.getMessage());
        }

        return productos;
    }

    private BarChart<String, Number> crearGraficaProductos() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Productos");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Ventas");

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Top 5 Productos Más Vendidos");
        chart.setLegendVisible(false);
        chart.setStyle("-fx-font-size: 14px;");

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        String query = "SELECT p.nombre, COUNT(*) as ventas " +
                "FROM detalleorden d JOIN producto p ON d.idProd = p.idProd " +
                "GROUP BY p.idProd ORDER BY ventas DESC LIMIT 5";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(
                        rs.getString("nombre"),
                        rs.getInt("ventas")
                ));
            }
        } catch (SQLException e) {
            mostrarAlertaError("Error de BD", "No se pudieron cargar los datos de productos: " + e.getMessage());
        }

        chart.getData().add(series);
        return chart;
    }

    private LineChart<String, Number> crearGraficaVentas() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Fecha");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Total Ventas ($)");

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Ventas últimos 7 días");
        chart.setLegendVisible(false);
        chart.setStyle("-fx-font-size: 14px;");

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        String query = "SELECT DATE(fecha) as dia, SUM(total) as total " +
                "FROM orden " +
                "WHERE fecha >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                "GROUP BY dia ORDER BY dia";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(
                        rs.getDate("dia").toString(),
                        rs.getDouble("total")
                ));
            }
        } catch (SQLException e) {
            mostrarAlertaError("Error de BD", "No se pudieron cargar los datos de ventas: " + e.getMessage());
        }

        chart.getData().add(series);
        return chart;
    }

    private BarChart<String, Number> crearGraficaEmpleados() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Empleado");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Ventas ($)");

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Top 3 Empleados del Mes");
        chart.setLegendVisible(false);
        chart.setStyle("-fx-font-size: 14px;");

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        String query = "SELECT e.nombre, SUM(o.total) as total " +
                "FROM orden o JOIN empleado e ON o.idEmpleado = e.idEmpleado " +
                "WHERE MONTH(o.fecha) = MONTH(CURRENT_DATE()) " +
                "GROUP BY e.idEmpleado ORDER BY total DESC LIMIT 3";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(
                        rs.getString("nombre"),
                        rs.getDouble("total")
                ));
            }
        } catch (SQLException e) {
            mostrarAlertaError("Error de BD", "No se pudieron cargar los datos de empleados: " + e.getMessage());
        }

        chart.getData().add(series);
        return chart;
    }

    private void generarReportePDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialFileName("reporte_productos_" + LocalDate.now().toString() + ".pdf");

        File file = fileChooser.showSaveDialog(this);

        if (file != null) {
            try {
                TxtGenerator.generarReporteProductos(
                        tablaProductos.getItems(),
                        file.getAbsolutePath()
                );
                mostrarAlertaInfo("Éxito", "PDF generado correctamente en: " + file.getAbsolutePath());
            } catch (Exception e) {
                mostrarAlertaError("Error", "No se pudo generar el PDF: " + e.getMessage());
            }
        }
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertaInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void close() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.close();
    }
}