package com.example.util;


import com.example.modelos.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TxtGenerator {

    public static void generarReporteProductos(List<ProductoDAO> productos, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            // Encabezado
            writer.write("====================================");
            writer.newLine();
            writer.write("       REPORTE DE PRODUCTOS");
            writer.newLine();
            writer.write("====================================");
            writer.newLine();
            writer.write(String.format("%-30s %-20s %-10s %s",
                    "PRODUCTO", "CATEGORÍA", "PRECIO", "INSUMOS"));
            writer.newLine();
            writer.write("------------------------------------");
            writer.newLine();

            // Contenido
            for (ProductoDAO producto : productos) {
                writer.write(String.format("%-30s %-20s $%-9.2f %s",
                        producto.getNombre(),
                        obtenerNombreCategoria(producto.getIdCat()),
                        producto.getPrecio(),
                        obtenerInsumosProducto(producto.getIdProd())));
                writer.newLine();
            }

            // Pie
            writer.write("------------------------------------");
            writer.newLine();
            writer.write("Total productos: " + productos.size());
            writer.newLine();
            writer.write("Generado: " + LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        } catch (IOException e) {
            System.err.println("Error al generar TXT: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void generarTicket(OrdenDAO orden, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            // Encabezado
            writer.write("====================================");
            writer.newLine();
            writer.write("       FONDITA DOÑA LUPE");
            writer.newLine();
            writer.write("RFC: ABCD123456XYZ  Tel: 555-123-4567");
            writer.newLine();
            writer.write("====================================");
            writer.newLine();
            writer.write("Fecha: " + LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            writer.newLine();
            writer.write("Mesa: " + orden.getMesa().getNumero());
            writer.newLine();
            writer.write("Atendió: " + orden.getEmpleado().getNombre());
            writer.newLine();
            writer.write("------------------------------------");
            writer.newLine();
            writer.write(String.format("%-20s %5s %10s", "PRODUCTO", "CANT", "SUBTOTAL"));
            writer.newLine();
            writer.write("------------------------------------");
            writer.newLine();

            // Items
            for (DetalleOrdenDAO item : orden.getDetalles()) {
                writer.write(String.format("%-20s %5d $%9.2f",
                        item.getProducto().getNombre(),
                        item.getCantidad(),
                        item.getPrecioUnitario() * item.getCantidad()));
                writer.newLine();
            }

            // Total
            writer.write("------------------------------------");
            writer.newLine();
            writer.write(String.format("%26s $%9.2f", "TOTAL:", orden.getTotal()));
            writer.newLine();
            writer.write("====================================");
            writer.newLine();
            writer.write("       ¡GRACIAS POR SU VISITA!");
            writer.newLine();
            writer.write("====================================");

        } catch (IOException e) {
            System.err.println("Error al generar ticket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Métodos auxiliares (implementar según tu sistema)
    private static String obtenerNombreCategoria(int idCat) {
        // Consultar a tu DAO de categorías
        return "General";
    }

    private static String obtenerInsumosProducto(int idProd) {
        // Consultar a tu DAO de insumos
        return "3 insumos";
    }
}