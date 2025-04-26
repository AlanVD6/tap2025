package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class DetalleProductoDAO {

    private int idDP;
    private int idProd;
    private int idIns;

    public int getIdDP() { return idDP; }
    public void setIdDP(int idDP) { this.idDP = idDP; }

    public int getIdProd() { return idProd; }
    public void setIdProd(int idProd) { this.idProd = idProd; }

    public int getIdIns() { return idIns; }
    public void setIdIns(int idIns) { this.idIns = idIns; }

    public void INSERT() {

        String query = "INSERT INTO detProducto (idProd, idIns) VALUES ('" + idProd + "', '" + idIns + "')";

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void UPDATE() {

        String query = "UPDATE detProducto SET  idProd= '" + idProd + "', idIns = '" + idIns + "' WHERE idDP = " + idDP;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void DELETE() {

        String query = "DELETE FROM detProducto WHERE idDP = " + idDP;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public ObservableList<DetalleProductoDAO> SELECT(){

        String query = "SELECT * FROM detProducto";

        ObservableList<DetalleProductoDAO> listaC = FXCollections.observableArrayList();
        DetalleProductoDAO objC;

        try{

            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()){

                objC = new DetalleProductoDAO();

                objC.setIdDP(res.getInt("idDP"));
                objC.setIdProd(res.getInt("idProd"));
                objC.setIdIns(res.getInt("idIns"));

                listaC.add(objC);
            }

        }catch(Exception e){

            e.printStackTrace();
        }

        return listaC;
    }
}
