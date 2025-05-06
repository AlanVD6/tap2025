package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class MesaDAO {
    private int idMesa;
    private int numero;
    private int capacidad;
    private String estado = "disponible";

    // Getters y Setters (se mantienen iguales)
    public int getIdMesa() { return idMesa; }
    public void setIdMesa(int idMesa) { this.idMesa = idMesa; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Operaciones CRUD
    public void INSERT() {
        String query = "INSERT INTO mesas (numero, capacidad, estado) VALUES ('" +
                numero + "', '" + capacidad + "', '" + estado + "')";

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idMesa = rs.getInt(1);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void UPDATE() {
        String query = "UPDATE mesas SET " +
                "numero = '" + numero + "', " +
                "capacidad = '" + capacidad + "', " +
                "estado = '" + estado + "' " +
                "WHERE idMesa = " + idMesa;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        String query = "DELETE FROM mesas WHERE idMesa = " + idMesa;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MesaDAO> SELECT() {
        String query = "SELECT * FROM mesas";
        ObservableList<MesaDAO> listaMesas = FXCollections.observableArrayList();

        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                MesaDAO mesa = new MesaDAO();
                mesa.setIdMesa(res.getInt("idMesa"));
                mesa.setNumero(res.getInt("numero"));
                mesa.setCapacidad(res.getInt("capacidad"));
                mesa.setEstado(res.getString("estado"));
                listaMesas.add(mesa);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return listaMesas;
    }

    // Método adicional para actualizar solo el estado
    public void UPDATEestado() {
        String query = "UPDATE mesas SET estado = '" + estado + "' WHERE numero = " + numero;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}