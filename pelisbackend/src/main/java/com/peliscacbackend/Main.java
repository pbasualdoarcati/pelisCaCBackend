package com.peliscacbackend;

import java.sql.*;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Main {

    private Connection conectar = null;
    private final String usuario = "root";
    private final String contraseña = "";
    private final String db = "proyecto_movies_2024";
    private final String ip = "localhost"; 
    private final String puerto = "3312";//Chequeen su puerto.
    private final String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + db;


    public Connection establecerConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, contraseña);
            System.out.println("Conexion exitosa");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar " + e.getMessage());
        }
        return conectar;
    }
    public static void main(String[] args) {
        Main app = new Main();
        app.establecerConexion();

    }
}