package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class CategoriaDAO {

    private int idCat;
    private String categoria;

    public int getIdCat(){ return idCat; }

    public void setIdCat(int idCat) { this.idCat = idCat; }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }

    public void INSERT (){

        String query = "INSERT INTO categoria(categoria) " + "values('" + categoria +"')";

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void UPDATE(){

        String query = "UPDATE categoria SET categoria = '" + categoria + "' WHERE idCat = " + idCat;

        try {

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = stmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                this.idCat = generatedKeys.getInt(1);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void DELETE(){

        String query = "DELETE FROM categoria WHERE idCat = " + idCat;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public ObservableList<CategoriaDAO> SELECT () {

        String query = "SELECT * FROM categoria";
        ObservableList<CategoriaDAO> listaC = FXCollections.observableArrayList();
        CategoriaDAO objC;

        try{

            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()){

                objC = new CategoriaDAO();
                objC.setIdCat(res.getInt("idCat"));
                objC.setCategoria(res.getString("categoria"));
                listaC.add(objC);

            }
        }catch(Exception e){

            e.printStackTrace();
        }

        return listaC;
    }
}
