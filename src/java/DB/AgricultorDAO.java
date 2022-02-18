package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import lib.utilidades;
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
    
    public ArrayList<Agricultor> recuperarUsuarios () throws SQLException{
        ArrayList<Agricultor> usuarios = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM agricultores");
        while (result.next()) {
            usuarios.add(new Agricultor(result.getInt("id"), result.getString("nombre"), result.getString("apellido"), result.getString("dni"), result.getString("email")));
        }
        return usuarios;
    }

    public Agricultor recuperarDatos(int id) throws SQLException {
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM agricultores WHERE id = " + id);
        Agricultor agricultorRecuperado = null;
        while (result.next()) {
            agricultorRecuperado = new Agricultor(result.getInt("id"), result.getString("nombre"), result.getString("apellido"), result.getString("dni"), result.getString("email"), result.getString("password"));
        }
        return agricultorRecuperado;
    }

    public ArrayList<Agricultor> recuperarPilotos() throws SQLException {
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT agricultores.id, agricultores.nombre, agricultores.apellido FROM agricultores, rolesagricultores WHERE rolesagricultores.idRol = 3 AND rolesagricultores.idAgricultor = agricultores.id");
        ArrayList<Agricultor> pilotos = new ArrayList();
        while (result.next()) {
            pilotos.add(new Agricultor(result.getInt("id"), result.getString("nombre"), result.getString("apellido"), result.getString("dni"), result.getString("email"), result.getString("password")));
        }
        return pilotos;
    }
    
    public Agricultor verificar(String login, String pwd) throws SQLException{
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT id, password FROM agricultores WHERE dni = " + login + " OR email = " + login + ";");
        if (result.getString("password").equals(utilidades.convertirSHA256(pwd))){
            return new Agricultor(result.getInt("id"), result.getString("nombre"), result.getString("apellido"), result.getString("dni"), result.getString("email"), result.getString("password"));
        }
        return null;
    }
    
}