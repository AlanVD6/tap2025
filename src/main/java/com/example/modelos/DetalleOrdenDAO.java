package com.example.modelos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DetalleOrdenDAO {
    private int idDetalle;
    private int idOrden;
    private ProductoDAO producto;
    private int cantidad;
    private double precioUnitario;

    // Getters y Setters
    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }

    public int getIdOrden() { return idOrden; }
    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }

    public ProductoDAO getProducto() { return producto; }
    public void setProducto(ProductoDAO producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    // Métodos CRUD
    public void INSERT() throws SQLException {
        String query = "INSERT INTO detalleorden (idOrden, idProd, cantidad, precioUnitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idOrden);
            stmt.setInt(2, producto.getIdProd());
            stmt.setInt(3, cantidad);
            stmt.setDouble(4, precioUnitario);
            stmt.executeUpdate();
        }
    }

    public static ObservableList<DetalleOrdenDAO> SELECT_POR_ORDEN(int idOrden) throws SQLException {
        ObservableList<DetalleOrdenDAO> detalles = FXCollections.observableArrayList();
        String query = "SELECT d.*, p.nombre, p.precio FROM detalleorden d " +
                "JOIN producto p ON d.idProd = p.idProd WHERE d.idOrden = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idOrden);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DetalleOrdenDAO detalle = new DetalleOrdenDAO();
                ProductoDAO producto = new ProductoDAO();

                detalle.setIdDetalle(rs.getInt("idDetalle"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPrecioUnitario(rs.getDouble("precioUnitario"));

                producto.setIdProd(rs.getInt("idProd"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));

                detalle.setProducto(producto);
                detalles.add(detalle);
            }
        }
        return detalles;
    }
}