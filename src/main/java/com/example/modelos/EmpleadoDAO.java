package com.example.modelos;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class EmpleadoDAO {
    private int idEmp;
    private StringProperty nombre = new SimpleStringProperty();
    private StringProperty usuario = new SimpleStringProperty();
    private StringProperty curp = new SimpleStringProperty();
    private StringProperty telefono = new SimpleStringProperty();
    private DoubleProperty sueldo = new SimpleDoubleProperty();
    private BooleanProperty esAdmin = new SimpleBooleanProperty();
    private ObjectProperty<Date> nacimiento = new SimpleObjectProperty<>();

    // Constructores
    public EmpleadoDAO() {}

    public EmpleadoDAO(String nombre, String usuario, boolean esAdmin) {
        this.nombre.set(nombre);
        this.usuario.set(usuario);
        this.esAdmin.set(esAdmin);
    }

    // Métodos CRUD
    public void insertar() throws SQLException {
        String query = "INSERT INTO empleado (nombre, usuario, contraseña, curp, telefono, sueldo, esAdmin, nacimiento) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, getNombre());
            stmt.setString(2, getUsuario());
            stmt.setString(3, generarHashContraseña(getUsuario())); // Contraseña inicial = usuario
            stmt.setString(4, getCurp());
            stmt.setString(5, getTelefono());
            stmt.setDouble(6, getSueldo());
            stmt.setBoolean(7, isEsAdmin());
            stmt.setDate(8, getNacimiento());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.idEmp = rs.getInt(1);
                }
            }
        }
    }

    public void actualizar() throws SQLException {
        String query = "UPDATE empleado SET nombre = ?, usuario = ?, curp = ?, telefono = ?, " +
                "sueldo = ?, esAdmin = ?, nacimiento = ? WHERE idEmp = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, getNombre());
            stmt.setString(2, getUsuario());
            stmt.setString(3, getCurp());
            stmt.setString(4, getTelefono());
            stmt.setDouble(5, getSueldo());
            stmt.setBoolean(6, isEsAdmin());
            stmt.setDate(7, getNacimiento());
            stmt.setInt(8, getIdEmp());
            stmt.executeUpdate();
        }
    }

    public void eliminar() throws SQLException {
        String query = "DELETE FROM empleado WHERE idEmp = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, getIdEmp());
            stmt.executeUpdate();
        }
    }

    // Métodos estáticos
    public static EmpleadoDAO autenticar(String usuario, String contraseña) throws SQLException {
        String query = "SELECT * FROM empleado WHERE usuario = ? AND contraseña = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, usuario);
            stmt.setString(2, generarHashContraseña(contraseña));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearDesdeResultSet(rs);
            }
        }
        return null;
    }

    public static ObservableList<EmpleadoDAO> obtenerTodos() throws SQLException {
        ObservableList<EmpleadoDAO> empleados = FXCollections.observableArrayList();
        String query = "SELECT * FROM empleado ORDER BY nombre";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                empleados.add(mapearDesdeResultSet(rs));
            }
        }
        return empleados;
    }

    public static void cambiarContraseña(int idEmpleado, String nuevaContraseña) throws SQLException {
        String query = "UPDATE empleado SET contraseña = ? WHERE idEmp = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, generarHashContraseña(nuevaContraseña));
            stmt.setInt(2, idEmpleado);
            stmt.executeUpdate();
        }
    }

    // Métodos auxiliares
    private static EmpleadoDAO mapearDesdeResultSet(ResultSet rs) throws SQLException {
        EmpleadoDAO emp = new EmpleadoDAO();
        emp.setIdEmp(rs.getInt("idEmp"));
        emp.setNombre(rs.getString("nombre"));
        emp.setUsuario(rs.getString("usuario"));
        emp.setCurp(rs.getString("curp"));
        emp.setTelefono(rs.getString("telefono"));
        emp.setSueldo(rs.getDouble("sueldo"));
        emp.setEsAdmin(rs.getBoolean("esAdmin"));
        emp.setNacimiento(rs.getDate("nacimiento"));
        return emp;
    }

    private static String generarHashContraseña(String contraseña) {
        // En producción usar BCrypt o similar
        return Integer.toString(contraseña.hashCode()); // Ejemplo básico
    }

    // Getters y Setters
    public int getIdEmp() { return idEmp; }
    public void setIdEmp(int idEmp) { this.idEmp = idEmp; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public StringProperty nombreProperty() { return nombre; }

    public String getUsuario() { return usuario.get(); }
    public void setUsuario(String usuario) { this.usuario.set(usuario); }
    public StringProperty usuarioProperty() { return usuario; }

    public String getCurp() { return curp.get(); }
    public void setCurp(String curp) { this.curp.set(curp); }
    public StringProperty curpProperty() { return curp; }

    public String getTelefono() { return telefono.get(); }
    public void setTelefono(String telefono) { this.telefono.set(telefono); }
    public StringProperty telefonoProperty() { return telefono; }

    public double getSueldo() { return sueldo.get(); }
    public void setSueldo(double sueldo) { this.sueldo.set(sueldo); }
    public DoubleProperty sueldoProperty() { return sueldo; }

    public boolean isEsAdmin() { return esAdmin.get(); }
    public void setEsAdmin(boolean esAdmin) { this.esAdmin.set(esAdmin); }
    public BooleanProperty esAdminProperty() { return esAdmin; }

    public Date getNacimiento() { return nacimiento.get(); }
    public void setNacimiento(Date nacimiento) { this.nacimiento.set(nacimiento); }
    public ObjectProperty<Date> nacimientoProperty() { return nacimiento; }

    @Override
    public String toString() {
        return getNombre() + " (" + getUsuario() + ")";
    }
}
