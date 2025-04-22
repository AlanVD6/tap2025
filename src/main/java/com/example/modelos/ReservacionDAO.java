package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservacionDAO {
    private int idReservacion;
    private String nombreCliente;
    private String telefono;
    private LocalDate fecha;
    private LocalTime hora;
    private MesaDAO mesa;

    // Getters y Setters
    public int getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(int idReservacion) {
        this.idReservacion = idReservacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public MesaDAO getMesa() {
        return mesa;
    }

    public void setMesa(MesaDAO mesa) {
        this.mesa = mesa;
    }

    public void insertar() throws SQLException {
        String query = "INSERT INTO reservacion (nombreCliente, telefono, fecha, hora, idMesa) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombreCliente);
            stmt.setString(2, telefono);
            stmt.setDate(3, Date.valueOf(fecha));
            stmt.setTime(4, Time.valueOf(hora));
            stmt.setInt(5, mesa.getIdMesa());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.idReservacion = rs.getInt(1);
                }
            }
        }
    }

    public static ObservableList<ReservacionDAO> obtenerTodas() throws SQLException {
        ObservableList<ReservacionDAO> reservaciones = FXCollections.observableArrayList();
        String query = "SELECT r.*, m.numero FROM reservacion r JOIN mesa m ON r.idMesa = m.idMesa";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ReservacionDAO res = new ReservacionDAO();
                MesaDAO mesa = new MesaDAO();

                res.setIdReservacion(rs.getInt("idReservacion"));
                res.setNombreCliente(rs.getString("nombreCliente"));
                res.setTelefono(rs.getString("telefono"));
                res.setFecha(rs.getDate("fecha").toLocalDate());
                res.setHora(rs.getTime("hora").toLocalTime());

                mesa.setIdMesa(rs.getInt("idMesa"));
                mesa.setNumero(rs.getInt("numero"));
                res.setMesa(mesa);

                reservaciones.add(res);
            }
        }
        return reservaciones;
    }
}
