package com.example.modelos;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class ProveedorDAO {
    private int idProv;
    private StringProperty razonSocial = new SimpleStringProperty();
    private StringProperty rfc = new SimpleStringProperty();
    private StringProperty cp = new SimpleStringProperty();
    private StringProperty telefono = new SimpleStringProperty();

    // Constructores
    public ProveedorDAO() {}

    public ProveedorDAO(String razonSocial, String rfc, String cp) {
        this.razonSocial.set(razonSocial);
        this.rfc.set(rfc);
        this.cp.set(cp);
    }

    // Métodos CRUD
    public void insertar() throws SQLException {
        String query = "INSERT INTO proveedor (razon_social, rfc, cp, telefono) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, getRazonSocial());
            stmt.setString(2, getRfc());
            stmt.setString(3, getCp());
            stmt.setString(4, getTelefono());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.idProv = rs.getInt(1);
                }
            }
        }
    }

    public void actualizar() throws SQLException {
        String query = "UPDATE proveedor SET razon_social = ?, rfc = ?, cp = ?, telefono = ? WHERE idProv = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, getRazonSocial());
            stmt.setString(2, getRfc());
            stmt.setString(3, getCp());
            stmt.setString(4, getTelefono());
            stmt.setInt(5, getIdProv());
            stmt.executeUpdate();
        }
    }

    public void eliminar() throws SQLException {
        String query = "DELETE FROM proveedor WHERE idProv = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, getIdProv());
            stmt.executeUpdate();
        }
    }

    // Métodos estáticos
    public static ObservableList<ProveedorDAO> obtenerTodos() throws SQLException {
        ObservableList<ProveedorDAO> proveedores = FXCollections.observableArrayList();
        String query = "SELECT * FROM proveedor ORDER BY razon_social";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                proveedores.add(mapearDesdeResultSet(rs));
            }
        }
        return proveedores;
    }

    public static ProveedorDAO obtenerPorId(int idProv) throws SQLException {
        String query = "SELECT * FROM proveedor WHERE idProv = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idProv);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearDesdeResultSet(rs);
            }
        }
        return null;
    }

    private static ProveedorDAO mapearDesdeResultSet(ResultSet rs) throws SQLException {
        ProveedorDAO prov = new ProveedorDAO();
        prov.setIdProv(rs.getInt("idProv"));
        prov.setRazonSocial(rs.getString("razon_social"));
        prov.setRfc(rs.getString("rfc"));
        prov.setCp(rs.getString("cp"));
        prov.setTelefono(rs.getString("telefono"));
        return prov;
    }

    // Getters y Setters
    public int getIdProv() { return idProv; }
    public void setIdProv(int idProv) { this.idProv = idProv; }

    public String getRazonSocial() { return razonSocial.get(); }
    public void setRazonSocial(String razonSocial) { this.razonSocial.set(razonSocial); }
    public StringProperty razonSocialProperty() { return razonSocial; }

    public String getRfc() { return rfc.get(); }
    public void setRfc(String rfc) { this.rfc.set(rfc); }
    public StringProperty rfcProperty() { return rfc; }

    public String getCp() { return cp.get(); }
    public void setCp(String cp) { this.cp.set(cp); }
    public StringProperty cpProperty() { return cp; }

    public String getTelefono() { return telefono.get(); }
    public void setTelefono(String telefono) { this.telefono.set(telefono); }
    public StringProperty telefonoProperty() { return telefono; }

    @Override
    public String toString() {
        return getRazonSocial() + " (" + getRfc() + ")";
    }
}
