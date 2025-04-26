package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class MesaReservaDAO {

    private int idMR;
    private int idMesa;
    private int idReserva;

    public int getIdMR() { return idMR;}
    public void setIdMR(int idMR) { this.idMR = idMR; }

    public int getIdMesa() { return idMesa; }
    public void setIdMesa(int idMesa) { this.idMesa = idMesa; }

    public int  getIdReserva() { return idReserva;}
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public void INSERT() {

        String query = "INSERT INTO mesa_reserva (idMesa, idReserva) VALUES ('" +idMesa + "', '" + idReserva+ "')";

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void UPDATE() {

        String query = "UPDATE mesa_reserva SET idMesa = '" + idMesa + "', idReserva = '" + idReserva + "' WHERE idMR = " + idMR;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void DELETE() {

        String query = "DELETE FROM mesa_reserva WHERE idMR = " + idMR;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public ObservableList<MesaReservaDAO> SELECT(){

        String query = "SELECT * FROM mesa_reserva";

        ObservableList<MesaReservaDAO> listaC = FXCollections.observableArrayList();
        MesaReservaDAO objC;

        try{

            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()){

                objC = new MesaReservaDAO();

                objC.setIdMR(res.getInt("idMR"));
                objC.setIdMesa(res.getInt("idMesa"));
                objC.setIdReserva(res.getInt("idReserva"));

                listaC.add(objC);
            }

        }catch(Exception e){

            e.printStackTrace();
        }

        return listaC;
    }
}
