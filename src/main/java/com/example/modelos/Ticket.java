package com.example.modelos;

import java.sql.ResultSet;
import java.sql.Statement;

public class Ticket {

    public ResultSet ticket(int idOrden){
        ResultSet res = null;
        String query1 = "SELECT c.nombre, e.nombre, o.fecha, o.hora, " +
                "o.idOrd, m.numero, p.producto, p.precio, " +
                "sum(p.precio * do.cantidad) from clientes c join orden o on c.idCte = o.idCte " +
                "join empleado e on o.idEmp = e.idEmp " +
                "join mesas m on o.idMesa = m.idMesa " +
                "join detorden do on o.idOrd = do.idOrden" +
                "join producto p on do.idProd = p.idProd " +
                "where o.idOrd ="+ idOrden +
                " group by 1,2,3,4,5,6,7,8;";

        try{
            Statement stmt = Conexion.connection.createStatement();
            res = stmt.executeQuery(query1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
}