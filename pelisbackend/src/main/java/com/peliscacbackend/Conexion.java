package com.peliscacbackend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private Connection connection;  // Objeto Connection para manejar la conexión a la base de datos

    // Constructor de la clase Conexion
    public Conexion() {
        try {
            // Paso 1: Cargar dinámicamente el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Paso 2: Establecer la conexión con la base de datos 'peliculas_cac_java' en localhost
            this.connection = DriverManager.getConnection(

                "jdbc:mysql://localhost:3312/proyecto_movies_2024",  // URL de conexión JDBC para MySQL
                "root",  // Nombre de usuario de la base de datos (cambia según tu configuración)
                ""  // Contraseña de la base de datos (cambia según tu configuración)
            );
            System.out.println("Connection established successfully.");  // Mensaje de éxito en la conexión
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  // Imprimir el error en caso de no encontrar el driver
            System.out.println("MySQL JDBC Driver not found.");
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir el error en caso de problemas con la conexión a la base de datos
            System.out.println("Failed to establish a database connection111.");
        }
    }

    // Método para obtener la conexión GETTER
    public Connection getConnection() {
        return connection;  // Devuelve el objeto Connection establecido
    }

    // Método para cerrar la conexión
    public void close() {
        try {
            // Verificar si la conexión no es nula y está abierta, entonces cerrarla
            if (connection != null && !connection.isClosed()) {
                connection.close();  // Cierra la conexión a la base de datos
                System.out.println("Connection closed successfully.");  // Mensaje de éxito al cerrar la conexión
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir el error en caso de problemas al cerrar la conexión
            System.out.println("Failed to close the database connection.");
        }
    }
}
