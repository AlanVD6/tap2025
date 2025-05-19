package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ReservacionDAO {

    private int idReserva;
    private int idCte;
    private String nomCte;
    private String fecha;
    private String hora;
    private int personas;
    private String telefono;
    private int numeroMesa;

    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public int getIdCte() { return idCte; }
    public void setIdCte(int idCte) { this.idCte = idCte; }

    public String getNomCte() { return nomCte; }
    public void setNomCte(String nomCte) { this.nomCte = nomCte; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public int getPersonas() { return personas; }
    public void setPersonas(int personas) { this.personas = personas; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }

    public void INSERT() {
        String query = "INSERT INTO reservacion (idCte, nomCte, fecha, hora, personas, telefono, numeroMesa) VALUES (" +
                idCte + ", '" + nomCte + "', '" + fecha + "', '" + hora + "', " + personas + ", '" + telefono + "', " + numeroMesa + ")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idReserva = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void UPDATE() {
        String query = "UPDATE reservacion SET idCte = " + idCte + ", nomCte = '" + nomCte + "', fecha = '" + fecha +
                "', hora = '" + hora + "', personas = " + personas + ", telefono = '" + telefono +
                "', numeroMesa = " + numeroMesa + " WHERE idReserva = " + idReserva;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE() {
        try {
            // Cambiar estado de la mesa a disponible antes de eliminar
            MesaDAO mesaDAO = new MesaDAO();
            for (MesaDAO m : mesaDAO.SELECT()) {
                if (m.getNumero() == numeroMesa) {
                    m.setEstado("disponible");
                    m.UPDATE();
                    break;
                }
            }

            String query = "DELETE FROM reservacion WHERE idReserva = " + idReserva;
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ReservacionDAO> SELECT() {
        ObservableList<ReservacionDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT * FROM reservacion";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ReservacionDAO r = new ReservacionDAO();
                r.setIdReserva(rs.getInt("idReserva"));
                r.setIdCte(rs.getInt("idCte"));
                r.setNomCte(rs.getString("nomCte"));
                r.setFecha(rs.getString("fecha"));
                r.setHora(rs.getString("hora"));
                r.setPersonas(rs.getInt("personas"));
                r.setTelefono(rs.getString("telefono"));
                r.setNumeroMesa(rs.getInt("numeroMesa"));
                lista.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}

