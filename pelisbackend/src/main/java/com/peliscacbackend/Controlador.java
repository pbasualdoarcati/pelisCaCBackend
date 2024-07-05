package com.peliscacbackend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

// Clase Controlador: Maneja las peticiones HTTP para insertar y recuperar películas.
@WebServlet("/peliculas") // Anotación que mapea este servlet a la URL "/peliculas"
public class Controlador extends HttpServlet { // Declaración de la clase Controlador que extiende HttpServlet

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // Crear una nueva conexión a la base de datos (REVISAR CONEXION DE CADA UNO, EL
        // MIO TIENE PASS, Y TAL VEZ USTEDES TENGAN LA DB CON OTRO NOMBRE, LA MIA SE
        // LLAMA CAC)
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConnection();

        try {
            ObjectMapper mapper = new ObjectMapper();
            // OBJETO Pelicula
            Pelicula pelicula = mapper.readValue(request.getReader(), Pelicula.class);

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

            // ELIMINA DE LA QUERY LA ULTIMA COMA Y EL ESPACIO
            if (params.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("No fields provided for update.");
                return;
            }

            // AREGA A LA QUERY EL FILTRO POR ID
            query.setLength(query.length() - 2);
            query.append(" WHERE id = ?");
            params.add(pelicula.getIdPelicula());

            PreparedStatement statement = conn.prepareStatement(query.toString());
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }

            // DEBUG DE LA QUERY FINAL Y LOS PARAMETROS
            System.out.println("Final SQL Query: " + query.toString());
            System.out.println("Parameters: " + params);

            // EJECUTA LA QUERY, SI NO SE UPDATEARON CAMPOS LA API RESPONDE NOTFOUND 404, SI SE APLICARON CAMBIOS DEVUELVE UN 200
            int rowsUpdated = statement.executeUpdate(); 
            if (rowsUpdated > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Movie updated successfully.");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Movie not found.");
            }
        } catch (SQLException e) {
            // DEBUG DEL ERROR EN CASO DE CONFLICTOS CON LA DB, DEVUELVE UN 500
            e.printStackTrace(); 
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error updating movie.");
        } catch (IOException e) {
            // DEBUG DEL ERROR EN CASO DE CONFLICTOS, DEVUELVE UN 400
            e.printStackTrace(); 
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);     
            response.getWriter().write("Error processing request.");
        } finally {
            conexion.close(); // Close the database connection after the operation
        }
    }

}
