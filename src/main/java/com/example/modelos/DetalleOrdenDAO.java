package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DetalleOrdenDAO {

    private int idOD;
    private int idOrden;
    private int idProd;
    private int cantidad;

    public int getIdOD() { return idOD; }
    public void setIdOD(int idOD) { this.idOD = idOD; }

    public int getIdOrden() { return idOrden; }
    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }

    public int getIdProd() { return idProd; }
    public void setIdProd(int idProd) { this.idProd = idProd; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public void INSERT() {

        String query = "INSERT INTO detOrden (idOrden, idProd, cantidad) VALUES ('" + idOrden + "', '" + idProd + "', '" + cantidad + "')";

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void UPDATE() {

        String query = "UPDATE detOrden SET idOrden = '" + idOrden + "', idProd = '" + idProd + "', cantidad = '" + cantidad + "'WHERE idOD = " + idOD;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void DELETE() {

        String query = "DELETE FROM detOrden WHERE idOD = " + idOD;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            Alert warning = new Alert(Alert.AlertType.ERROR);
            warning.setTitle("Aviso!");
            warning.setHeaderText("No se puede eliminar un registro.");
            warning.setContentText("No puedes eliminar un producto, pues esta relacionado con el menu directamente,\nademas de estar relacionado con tu inventario");
            warning.showAndWait();
            e.printStackTrace();
        }
    }

    public ObservableList<DetalleOrdenDAO> SELECT(){

        String query = "SELECT * FROM detOrden";

        ObservableList<DetalleOrdenDAO> listaC = FXCollections.observableArrayList();
        DetalleOrdenDAO objC;

        try{

            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()){

                objC = new DetalleOrdenDAO();

                objC.setIdOD(res.getInt("idOD"));
                objC.setIdOrden(res.getInt("idOrden"));
                objC.setIdProd(res.getInt("idProd"));
                objC.setCantidad(res.getInt("cantidad"));

                listaC.add(objC);
            }

        }catch(Exception e){

            e.printStackTrace();
        }

        return listaC;
    }

    public void INSERT(int IdOrden, int IdProducto, int Cantidad) {
        String query = "INSERT INTO detOrden (idOrden, idProd, cantidad) VALUES (?, ?, ?)";

        try {
            PreparedStatement pstmt = Conexion.connection.prepareStatement(query);
            pstmt.setInt(1, IdOrden);
            pstmt.setInt(2, IdProducto);
            pstmt.setInt(3, Cantidad);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
