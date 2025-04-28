package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class InsumosDAO {

    private int idIns;
    private String insumo;
    private float cantidad;
    private int idProv;

    public int getIdIns() { return idIns; }
    public void setIdIns(int idIns) { this.idIns = idIns; }

    public String getInsumo() { return insumo; }
    public void setInsumo(String insumo) { this.insumo = insumo; }

    public float getCantidad() { return cantidad; }
    public void setCantidad(float cantidad) { this.cantidad = cantidad; }

    public int getIdProv() { return idProv; }
    public void setIdProv(int idProv) { this.idProv = idProv; }

    public void INSERT() {

        String query = "INSERT INTO insumos (insumo,  cantidad, idProv) VALUES ('" + insumo+ "', '" + cantidad + "', '" + idProv + "')";

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void UPDATE() {

        String query = "UPDATE insumos SET insumo = '" + insumo + "', cantidad = '" + cantidad + "', idProv = '" + idProv + "' WHERE idIns = " + idIns;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void DELETE() {

        String query = "DELETE FROM insumos WHERE idIns = " + idIns;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public ObservableList<InsumosDAO> SELECT(){

        String query = "SELECT * FROM insumos";

        ObservableList<InsumosDAO> listaC = FXCollections.observableArrayList();
        InsumosDAO objC;

        try{

            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()){

                objC = new InsumosDAO();

                objC.setIdIns(res.getInt("idIns"));
                objC.setInsumo(res.getString("Insumo"));
                objC.setCantidad(res.getFloat("Cantidad"));
                objC.setIdProv(res.getInt("idProv"));

                listaC.add(objC);
            }

        }catch(Exception e){

            e.printStackTrace();
        }

        return listaC;
    }
}