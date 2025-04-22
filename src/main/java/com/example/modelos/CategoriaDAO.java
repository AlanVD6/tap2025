package com.example.modelos;


import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class CategoriaDAO {
    private IntegerProperty idCat = new SimpleIntegerProperty();
    private StringProperty nombre = new SimpleStringProperty();
    private StringProperty descripcion = new SimpleStringProperty();

    // Constructores
    public CategoriaDAO() {}

    public CategoriaDAO(int idCat, String nombre, String descripcion) {
        this.idCat.set(idCat);
        this.nombre.set(nombre);
        this.descripcion.set(descripcion);
    }

    // Métodos CRUD
    public void INSERT() throws SQLException {
        String query = "INSERT INTO categoria (nombre, descripcion) VALUES (?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, getNombre());
            stmt.setString(2, getDescripcion());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    setIdCat(rs.getInt(1));
                }
            }
        }
    }

    public void UPDATE() throws SQLException {
        String query = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE idCat = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, getNombre());
            stmt.setString(2, getDescripcion());
            stmt.setInt(3, getIdCat());
            stmt.executeUpdate();
        }
    }

    public void DELETE() throws SQLException {
        String query = "DELETE FROM categoria WHERE idCat = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, getIdCat());
            stmt.executeUpdate();
        }
    }

    // Métodos estáticos para consultas
    public static ObservableList<CategoriaDAO> obtenerTodas() throws SQLException {
        ObservableList<CategoriaDAO> categorias = FXCollections.observableArrayList();
        String query = "SELECT * FROM categoria";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CategoriaDAO categoria = new CategoriaDAO(
                        rs.getInt("idCat"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                categorias.add(categoria);
            }
        }
        return categorias;
    }

    public static String obtenerNombrePorId(int idCat) throws SQLException {
        String query = "SELECT nombre FROM categoria WHERE idCat = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idCat);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            }
        }
        return "Sin categoría";
    }

    public static int obtenerIdPorNombre(String nombre) throws SQLException {
        String query = "SELECT idCat FROM categoria WHERE nombre = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idCat");
            }
        }
        return -1;
    }

    // Getters y Setters
    public int getIdCat() { return idCat.get(); }
    public void setIdCat(int idCat) { this.idCat.set(idCat); }
    public IntegerProperty idCatProperty() { return idCat; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public StringProperty nombreProperty() { return nombre; }

    public String getDescripcion() { return descripcion.get(); }
    public void setDescripcion(String descripcion) { this.descripcion.set(descripcion); }
    public StringProperty descripcionProperty() { return descripcion; }

    @Override
    public String toString() {
        return getNombre();
    }
}