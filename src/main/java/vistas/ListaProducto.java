package vistas;

import com.example.Componentes.ButtonCellProducto;
import com.example.modelos.CategoriaDAO;
import com.example.modelos.ProductoDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ListaProducto extends Stage {

    private ToolBar tlbMenu;
    private TableView<ProductoDAO> tbvProducto;
    private VBox vBox;
    private Scene escena;
    private Button btnAgregar;
    private ComboBox<CategoriaDAO> cbxCategoria;
    private ObservableList<CategoriaDAO> categorias;

    public ListaProducto() {
        CrearUI();
        this.setTitle("Lista de Productos");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI() {
        tbvProducto = new TableView<>();

        // Configurar el ComboBox de categorías
        categorias = new CategoriaDAO().SELECT();
        cbxCategoria = new ComboBox<>(categorias);
        cbxCategoria.setPromptText("Seleccione categoría");

        // Configurar cómo se muestra la categoría en el ComboBox
        cbxCategoria.setConverter(new StringConverter<CategoriaDAO>() {
            @Override
            public String toString(CategoriaDAO categoria) {
                if (categoria == null) {
                    return "";
                }
                return categoria.getCategoria();
            }

            @Override
            public CategoriaDAO fromString(String string) {
                return null; // No se necesita para este caso
            }
        });

        btnAgregar = new Button();
        btnAgregar.setOnAction(event -> {
            CategoriaDAO categoriaSeleccionada = cbxCategoria.getSelectionModel().getSelectedItem();
            if (categoriaSeleccionada != null) {
                ProductoDAO nuevoProducto = new ProductoDAO();
                nuevoProducto.setIdCat(categoriaSeleccionada.getIdCat());
                new Producto(tbvProducto, nuevoProducto);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("Categoría no seleccionada");
                alert.setContentText("Por favor seleccione una categoría antes de agregar un producto.");
                alert.showAndWait();
            }
        });

        ImageView imv = new ImageView(getClass().getResource("/Image/next.png").toString());
        imv.setFitWidth(20);
        imv.setFitHeight(20);
        btnAgregar.setGraphic(imv);

        // Crear un HBox para organizar los controles en la barra de herramientas
        HBox hBoxControles = new HBox(10);
        hBoxControles.getChildren().addAll(
                new Label("Categoría:"),
                cbxCategoria,
                btnAgregar
        );

        tlbMenu = new ToolBar(hBoxControles);
        CreateTable();

        vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(tlbMenu, tbvProducto);
        escena = new Scene(vBox, 900, 650);
    }

    public void CreateTable() {
        ProductoDAO objC = new ProductoDAO();

        TableColumn<ProductoDAO, String> tbcProducto = new TableColumn<>("Producto");
        tbcProducto.setCellValueFactory(new PropertyValueFactory<>("producto"));

        TableColumn<ProductoDAO, String> tbcPrecio = new TableColumn<>("Precio");
        tbcPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<ProductoDAO, String> tbcCosto = new TableColumn<>("Costo");
        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        // Columna para mostrar el nombre de la categoría
        TableColumn<ProductoDAO, String> tbcCategoria = new TableColumn<>("Categoría");
        tbcCategoria.setCellValueFactory(cellData -> {
            int idCat = cellData.getValue().getIdCat();
            String nombreCategoria = categorias.stream()
                    .filter(cat -> cat.getIdCat() == idCat)
                    .findFirst()
                    .map(CategoriaDAO::getCategoria)
                    .orElse("Desconocida");
            return new SimpleStringProperty(nombreCategoria);
        });

        TableColumn<ProductoDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<ProductoDAO, String>, TableCell<ProductoDAO, String>>() {
            @Override
            public TableCell<ProductoDAO, String> call(TableColumn<ProductoDAO, String> param) {
                return new ButtonCellProducto("Editar");
            }
        });

        TableColumn<ProductoDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ProductoDAO, String>, TableCell<ProductoDAO, String>>() {
            @Override
            public TableCell<ProductoDAO, String> call(TableColumn<ProductoDAO, String> param) {
                return new ButtonCellProducto("Eliminar");
            }
        });

        tbvProducto.getColumns().addAll(tbcProducto, tbcPrecio, tbcCosto, tbcCategoria, tbcEditar, tbcEliminar);
        tbvProducto.setItems(objC.SELECT());
    }
}