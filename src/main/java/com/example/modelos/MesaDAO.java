package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class MesaDAO {

    private int idMesa;
    private int numero;
    private  int capacidad;
    private String estado = "Disponible";

    public int getIdMesa() { return idMesa; }
    public void setIdMesa (int idMesa) { this.idMesa = idMesa; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado;}

    public void INSERT() {

        String query = "INSERT INTO mesa (numero, capacidad, estado) VALUES ('" + numero + "', '" + capacidad + "', '" + estado + "')";

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void UPDATE() {

        String query = "UPDATE mesa SET numero = '" + numero + "', capacidad = '" + capacidad + "', estado = '" + estado + "' WHERE id = " + idMesa;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void DELETE() {

        String query = "DELETE FROM mesa WHERE idMesa = " + idMesa;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public ObservableList<MesaDAO> SELECT(){

        String query = "SELECT * FROM mesa";

        ObservableList<MesaDAO> listaC = FXCollections.observableArrayList();
        MesaDAO objC;

        try{

            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()){

                objC = new MesaDAO();

                objC.setIdMesa(res.getInt("idMesa"));
                objC.setNumero(res.getInt("Número"));
                objC.setCapacidad(res.getInt("Capacidad"));
                objC.setEstado(res.getString("Estado"));

                listaC.add(objC);
            }

        }catch(Exception e){

            e.printStackTrace();
        }

        return listaC;
    }
}
