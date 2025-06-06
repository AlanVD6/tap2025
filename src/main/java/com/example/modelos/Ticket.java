package com.example.modelos;

import java.sql.ResultSet;
import java.sql.Statement;

public class Ticket {

    public ResultSet ticket(int idOrden) {
        ResultSet res = null;

        String query = "SELECT e.nombre, o.fecha, o.hora, o.idOrd, m.numero, " +
                "p.producto, p.precio, do.cantidad, (p.precio * do.cantidad) AS subtotal " +
                "FROM orden o " +
                "JOIN empleado e ON o.idEmp = e.idEmp " +
                "JOIN mesas m ON o.idMesa = m.idMesa " +
                "JOIN detorden do ON o.idOrd = do.idOrden " +
                "JOIN producto p ON do.idProd = p.idProd " +
                "WHERE o.idOrd = " + idOrden + ";";

        try {
            Statement stmt = Conexion.connection.createStatement();
            res = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
