package vistas;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.example.modelos.DetalleOrdenDAO;
import com.example.modelos.ProductoDAO;

public class TicketView {

    public TicketView() {
        String ticketTexto = generarTicket();
        mostrarTicket(ticketTexto);
    }

    private String generarTicket() {
        DetalleOrdenDAO detDao = new DetalleOrdenDAO();
        ProductoDAO prodDao = new ProductoDAO();

        StringBuilder ticket = new StringBuilder();
        float total = 0;

        for (DetalleOrdenDAO detalle : detDao.SELECT()) {
            ProductoDAO producto = buscarProductoPorID(detalle.getIdProd());
            if (producto != null) {
                float subtotal = producto.getPrecio() * detalle.getCantidad();
                ticket.append(producto.getProducto())
                        .append(" x").append(detalle.getCantidad())
                        .append(" - $").append(String.format("%.2f", subtotal))
                        .append("\n");
                total += subtotal;
            }
        }

        ticket.append("\nTotal: $").append(String.format("%.2f", total));
        return ticket.toString();
    }

    private ProductoDAO buscarProductoPorID(int idProd) {
        ProductoDAO prodDao = new ProductoDAO();
        for (ProductoDAO p : prodDao.SELECT()) {
            if (p.getIdProd() == idProd) {
                return p;
            }
        }
        return null;
    }

    private void mostrarTicket(String ticketTexto) {
        Stage ticketStage = new Stage();
        TextArea area = new TextArea(ticketTexto);
        area.setEditable(false);
        area.setStyle("-fx-font-size: 14px; -fx-padding: 20px;");

        Scene scene = new Scene(new StackPane(area), 400, 600);
        ticketStage.setScene(scene);
        ticketStage.setTitle("Ticket de Compra");
        ticketStage.show();
    }
}
