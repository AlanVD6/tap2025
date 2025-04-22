package com.example.modelos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class MesaDAO {
    private int idMesa;
    private IntegerProperty numero = new SimpleIntegerProperty();
    private IntegerProperty capacidad = new SimpleIntegerProperty();
    private StringProperty estado = new SimpleStringProperty();

    // Constructores
    public MesaDAO() {}

    public MesaDAO(int numero, int capacidad, String estado) {
        this.numero.set(numero);
        this.capacidad.set(capacidad);
        this.estado.set(estado);
    }

    // Getters y Setters
    public int getIdMesa() { return idMesa; }
    public void setIdMesa(int idMesa) { this.idMesa = idMesa; }

    public int getNumero() { return numero.get(); }
    public void setNumero(int numero) { this.numero.set(numero); }
    public IntegerProperty numeroProperty() { return numero; }

    public int getCapacidad() { return capacidad.get(); }
    public void setCapacidad(int capacidad) { this.capacidad.set(capacidad); }
    public IntegerProperty capacidadProperty() { return capacidad; }

    public String getEstado() { return estado.get(); }
    public void setEstado(String estado) { this.estado.set(estado); }
    public StringProperty estadoProperty() { return estado; }

    // Métodos CRUD
    public void insertar() throws SQLException {
        String query = "INSERT INTO mesa (numero, capacidad, estado) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, getNumero());
            stmt.setInt(2, getCapacidad());
            stmt.setString(3, getEstado());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.idMesa = rs.getInt(1);
                }
            }
        }
    }

    public void actualizar() throws SQLException {
        String query = "UPDATE mesa SET numero = ?, capacidad = ?, estado = ? WHERE idMesa = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, getNumero());
            stmt.setInt(2, getCapacidad());
            stmt.setString(3, getEstado());
            stmt.setInt(4, getIdMesa());
            stmt.executeUpdate();
        }
    }

    public void eliminar() throws SQLException {
        String query = "DELETE FROM mesa WHERE idMesa = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, getIdMesa());
            stmt.executeUpdate();
        }
    }

    // Métodos estáticos
    public static ObservableList<MesaDAO> obtenerTodas() throws SQLException {
        ObservableList<MesaDAO> mesas = FXCollections.observableArrayList();
        String query = "SELECT * FROM mesa ORDER BY numero";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                MesaDAO mesa = mapearDesdeResultSet(rs);
                mesas.add(mesa);
            }
        }
        return mesas;
    }

    public static ObservableList<MesaDAO> obtenerMesasDisponibles() throws SQLException {
        ObservableList<MesaDAO> mesas = FXCollections.observableArrayList();
        String query = "SELECT * FROM mesa WHERE estado = 'Disponible' ORDER BY numero";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                MesaDAO mesa = mapearDesdeResultSet(rs);
                mesas.add(mesa);
            }
        }
        return mesas;
    }

    public static MesaDAO obtenerPorNumero(int numeroMesa) throws SQLException {
        String query = "SELECT * FROM mesa WHERE numero = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, numeroMesa);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearDesdeResultSet(rs);
            }
        }
        return null;
    }

    public static void cambiarEstadoMesa(int idMesa, String nuevoEstado) throws SQLException {
        String query = "UPDATE mesa SET estado = ? WHERE idMesa = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idMesa);
            stmt.executeUpdate();
        }
    }

    // Método auxiliar
    private static MesaDAO mapearDesdeResultSet(ResultSet rs) throws SQLException {
        MesaDAO mesa = new MesaDAO();
        mesa.setIdMesa(rs.getInt("idMesa"));
        mesa.setNumero(rs.getInt("numero"));
        mesa.setCapacidad(rs.getInt("capacidad"));
        mesa.setEstado(rs.getString("estado"));
        return mesa;
    }

    @Override
    public String toString() {
        return "Mesa #" + getNumero() + " (" + getCapacidad() + " pers.) - " + getEstado();
    }
}