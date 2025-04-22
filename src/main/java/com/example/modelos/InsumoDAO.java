package com.example.modelos;

import javafx.beans.property.*;
import javafx.collections.*;
import java.sql.*;

public class InsumoDAO {
    private int idInsumo;
    private StringProperty nombre = new SimpleStringProperty();
    private StringProperty marca = new SimpleStringProperty();
    private IntegerProperty cantidad = new SimpleIntegerProperty();
    private ObjectProperty<ProveedorDAO> proveedor = new SimpleObjectProperty<>();

    // Constructores
    public InsumoDAO() {}

    public InsumoDAO(String nombre, String marca, int cantidad) {
        this.nombre.set(nombre);
        this.marca.set(marca);
        this.cantidad.set(cantidad);
    }

    // Getters y Setters
    public int getIdInsumo() { return idInsumo; }
    public void setIdInsumo(int idInsumo) { this.idInsumo = idInsumo; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public StringProperty nombreProperty() { return nombre; }

    public String getMarca() { return marca.get(); }
    public void setMarca(String marca) { this.marca.set(marca); }
    public StringProperty marcaProperty() { return marca; }

    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int cantidad) { this.cantidad.set(cantidad); }
    public IntegerProperty cantidadProperty() { return cantidad; }

    public ProveedorDAO getProveedor() { return proveedor.get(); }
    public void setProveedor(ProveedorDAO proveedor) { this.proveedor.set(proveedor); }
    public ObjectProperty<ProveedorDAO> proveedorProperty() { return proveedor; }

    // Métodos CRUD
    public void insertar() throws SQLException {
        String query = "INSERT INTO insumo (nombre, marca, cantidad, idProv) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, getNombre());
            stmt.setString(2, getMarca());
            stmt.setInt(3, getCantidad());
            stmt.setInt(4, proveedor.get() != null ? proveedor.get().getIdProv() : null);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.idInsumo = rs.getInt(1);
                }
            }
        }
    }

    public void actualizar() throws SQLException {
        String query = "UPDATE insumo SET nombre = ?, marca = ?, cantidad = ?, idProv = ? WHERE idInsumo = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, getNombre());
            stmt.setString(2, getMarca());
            stmt.setInt(3, getCantidad());
            stmt.setInt(4, proveedor.get() != null ? proveedor.get().getIdProv() : null);
            stmt.setInt(5, getIdInsumo());
            stmt.executeUpdate();
        }
    }

    public void eliminar() throws SQLException {
        String query = "DELETE FROM insumo WHERE idInsumo = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, getIdInsumo());
            stmt.executeUpdate();
        }
    }

    // Métodos estáticos
    public static ObservableList<InsumoDAO> obtenerTodos() throws SQLException {
        ObservableList<InsumoDAO> insumos = FXCollections.observableArrayList();
        String query = "SELECT i.*, p.razon_social FROM insumo i LEFT JOIN proveedor p ON i.idProv = p.idProv";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                InsumoDAO insumo = mapearDesdeResultSet(rs);
                insumos.add(insumo);
            }
        }
        return insumos;
    }

    public static ObservableList<InsumoDAO> obtenerPorProducto(int idProducto) throws SQLException {
        ObservableList<InsumoDAO> insumos = FXCollections.observableArrayList();
        String query = "SELECT i.*, p.razon_social, dp.cantidad as cantidad_requerida " +
                "FROM detalleproducto dp " +
                "JOIN insumo i ON dp.idInsumo = i.idInsumo " +
                "LEFT JOIN proveedor p ON i.idProv = p.idProv " +
                "WHERE dp.idProd = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                InsumoDAO insumo = mapearDesdeResultSet(rs);
                insumo.cantidad.set(rs.getInt("cantidad_requerida")); // Sobrescribe con cantidad requerida
                insumos.add(insumo);
            }
        }
        return insumos;
    }

    private static InsumoDAO mapearDesdeResultSet(ResultSet rs) throws SQLException {
        InsumoDAO insumo = new InsumoDAO();
        insumo.setIdInsumo(rs.getInt("idInsumo"));
        insumo.setNombre(rs.getString("nombre"));
        insumo.setMarca(rs.getString("marca"));
        insumo.setCantidad(rs.getInt("cantidad"));

        if (rs.getString("razon_social") != null) {
            ProveedorDAO prov = new ProveedorDAO();
            prov.setIdProv(rs.getInt("idProv"));
            prov.setRazonSocial(rs.getString("razon_social"));
            insumo.setProveedor(prov);
        }

        return insumo;
    }

    @Override
    public String toString() {
        return getNombre() + " (" + getMarca() + ") - " + getCantidad() + " unidades";
    }
}