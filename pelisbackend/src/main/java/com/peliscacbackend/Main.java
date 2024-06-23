package com.peliscacbackend;

import java.sql.*;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Main {

    private Connection conectar = null;
    private final String usuario = "VA EL USUARIO DE CADA UNO";
    private final String contraseña = "VA LA CONTRASEÑA DE CADA UNO";
    private final String db = "proyecto_movies_2024";
    private final String ip = "localhost"; 
    private final String puerto = "3306";//Chequeen su puerto.
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