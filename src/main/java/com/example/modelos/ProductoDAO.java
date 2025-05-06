package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ProductoDAO {

    private int idProd;
    private String producto;
    private float precio;

    public int getIdProd() { return idProd; }
    public void setIdProd( int idProd) { this.idProd = idProd; }

    public String getProducto() { return producto; }
    public void setProducto (String producto) { this.producto = producto; }

    public float getPrecio() { return precio; }
    public void setPrecio( float precio) { this.precio = precio; }

    public void INSERT() {

        String query = "INSERT INTO producto (producto, precio) VALUES ('" + producto + "', '" + precio + "')";

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void UPDATE() {

        String query = "UPDATE producto SET producto = '" + producto + "', precio = '" + precio + "' WHERE idProd = " + idProd;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void DELETE() {

        String query = "DELETE FROM producto WHERE idProd = " + idProd;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public ObservableList<ProductoDAO> SELECT(){

        String query = "SELECT * FROM producto";

        ObservableList<ProductoDAO> listaC = FXCollections.observableArrayList();
        ProductoDAO objC;

        try{

            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()){

                objC = new ProductoDAO();

                objC.setIdProd(res.getInt("idProd"));
                objC.setProducto(res.getString("Producto"));
                objC.setPrecio(res.getFloat("Precio"));

                listaC.add(objC);
            }

        }catch(Exception e){

            e.printStackTrace();
        }

        return listaC;
    }
}
