package com.example.Componentes;

import com.example.modelos.ProductoDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ProductoCard extends VBox {
    public ProductoCard(ProductoDAO producto) {
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
        setPadding(new Insets(15));
        setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        // Imagen del producto
        ImageView imgProducto = new ImageView();
        try {
            imgProducto.setImage(new Image(getClass().getResourceAsStream("/Images/productos/" + producto.getIdProd() + ".jpg")));
        } catch (Exception e) {
            imgProducto.setImage(new Image(getClass().getResourceAsStream("/Images/productos/default.jpg")));
        }
        imgProducto.setFitWidth(150);
        imgProducto.setFitHeight(150);

        // Clip para imagen redonda
        Rectangle clip = new Rectangle(150, 150);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imgProducto.setClip(clip);

        // Nombre del producto
        Label lblNombre = new Label(producto.getNombre());
        lblNombre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #343a40;");
        lblNombre.setMaxWidth(150);
        lblNombre.setWrapText(true);

        // Precio
        Label lblPrecio = new Label(String.format("$%.2f", producto.getPrecio()));
        lblPrecio.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #28a745;");

        getChildren().addAll(imgProducto, lblNombre, lblPrecio);
    }
}