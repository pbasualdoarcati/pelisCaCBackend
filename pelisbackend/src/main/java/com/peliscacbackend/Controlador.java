package com.peliscacbackend;

import java.io.IOException;
import java.sql.*;
import java.util.*;


import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Statement;


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

    // Post- hay errores y consultas
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        Conexion conexion = new Conexion();
        Connection conn = conexion.getConnection();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Pelicula pelicula = mapper.readValue(request.getInputStream(), Pelicula.class);

            // Validación de la entrada
            if (pelicula.getTitulo() == null || pelicula.getDuracion() == null || pelicula.getImagen() == null ||
                pelicula.getSynopsis() == null || pelicula.getIdActor() == null || pelicula.getIdDirector() == null || pelicula.getIdGenero() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Todos los campos son requeridos.");
                return;
            }

            String query = "INSERT INTO peliculas (titulo, duracion, imagen, synopsis, idActor, idDirector, idGenero) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, pelicula.getTitulo());
            statement.setString(2, pelicula.getDuracion());
            statement.setString(3, pelicula.getImagen());
            statement.setString(4, pelicula.getSynopsis());
            statement.setInt(5, pelicula.getIdActor());
            statement.setInt(6, pelicula.getIdDirector());
            statement.setInt(7, pelicula.getIdGenero());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    Long idPeli = rs.getLong(1);
                    response.setContentType("application/json");
                    String json = mapper.writeValueAsString(idPeli);
                    response.getWriter().write(json);
                }
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("No se pudo insertar la película.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al insertar la película en la base de datos.");
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error al procesar la solicitud.");
        } finally {
            conexion.close();
        }
    }
}
