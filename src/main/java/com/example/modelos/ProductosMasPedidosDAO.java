package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductosMasPedidosDAO {
    private String nombreProducto;
    private int totalPedidos;
    private double gananciasTotales;

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(int totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public double getGananciasTotales() {
        return gananciasTotales;
    }

    public void setGananciasTotales(double gananciasTotales) {
        this.gananciasTotales = gananciasTotales;
    }

    public ObservableList<ProductosMasPedidosDAO> selectAll() {
        String query = "SELECT p.producto AS nombreProducto, SUM(d.cantidad) AS totalPedidos, " +
                "SUM(d.cantidad * p.precio) AS gananciasTotales " +
                "FROM detOrden d " +
                "JOIN producto p ON d.idProd = p.idProd " +
                "GROUP BY p.producto " +
                "ORDER BY totalPedidos DESC " +
                "LIMIT 10";

        ObservableList<ProductosMasPedidosDAO> lista = FXCollections.observableArrayList();

        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                ProductosMasPedidosDAO obj = new ProductosMasPedidosDAO();
                obj.setNombreProducto(res.getString("nombreProducto"));
                obj.setTotalPedidos(res.getInt("totalPedidos"));
                obj.setGananciasTotales(res.getDouble("gananciasTotales"));
                lista.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}