package com.example.modelos;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static String DB = "Restaurantec";
    private static String USER = "admin";
    private static String PWD = "1234";
    private static String HOST = "localhost"; // 127.0.0.1
    private static String PORT = "3306";

    public static Connection connection;

    public static void createConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"+HOST+":"+PORT+"/"+DB,USER,PWD);
            System.out.println("Conexión establecida :v");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}