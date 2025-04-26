package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class OrdenDAO {
    
    private int idOrd;
    private int noOrden;
    private String fecha;
    private String hora;
    private int idCte;
    private int idMesa;

    public int getIdOrd() { return idOrd; }
    public void setIdOrd(int idOrd) { this.idOrd = idOrd; }

    public int getNoOrden() { return noOrden; }
    public void setNoOrden(int noOrden) { this.noOrden = noOrden; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public int getIdCte() { return idCte; }
    public void setIdCte(int idCte) { this.idCte = idCte; }

    public int getIdMesa() { return idMesa; }
    public void setIdMesa(int idMesa) { this.idMesa = idMesa; }

    public void INSERT() {

        String query = "INSERT INTO orden (noOrden, fecha, hora, idCte, idMesa) VALUES ('" + noOrden + "', '" + fecha + "', '" + hora + "', '" + idCte + "', '" + idMesa + "')";

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void UPDATE() {

        String query = "UPDATE orden SET  noOrden = '" + noOrden + "', fecha = '" + fecha + "', hora = '" + hora + "', idCte = '" + idCte + "', idMesa = '" + idMesa + "' WHERE idOrd = " + idOrd;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void DELETE() {

        String query = "DELETE FROM orden WHERE idOrd = " + idOrd;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public ObservableList<OrdenDAO> SELECT(){

        String query = "SELECT * FROM orden";

        ObservableList<OrdenDAO> listaC = FXCollections.observableArrayList();
        OrdenDAO objC;

        try{

            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()){

                objC = new OrdenDAO();

                objC.setIdOrd(res.getInt("idOrd"));
                objC.setNoOrden(res.getInt("noOrden"));
                objC.setFecha(res.getString("Fecha"));
                objC.setHora(res.getString("Hora"));
                objC.setIdCte(res.getInt("idCte"));
                objC.setIdMesa(res.getInt("idMesa"));

                listaC.add(objC);
            }

        }catch(Exception e){

            e.printStackTrace();
        }

        return listaC;
    }
}