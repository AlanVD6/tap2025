package vistas;

import com.example.modelos.Ticket;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

public class TicketView {

    public TicketView(int idOrden) {
        StringBuilder contenido = generarTicket(idOrden);
        mostrarTicket(contenido.toString());
        guardarTicketPDF(contenido.toString(), idOrden);
    }

    public StringBuilder generarTicket(int idOrden) {
        StringBuilder ticket = new StringBuilder();
        Ticket datos = new Ticket();
        ResultSet rs = datos.ticket(idOrden);  // Usar el idOrden recibido
        double total = 0.0;
        boolean firstRow = true;

        try {
            ticket.append("====================================\n");
            ticket.append("              TICKET\n");
            ticket.append("====================================\n");

            while (rs != null && rs.next()) {
                if (firstRow) {
                    String empleado = rs.getString(1); // e.nombre
                    String fecha = rs.getString(2);    // o.fecha
                    String hora = rs.getString(3);     // o.hora
                    int id = rs.getInt(4);             // o.idOrd
                    int mesa = rs.getInt(5);           // m.numero

                    ticket.append("ID Orden: ").append(id).append("\n");
                    ticket.append("Empleado: ").append(empleado).append("\n");
                    ticket.append("Fecha: ").append(fecha).append("  Hora: ").append(hora).append("\n");
                    ticket.append("Mesa: ").append(mesa).append("\n");
                    ticket.append("------------------------------------\n");
                    ticket.append("Producto   Cant.   Precio   Subtotal\n");
                    firstRow = false;
                }

                String producto = rs.getString(6); // p.producto
                double precio = rs.getDouble(7);   // p.precio
                int cantidad = rs.getInt(8);       // do.cantidad
                double subtotal = rs.getDouble(9); // subtotal

                ticket.append(String.format("%-10s %3d   $%6.2f  $%6.2f\n",
                        producto, cantidad, precio, subtotal));
                total += subtotal;
            }

            if (!firstRow) {
                ticket.append("------------------------------------\n");
                ticket.append(String.format("TOTAL:              $%.2f\n", total));
                ticket.append("====================================\n");
                ticket.append("¡Gracias por su compra!\n");
            } else {
                ticket.append("No se encontraron productos para la orden.\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            ticket.append("Error al generar el ticket.");
        }

        return ticket;
    }

    private void mostrarTicket(String contenido) {
        Stage ticketStage = new Stage();
        TextArea area = new TextArea();
        area.setText(contenido);
        area.setEditable(false);
        area.setStyle("-fx-font-size: 14px; -fx-padding: 20px;");

        Scene scene = new Scene(new StackPane(area), 400, 600);
        ticketStage.setScene(scene);
        ticketStage.setTitle("Ticket de Compra");
        ticketStage.show();
    }

    private void guardarTicketPDF(String contenido, int idOrden) {
        PDDocument document = new PDDocument();

        try {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.COURIER, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);

            float y = 700;
            for (String line : contenido.split("\n")) {
                if (y <= 50) {
                    contentStream.endText();
                    contentStream.close();

                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(PDType1Font.COURIER, 12);
                    contentStream.beginText();
                    y = 700;
                    contentStream.newLineAtOffset(50, y);
                }

                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -15);
                y -= 15;
            }

            contentStream.endText();
            contentStream.close();

            String fileName = "ticket_orden_" + idOrden + ".pdf";
            File outputFile = new File(fileName);
            document.save(outputFile);
            System.out.println("PDF guardado en: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error al guardar el PDF: " + e.getMessage());
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el documento PDF.");
            }
        }
    }
}
