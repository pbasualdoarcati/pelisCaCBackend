package com.peliscacbackend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Importación de Statement
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet; // Importación de WebServlet para la anotación que mapea este servlet a una URL específica
import javax.servlet.http.HttpServlet; // Importación de HttpServlet para extender esta clase y manejar peticiones HTTP
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

// Clase Controlador: Maneja las peticiones HTTP para insertar y recuperar películas.
@WebServlet("/peliculas") // Anotación que mapea este servlet a la URL "/peliculas"
public class Controlador extends HttpServlet { // Declaración de la clase Controlador que extiende HttpServlet

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configurar cabeceras CORS
        response.setHeader("Access-Control-Allow-Origin", "*"); // Permitir acceso desde cualquier origen
        response.setHeader("Access-Control-Allow-Methods", "*"); // Métodos permitidos
        response.setHeader("Access-Control-Allow-Headers", "Content-Type"); // Cabeceras permitidas

        Conexion conexion = new Conexion(); // Crear una nueva conexión a la base de datos
        Connection conn = conexion.getConnection(); // Obtener la conexión establecida

        try {
            ObjectMapper mapper = new ObjectMapper(); // Create an ObjectMapper to read the request body
            Pelicula pelicula = mapper.readValue(request.getReader(), Pelicula.class); // Convert JSON request body to
                                                                                       // Pelicula object

            StringBuilder query = new StringBuilder("UPDATE movies SET ");
            List<Object> params = new ArrayList<>();
            if (pelicula.getTitulo() != null) {
                query.append("title = ?, ");
                params.add(pelicula.getTitulo());
            }
            if (pelicula.getDuracion() != null) {
                query.append("runtime = ?, ");
                params.add(pelicula.getDuracion());
            }
            if (pelicula.getImagen() != null) {
                query.append("poster_path = ?, ");
                params.add(pelicula.getImagen());
            }
            if (pelicula.getSynopsis() != null) {
                query.append("overview = ?, ");
                params.add(pelicula.getSynopsis());
            }
            if (pelicula.getIdDirector() != null) {
                query.append("idDirector = ?, ");
                params.add(pelicula.getIdDirector());
            }

            // Remove the last comma and space
            if (params.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("No fields provided for update.");
                return;
            }
            query.setLength(query.length() - 2);
            query.append(" WHERE id = ?");
            params.add(pelicula.getIdPelicula());

            System.out.println("Final SQL Query: " + query.toString());
            System.out.println("Parameters: " + params);

            PreparedStatement statement = conn.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }

            int rowsUpdated = statement.executeUpdate(); // Ejecutar la consulta de actualización en la base de datos
            if (rowsUpdated > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Movie updated successfully.");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Movie not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the error in case of database issues
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Set the HTTP response status to 500
                                                                    // (INTERNAL_SERVER_ERROR)
            response.getWriter().write("Error updating movie.");
        } catch (IOException e) {
            e.printStackTrace(); // Print the error in case of input/output issues
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Set the HTTP response status to 500
                                                                              // (INTERNAL_SERVER_ERROR)
            response.getWriter().write("Error processing request.");
        } finally {
            conexion.close(); // Close the database connection after the operation
        }
    }

}
