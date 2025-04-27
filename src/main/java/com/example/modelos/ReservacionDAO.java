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

    public int getIdReserva() { return idReserva; }
    public void setIdReserva (int idReserva) { this.idReserva = idReserva; }

    public int getidCte() { return idCte; }
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

    public void INSERT() {

        String query = "INSERT INTO reservacion (idCte, nomCte, fecha, hora, personas, telefono) VALUES (" +
                idCte + ", '" + nomCte + "', '" + fecha + "', '" + hora + "', '" + personas + "', '" + telefono + "')";

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void UPDATE() {

        String query = "UPDATE reservacion SET nomCte = '" + nomCte + "', fecha = '" + fecha + "', hora = '" +
                hora + "', personas = '" + personas + "', telefono = '" + telefono + "' WHERE idReserva = " + idReserva;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void DELETE() {

        String query = "DELETE FROM reservacion WHERE idProd = " + idReserva;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public ObservableList<ReservacionDAO> SELECT() {

        String query = "SELECT * FROM reservacion";

        ObservableList<ReservacionDAO> listaC = FXCollections.observableArrayList();
        ReservacionDAO objC;

        try{

            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()){

                objC = new ReservacionDAO();

                objC.setIdReserva(res.getInt("idReserva"));
                objC.setIdCte(res.getInt("idCte"));
                objC.setNomCte(res.getString("Nombre"));
                objC.setFecha(res.getString("Fecha"));
                objC.setHora(res.getString("Hora"));
                objC.setPersonas(res.getInt("No. Personas"));
                objC.setTelefono((res.getString("Teléfono")));

                listaC.add(objC);
            }

        }catch(Exception e){

            e.printStackTrace();
        }

        return listaC;
    }
}
