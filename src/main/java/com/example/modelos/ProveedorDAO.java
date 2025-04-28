package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ProveedorDAO {

    private int idProv;
    private String razonSocial;

    public int getIdProv() { return idProv; }
    public void setIdProv( int idProv) { this.idProv = idProv; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public void INSERT() {

        String query = "INSERT INTO proveedor (razonSocial) VALUES ('" + razonSocial + "')";

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void UPDATE() {

        String query = "UPDATE proveedor SET razonSocial = '" + razonSocial + "' WHERE idProv = " + idProv;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void DELETE() {

        String query = "DELETE FROM proveedor WHERE idProv = " + idProv;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public ObservableList<ProveedorDAO> SELECT(){

        String query = "SELECT * FROM proveedor";

        ObservableList<ProveedorDAO> listaC = FXCollections.observableArrayList();
        ProveedorDAO objC;

        try{

            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()){

                objC = new ProveedorDAO();

                objC.setIdProv(res.getInt("idProv"));
                objC.setRazonSocial(res.getString("razonSocial"));

                listaC.add(objC);
            }

        }catch(Exception e){

            e.printStackTrace();
        }

        return listaC;
    }
}
