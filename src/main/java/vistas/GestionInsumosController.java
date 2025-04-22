package vistas;

import com.example.modelos.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;

public class GestionInsumosController {

    @FXML private TableView<InsumoDAO> tvInsumos;
    @FXML private TableView<ProductoDAO> tvProductos;
    @FXML private TableView<InsumoDAO> tvInsumosProducto;

    @FXML
    public void initialize() {
        configurarTablas();
        cargarDatos();
    }

    private void configurarTablas() {
        // Configurar tabla de insumos
        TableColumn<InsumoDAO, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setStyle("-fx-alignment: CENTER_LEFT;");

        TableColumn<InsumoDAO, String> colMarca = new TableColumn<>("Marca");
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colMarca.setStyle("-fx-alignment: CENTER_LEFT;");

        TableColumn<InsumoDAO, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCantidad.setStyle("-fx-alignment: CENTER;");

        TableColumn<InsumoDAO, String> colProveedor = new TableColumn<>("Proveedor");
        colProveedor.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProveedor() != null ?
                        cellData.getValue().getProveedor().getRazonSocial() : "N/A"));
        colProveedor.setStyle("-fx-alignment: CENTER_LEFT;");

        tvInsumos.getColumns().addAll(colNombre, colMarca, colCantidad, colProveedor);

        // Configurar tabla de productos
        TableColumn<ProductoDAO, String> colProducto = new TableColumn<>("Producto");
        colProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colProducto.setStyle("-fx-alignment: CENTER_LEFT;");

        TableColumn<ProductoDAO, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombreCategoria()));
        colCategoria.setStyle("-fx-alignment: CENTER_LEFT;");

        tvProductos.getColumns().addAll(colProducto, colCategoria);

        // Configurar tabla de insumos por producto
        TableColumn<InsumoDAO, String> colInsumoNombre = new TableColumn<>("Insumo");
        colInsumoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<InsumoDAO, Integer> colInsumoCantidad = new TableColumn<>("Cant. Requerida");
        colInsumoCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        tvInsumosProducto.getColumns().addAll(colInsumoNombre, colInsumoCantidad);

        // Listeners para selección
        tvProductos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> cargarInsumosProducto(newSelection));
    }

    private void cargarDatos() {
        try {
            tvInsumos.setItems(InsumoDAO.obtenerTodos());
            tvProductos.setItems(ProductoDAO.obtenerTodos());
        } catch (SQLException e) {
            mostrarError("Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarInsumosProducto(ProductoDAO producto) {
        if (producto != null) {
            try {
                tvInsumosProducto.setItems(InsumoDAO.obtenerPorProducto(producto.getIdProd()));
            } catch (SQLException e) {
                mostrarError("Error al cargar insumos: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            tvInsumosProducto.getItems().clear();
        }
    }

    @FXML
    private void guardarRelaciones() {
        ProductoDAO producto = tvProductos.getSelectionModel().getSelectedItem();
        if (producto != null) {
            try {
                DetalleProductoDAO.actualizarInsumosProducto(
                        producto.getIdProd(),
                        tvInsumosProducto.getItems()
                );
                mostrarMensaje("Relaciones guardadas exitosamente");
                cargarInsumosProducto(producto); // Refrescar datos
            } catch (SQLException e) {
                mostrarError("Error al guardar: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            mostrarError("Seleccione un producto primero");
        }
    }

    @FXML
    private void agregarInsumo() {
        ProductoDAO producto = tvProductos.getSelectionModel().getSelectedItem();
        InsumoDAO insumo = tvInsumos.getSelectionModel().getSelectedItem();

        if (producto != null && insumo != null) {
            // Verificar si el insumo ya está en la lista
            boolean existe = false;
            for (InsumoDAO i : tvInsumosProducto.getItems()) {
                if (i.getIdInsumo() == insumo.getIdInsumo()) {
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                tvInsumosProducto.getItems().add(insumo);
            } else {
                mostrarError("Este insumo ya está agregado al producto");
            }
        } else {
            mostrarError("Seleccione un producto y un insumo");
        }
    }

    @FXML
    private void quitarInsumo() {
        InsumoDAO insumoSeleccionado = tvInsumosProducto.getSelectionModel().getSelectedItem();
        if (insumoSeleccionado != null) {
            tvInsumosProducto.getItems().remove(insumoSeleccionado);
        } else {
            mostrarError("Seleccione un insumo para quitar");
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }
}