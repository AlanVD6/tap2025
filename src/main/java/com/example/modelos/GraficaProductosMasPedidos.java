package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GraficaProductosMasPedidos extends Stage {

    public GraficaProductosMasPedidos() {
        TabPane tabPane = new TabPane();
        crearUI(tabPane);
        Scene scene = new Scene(tabPane, 900, 650);
        this.setScene(scene);
        this.setTitle("Análisis de Productos");
        this.show();
    }

    private void crearUI(TabPane tabPane) {
        ProductosMasPedidosDAO productoDAO = new ProductosMasPedidosDAO();
        ObservableList<ProductosMasPedidosDAO> listaProductos = productoDAO.selectAll();

        // Pestaña 1: Productos más pedidos (cantidad)
        Tab tabCantidad = new Tab("Productos Más Pedidos");
        tabCantidad.setClosable(false);
        VBox cantidadContainer = new VBox();

        CategoryAxis xAxisCant = new CategoryAxis();
        xAxisCant.setLabel("Productos");
        NumberAxis yAxisCant = new NumberAxis();
        yAxisCant.setLabel("Cantidad Pedida");

        BarChart<String, Number> barChartCant = new BarChart<>(xAxisCant, yAxisCant);
        barChartCant.setTitle("Top 10 Productos Más Pedidos");

        XYChart.Series<String, Number> seriesCant = new XYChart.Series<>();
        seriesCant.setName("Cantidad");

        for (ProductosMasPedidosDAO producto : listaProductos) {
            seriesCant.getData().add(new XYChart.Data<>(
                    producto.getNombreProducto(),
                    producto.getTotalPedidos()
            ));
        }

        barChartCant.getData().add(seriesCant);
        cantidadContainer.getChildren().add(barChartCant);
        tabCantidad.setContent(cantidadContainer);

        // Pestaña 2: Ganancias por producto
        Tab tabGanancias = new Tab("Ganancias por Producto");
        tabGanancias.setClosable(false);
        VBox gananciasContainer = new VBox();

        CategoryAxis xAxisGan = new CategoryAxis();
        xAxisGan.setLabel("Productos");
        NumberAxis yAxisGan = new NumberAxis();
        yAxisGan.setLabel("Ganancias ($)");

        BarChart<String, Number> barChartGan = new BarChart<>(xAxisGan, yAxisGan);
        barChartGan.setTitle("Top 10 Productos por Ganancias");

        XYChart.Series<String, Number> seriesGan = new XYChart.Series<>();
        seriesGan.setName("Ganancias");

        for (ProductosMasPedidosDAO producto : listaProductos) {
            seriesGan.getData().add(new XYChart.Data<>(
                    producto.getNombreProducto(),
                    producto.getGananciasTotales()
            ));
        }

        barChartGan.getData().add(seriesGan);
        gananciasContainer.getChildren().add(barChartGan);
        tabGanancias.setContent(gananciasContainer);

        tabPane.getTabs().addAll(tabCantidad, tabGanancias);
    }
}