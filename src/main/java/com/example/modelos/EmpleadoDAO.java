package com.example.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private int idEmp;
    private String nombre;
    private String nacimiento;
    private String CURP;
    private String telefono;
    private float sueldo;
    private String usuario;
    private String contrasena;

    public int getIdEmp() { return idEmp; }
    public void setIdEmp(int idEmp) { this.idEmp = idEmp; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNacimiento() { return nacimiento; }
    public void setNacimiento(String nacimiento) { this.nacimiento = nacimiento; }

    public String getCURP() { return CURP; }
    public void setCURP(String CURP) { this.CURP = CURP; }

    public String getTelefono () { return telefono; }
    public void setTelefono (String telefono) { this.telefono = telefono; }

    public float getSueldo() { return sueldo; }
    public void setSueldo(float sueldo) { this.sueldo = sueldo; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public void INSERT () {

        String query = "INSERT INTO empleado(nombre, nacimiento, CURP, telefono, sueldo, usuario, contrasena) values ('"
                + nombre + "', '" + nacimiento + "', '" + CURP + "', '" + telefono + "', '" + sueldo + "', '" + usuario + "', '" + contrasena + "')";

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public void UPDATE() {

        String query = "UPDATE empleado SET nombre = '"
                + nombre + "', nacimiento = '" + nacimiento + "', CURP = '" + CURP + "', telefono = '" + telefono
                + "', sueldo = '" + sueldo + "', usuario = '" + usuario + "', contrasena = '" + contrasena
                + "' WHERE idEmp = " + idEmp;

        try {

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void DELETE(){
        String query = "DELETE FROM empleado WHERE idEmp = " + idEmp;

        try{

            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    public ObservableList<EmpleadoDAO> SELECT() {

        String query = "SELECT * FROM empleado";

        ObservableList<EmpleadoDAO> listaC = FXCollections.observableArrayList();
        EmpleadoDAO objC;

        try {

            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {

                objC = new EmpleadoDAO();

                objC.setIdEmp(res.getInt("idEmp"));
                objC.setNombre(res.getString("nombre"));
                objC.setNacimiento(res.getString("nacimiento"));
                objC.setCURP(res.getString("CURP"));
                objC.setTelefono(res.getString("telefono"));
                objC.setSueldo(res.getFloat("sueldo"));
                objC.setUsuario(res.getString("usuario"));
                objC.setContrasena(res.getString("contrasena"));

                listaC.add(objC);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return listaC;
    }

    public List<Integer> obtenerIdsEmpleados() {
        List<Integer> ids = new ArrayList<>();
        String query = "SELECT idEmp FROM empleado";

        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                ids.add(res.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ids;
    }


}