package com.peliscacbackend;

import java.sql.*; 
import java.sql.SQLException;
import java.sql.DriverManager;


public class Main {

    // private Connection conectar = null;
    // private final String usuario = "root";
    // private final String contrasenia = "";
    // private final String db = "proyecto_movies_2024";
    // private final String ip = "localhost"; 
    // private final String puerto = "3312";//Chequeen su puerto.
    // private final String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + db;
    // private ResultSet rs=null;
    // private Statement st = null;
   




    // public Connection establecerConexion() {
    //     String res = "id_pelicula Títulos de las películas   \n";
            
    //     try {
            
    //         Class.forName("com.mysql.cj.jdbc.Driver");
            
    //         conectar = DriverManager.getConnection(cadena, usuario, contrasenia);
            
    //         System.out.println("Conexion exitosa a: "+this.db);
    //         st = conectar.createStatement();
    //         rs = st.executeQuery("Select * from peliculas;");

              
            
    //         while(rs.next()){
    //             res += rs.getInt("id_pelicula") + "      "
    //                  + rs.getString("titulo") + "\n";  

                


    //         }
    //     System.out.println(res);
    //     } catch (ClassNotFoundException | SQLException e) {
    //         System.out.println("Error al conectar " + e.getMessage());
    //     }
    
    //     return conectar;
        
    // }
 
    // public void Desconexion(){
    //     try{
    //         rs.close(); 
    //         st.close(); 
    //         conectar.close();
    //         System.out.println("Desconexión de nuestra base de datos: "+this.db);
    //     }
    //     catch(SQLException e){
    //         System.out.println("Error al desconectar " + e.getMessage());
    //     }
    // }
    // public static void main(String[] args) {
    //     Main app = new Main();
    //     app.establecerConexion();
    //     app.Desconexion();

        

    // }
}