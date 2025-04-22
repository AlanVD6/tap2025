package com.example.modelos;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class ProductoDAO {
    private IntegerProperty idProd = new SimpleIntegerProperty();
    private StringProperty nombre = new SimpleStringProperty();
    private DoubleProperty precio = new SimpleDoubleProperty();
    private IntegerProperty idCat = new SimpleIntegerProperty();
    private IntegerProperty cantidad = new SimpleIntegerProperty(1);
    private byte[] imagen;

    // Constructores
    public ProductoDAO() {}

    public ProductoDAO(int idProd, String nombre, double precio, int idCat) {
        this.idProd.set(idProd);
        this.nombre.set(nombre);
        this.precio.set(precio);
        this.idCat.set(idCat);
    }

    // Métodos CRUD
    public void INSERT() throws SQLException {
        String query = "INSERT INTO producto (nombre, precio, idCat, imagen) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, getNombre());
            stmt.setDouble(2, getPrecio());
            stmt.setInt(3, getIdCat());
            stmt.setBytes(4, getImagen());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    setIdProd(rs.getInt(1));
                }
            }
        }
    }

    public void UPDATE() throws SQLException {
        String query = "UPDATE producto SET nombre = ?, precio = ?, idCat = ?, imagen = ? WHERE idProd = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, getNombre());
            stmt.setDouble(2, getPrecio());
            stmt.setInt(3, getIdCat());
            stmt.setBytes(4, getImagen());
            stmt.setInt(5, getIdProd());
            stmt.executeUpdate();
        }
    }

    public void DELETE() throws SQLException {
        // Primero eliminar los detalles de producto relacionados
        eliminarDetallesProducto();

        // Luego eliminar el producto
        String query = "DELETE FROM producto WHERE idProd = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, getIdProd());
            stmt.executeUpdate();
        }
    }

    private void eliminarDetallesProducto() throws SQLException {
        String query = "DELETE FROM detalleproducto WHERE idProd = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, getIdProd());
            stmt.executeUpdate();
        }
    }

    // Métodos estáticos para consultas
    public static ObservableList<ProductoDAO> obtenerTodos() throws SQLException {
        return SELECT_ALL();
    }

    public static ObservableList<ProductoDAO> SELECT_ALL() throws SQLException {
        ObservableList<ProductoDAO> productos = FXCollections.observableArrayList();
        String query = "SELECT p.*, c.idCat FROM producto p JOIN categoria c ON p.idCat = c.idCat";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ProductoDAO producto = mapearDesdeResultSet(rs);
                productos.add(producto);
            }
        }
        return productos;
    }

    public static ObservableList<ProductoDAO> SELECT_POR_CATEGORIA(int idCategoria) throws SQLException {
        ObservableList<ProductoDAO> productos = FXCollections.observableArrayList();
        String query = "SELECT * FROM producto WHERE idCat = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idCategoria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProductoDAO producto = mapearDesdeResultSet(rs);
                productos.add(producto);
            }
        }
        return productos;
    }

    public static ProductoDAO SELECT_POR_ID(int idProducto) throws SQLException {
        String query = "SELECT * FROM producto WHERE idProd = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearDesdeResultSet(rs);
            }
        }
        return null;
    }

    public static ObservableList<ProductoDAO> buscarPorNombre(String nombre) throws SQLException {
        ObservableList<ProductoDAO> productos = FXCollections.observableArrayList();
        String query = "SELECT * FROM producto WHERE nombre LIKE ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProductoDAO producto = mapearDesdeResultSet(rs);
                productos.add(producto);
            }
        }
        return productos;
    }

    private static ProductoDAO mapearDesdeResultSet(ResultSet rs) throws SQLException {
        ProductoDAO producto = new ProductoDAO();
        producto.setIdProd(rs.getInt("idProd"));
        producto.setNombre(rs.getString("nombre"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setIdCat(rs.getInt("idCat"));
        producto.setImagen(rs.getBytes("imagen"));
        return producto;
    }

    // Métodos para gestión de insumos
    public ObservableList<InsumoDAO> obtenerInsumos() throws SQLException {
        return InsumoDAO.obtenerPorProducto(this.idProd.get());
    }

    // Getters y Setters para propiedades
    public int getIdProd() { return idProd.get(); }
    public void setIdProd(int idProd) { this.idProd.set(idProd); }
    public IntegerProperty idProdProperty() { return idProd; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public StringProperty nombreProperty() { return nombre; }

    public double getPrecio() { return precio.get(); }
    public void setPrecio(double precio) { this.precio.set(precio); }
    public DoubleProperty precioProperty() { return precio; }

    public int getIdCat() { return idCat.get(); }
    public void setIdCat(int idCat) { this.idCat.set(idCat); }
    public IntegerProperty idCatProperty() { return idCat; }

    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int cantidad) { this.cantidad.set(cantidad); }
    public IntegerProperty cantidadProperty() { return cantidad; }

    public byte[] getImagen() { return imagen; }
    public void setImagen(byte[] imagen) { this.imagen = imagen; }

    public String getNombreCategoria() {
        try {
            return CategoriaDAO.obtenerNombrePorId(getIdCat());
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al obtener categoría";
        }
    }

    @Override
    public String toString() {
        return getNombre() + " ($" + String.format("%.2f", getPrecio()) + ")";
    }
}