package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class OrdenDAO {
    private int idOrden;
    private LocalDateTime fecha;
    private MesaDAO mesa;
    private EmpleadoDAO empleado;
    private ObservableList<DetalleOrdenDAO> detalles;
    private double total;

    // Constructores
    public OrdenDAO() {
        this.detalles = FXCollections.observableArrayList();
    }

    public OrdenDAO(int idOrden, LocalDateTime fecha, MesaDAO mesa, EmpleadoDAO empleado) {
        this();
        this.idOrden = idOrden;
        this.fecha = fecha;
        this.mesa = mesa;
        this.empleado = empleado;
    }

    // Métodos CRUD
    public boolean guardarOrden(List<ProductoDAO> productos) throws SQLException {
        if (mesa == null || empleado == null || productos == null || productos.isEmpty()) {
            throw new IllegalArgumentException("Datos incompletos para guardar la orden");
        }

        Connection connection = Conexion.connection;
        try {
            connection.setAutoCommit(false); // Iniciar transacción

            // 1. Guardar la orden principal
            String queryOrden = "INSERT INTO orden (fecha, idMesa, idEmpleado, total) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(queryOrden, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setInt(2, mesa.getIdMesa());
                stmt.setInt(3, empleado.getIdEmp());
                stmt.setDouble(4, calcularTotal(productos));

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    return false;
                }

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.idOrden = rs.getInt(1);
                    } else {
                        connection.rollback();
                        return false;
                    }
                }
            }

            // 2. Guardar los detalles de la orden
            String queryDetalle = "INSERT INTO detalleorden (idOrden, idProd, cantidad, precioUnitario) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(queryDetalle)) {
                for (ProductoDAO producto : productos) {
                    stmt.setInt(1, idOrden);
                    stmt.setInt(2, producto.getIdProd());
                    stmt.setInt(3, producto.getCantidad());
                    stmt.setDouble(4, producto.getPrecio());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            // 3. Actualizar estado de la mesa
            MesaDAO.cambiarEstadoMesa(mesa.getIdMesa(), "Ocupada");

            connection.commit(); // Confirmar transacción
            return true;

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public static boolean finalizarOrden(int idOrden) throws SQLException {
        // 1. Obtener la orden
        OrdenDAO orden = SELECT_POR_ID(idOrden);
        if (orden == null) {
            return false;
        }

        // 2. Cambiar estado de la mesa a disponible
        MesaDAO.cambiarEstadoMesa(orden.getMesa().getIdMesa(), "Disponible");

        // 3. Marcar orden como completada (podrías añadir un campo 'estado' en la tabla)
        String query = "UPDATE orden SET completada = 1 WHERE idOrden = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idOrden);
            return stmt.executeUpdate() > 0;
        }
    }

    // Métodos de consulta
    public static OrdenDAO SELECT_POR_ID(int idOrden) throws SQLException {
        String query = "SELECT * FROM orden WHERE idOrden = ?";
        OrdenDAO orden = new OrdenDAO();

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idOrden);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                orden.setIdOrden(rs.getInt("idOrden"));
                orden.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                orden.setDetalles(DetalleOrdenDAO.SELECT_POR_ORDEN(idOrden));
                orden.setTotal(rs.getDouble("total"));

                // Cargar mesa
                MesaDAO mesa = new MesaDAO();
                mesa.setIdMesa(rs.getInt("idMesa"));
                orden.setMesa(mesa);

                // Cargar empleado
                EmpleadoDAO emp = new EmpleadoDAO();
                emp.setIdEmp(rs.getInt("idEmpleado"));
                orden.setEmpleado(emp);
            }
        }
        return orden.getIdOrden() > 0 ? orden : null;
    }

    public static ObservableList<OrdenDAO> SELECT_POR_FECHA(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws SQLException {
        ObservableList<OrdenDAO> ordenes = FXCollections.observableArrayList();
        String query = "SELECT * FROM orden WHERE fecha BETWEEN ? AND ? ORDER BY fecha DESC";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(fechaInicio));
            stmt.setTimestamp(2, Timestamp.valueOf(fechaFin));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrdenDAO orden = new OrdenDAO();
                orden.setIdOrden(rs.getInt("idOrden"));
                orden.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                orden.setTotal(rs.getDouble("total"));

                // Cargar mesa
                MesaDAO mesa = new MesaDAO();
                mesa.setIdMesa(rs.getInt("idMesa"));
                orden.setMesa(mesa);

                // Cargar empleado
                EmpleadoDAO emp = new EmpleadoDAO();
                emp.setIdEmp(rs.getInt("idEmpleado"));
                orden.setEmpleado(emp);

                ordenes.add(orden);
            }
        }
        return ordenes;
    }

    public static ObservableList<OrdenDAO> SELECT_POR_MESA(int idMesa) throws SQLException {
        ObservableList<OrdenDAO> ordenes = FXCollections.observableArrayList();
        String query = "SELECT * FROM orden WHERE idMesa = ? ORDER BY fecha DESC";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idMesa);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrdenDAO orden = new OrdenDAO();
                orden.setIdOrden(rs.getInt("idOrden"));
                orden.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                orden.setTotal(rs.getDouble("total"));

                // Cargar mesa
                MesaDAO mesa = new MesaDAO();
                mesa.setIdMesa(rs.getInt("idMesa"));
                orden.setMesa(mesa);

                // Cargar empleado
                EmpleadoDAO emp = new EmpleadoDAO();
                emp.setIdEmp(rs.getInt("idEmpleado"));
                orden.setEmpleado(emp);

                ordenes.add(orden);
            }
        }
        return ordenes;
    }

    // Métodos auxiliares
    private double calcularTotal(List<ProductoDAO> productos) {
        return productos.stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidad())
                .sum();
    }

    // Getters y Setters
    public int getIdOrden() { return idOrden; }
    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public MesaDAO getMesa() { return mesa; }
    public void setMesa(MesaDAO mesa) { this.mesa = mesa; }

    public EmpleadoDAO getEmpleado() { return empleado; }
    public void setEmpleado(EmpleadoDAO empleado) { this.empleado = empleado; }

    public ObservableList<DetalleOrdenDAO> getDetalles() { return detalles; }
    public void setDetalles(ObservableList<DetalleOrdenDAO> detalles) {
        this.detalles = detalles;
        this.total = detalles.stream()
                .mapToDouble(d -> d.getPrecioUnitario() * d.getCantidad())
                .sum();
    }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    @Override
    public String toString() {
        return String.format("Orden #%d - Mesa %d - %s - $%.2f",
                idOrden,
                mesa != null ? mesa.getNumero() : 0,
                fecha != null ? fecha.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "",
                total);
    }
}
