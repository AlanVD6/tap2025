package com.example.modelos;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private int idOrden;
    private List<ItemTicket> productos;
    private float total;

    public Ticket(int idOrden) {
        this.idOrden = idOrden;
        this.productos = new ArrayList<>();
        cargarProductos();
    }

    private void cargarProductos() {
        try {
            String query = "SELECT d.idProd, d.cantidad, p.producto, p.precio " +
                    "FROM detOrden d JOIN producto p ON d.idProd = p.idProd " +
                    "WHERE d.idOrden = " + idOrden;

            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                String nombreProducto = res.getString("producto");
                int cantidad = res.getInt("cantidad");
                float precioUnitario = res.getFloat("precio");

                productos.add(new ItemTicket(nombreProducto, cantidad, precioUnitario));
                total += cantidad * precioUnitario;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imprimirTicket() {
        System.out.println("====== TICKET DE COMPRA ======");
        System.out.println("Orden No: " + idOrden);
        System.out.println("--------------------------------");
        System.out.printf("%-20s %5s %10s %10s\n", "Producto", "Cant", "P.Unit", "Subtotal");
        System.out.println("--------------------------------");

        for (ItemTicket item : productos) {
            System.out.printf("%-20s %5d %10.2f %10.2f\n",
                    item.getNombreProducto(),
                    item.getCantidad(),
                    item.getPrecioUnitario(),
                    item.getSubtotal());
        }

        System.out.println("--------------------------------");
        System.out.printf("%-30s %10.2f\n", "TOTAL:", total);
        System.out.println("Gracias por su compra :)");
        System.out.println("===============================");
    }

    public int getIdOrden() { return idOrden; }
    public List<ItemTicket> getProductos() { return productos; }
    public float getTotal() { return total; }

    // Clase interna para cada producto del ticket
    public static class ItemTicket {
        private String nombreProducto;
        private int cantidad;
        private float precioUnitario;

        public ItemTicket(String nombreProducto, int cantidad, float precioUnitario) {
            this.nombreProducto = nombreProducto;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
        }

        public String getNombreProducto() { return nombreProducto; }
        public int getCantidad() { return cantidad; }
        public float getPrecioUnitario() { return precioUnitario; }
        public float getSubtotal() { return cantidad * precioUnitario; }
    }
}
