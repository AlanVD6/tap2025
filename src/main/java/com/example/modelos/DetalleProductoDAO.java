package com.example.modelos;

import javafx.collections.ObservableList; // Importación añadida
import java.sql.*;

public class DetalleProductoDAO {
    private int idDetalle;
    private int idProd;
    private int idInsumo;
    private int cantidad;

    // Getters y Setters
    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }

    public int getIdProd() { return idProd; }
    public void setIdProd(int idProd) { this.idProd = idProd; }

    public int getIdInsumo() { return idInsumo; }
    public void setIdInsumo(int idInsumo) { this.idInsumo = idInsumo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    // Métodos CRUD
    public void insertar() throws SQLException {
        String query = "INSERT INTO detalleproducto (idProd, idInsumo, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idProd);
            stmt.setInt(2, idInsumo);
            stmt.setInt(3, cantidad);
            stmt.executeUpdate();
        }
    }

    public void eliminar() throws SQLException {
        String query = "DELETE FROM detalleproducto WHERE idDetalle = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idDetalle);
            stmt.executeUpdate();
        }
    }

    public static void actualizarInsumosProducto(int idProducto, ObservableList<InsumoDAO> insumos) throws SQLException {
        // Eliminar todos los insumos actuales del producto
        String deleteQuery = "DELETE FROM detalleproducto WHERE idProd = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(deleteQuery)) {
            stmt.setInt(1, idProducto);
            stmt.executeUpdate();
        }

        // Insertar los nuevos insumos
        String insertQuery = "INSERT INTO detalleproducto (idProd, idInsumo, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(insertQuery)) {
            for (InsumoDAO insumo : insumos) {
                if (insumo.getCantidad() > 0) {
                    stmt.setInt(1, idProducto);
                    stmt.setInt(2, insumo.getIdInsumo());
                    stmt.setInt(3, insumo.getCantidad());
                    stmt.addBatch();
                }
            }
            stmt.executeBatch();
        }
    }
}