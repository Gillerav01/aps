package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Agricultor;
import modelo.Rol;

public class AgricultorDAO {

    private Agricultor agricultor;
    private Connection conn;

    public AgricultorDAO() {
    }

    public AgricultorDAO(Agricultor agricultor, Connection conn) {
        this.agricultor = agricultor;
        this.conn = conn;
    }

    public Agricultor getAgricultor() {
        return agricultor;
    }

    public void setAgricultor(Agricultor agricultor) {
        this.agricultor = agricultor;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public boolean crearAgricultor(Agricultor agricultor) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("INSERT INTO `agricultores` (`id`, `nombre`, `apellido`, `dni`, `password`, `email`) VALUES (NULL, '" 
                    + agricultor.getNombre() 
                    + "', '" 
                    + agricultor.getApellido() 
                    + "', '" + agricultor.getDni() 
                    + "', '" + agricultor.getPassword() 
                    + "', '" + agricultor.getEmail() 
                    + "');");
            return true;
        }
    }

    public boolean borrarAgricultor(Agricultor agricultor) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("DELETE FROM `agricultores` WHERE `agricultores`.`id` = " 
                    + agricultor.getId() + ";");
            return true;
        }
    }

    public boolean modificarAgricultor(Agricultor agricultor, Agricultor agricultorModificado) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("UPDATE `agricultores` SET `nombre` = '" 
                    + agricultorModificado.getNombre() 
                    + "', `apellido` = '" 
                    + agricultorModificado.getApellido() 
                    + "', `dni` = '" + agricultorModificado.getDni()
                    + "', `password` = '" 
                    + agricultorModificado.getPassword() 
                    + "', `email` = '" + agricultorModificado.getEmail() 
                    + "' WHERE `agricultores`.`id` = " 
                    + agricultor.getId() 
                    + ";");
            return true;
        }
    }
    
    public Agricultor recuperarDatos(Agricultor agricultor) throws SQLException{
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM agricultores WHERE id = " + agricultor.getId());
        Agricultor agricultorRecuperado =  null;
        while (result.next()) {
            agricultorRecuperado = new Agricultor(result.getInt("id"), result.getString("nombre"), result.getString("apellido"), result.getString("dni"), result.getString("email"), result.getString("password"));
        }
        return agricultorRecuperado;
    }
}
