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

    /**
     * *
     * Crea un agricultor en la base de datos
     *
     * @param agricultor
     * @return
     * @throws SQLException
     */
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

    /**
     * *
     * Borra un agricultor de la base de datos
     *
     * @param agricultor
     * @return
     * @throws SQLException
     */
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

    /**
     * *
     * Modifica un agricultor de la base de datos
     *
     * @param agricultor
     * @param agricultorModificado
     * @return
     * @throws SQLException
     */
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

    /**
     * *
     * Recupera todos los usuarios de la base de datos
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<Agricultor> recuperarUsuarios() throws SQLException {
        ArrayList<Agricultor> usuarios = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM agricultores");
        while (result.next()) {
            usuarios.add(new Agricultor(result.getInt("id"), result.getString("nombre"), result.getString("apellido"), result.getString("dni"), result.getString("email")));
        }
        return usuarios;
    }

    /**
     * *
     * Recupera los datos de un agricultor y lo devuelve en forma de objeto
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public Agricultor recuperarDatos(int id) throws SQLException {
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM agricultores WHERE id = " + id);
        Agricultor agricultorRecuperado = null;
        while (result.next()) {
            agricultorRecuperado = new Agricultor(result.getInt("id"), result.getString("nombre"), result.getString("apellido"), result.getString("dni"), result.getString("email"), result.getString("password"));
        }
        return agricultorRecuperado;
    }

    /**
     * *
     * Recupera todos los pilotos de la base de datos
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<Agricultor> recuperarPilotos() throws SQLException {
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT agricultores.id, agricultores.nombre, agricultores.apellido FROM agricultores, rolesagricultores WHERE rolesagricultores.idRol = 3 AND rolesagricultores.idAgricultor = agricultores.id");
        ArrayList<Agricultor> pilotos = new ArrayList();
        while (result.next()) {
            pilotos.add(new Agricultor(result.getInt("id"), result.getString("nombre"), result.getString("apellido"), result.getString("dni"), result.getString("email"), result.getString("password")));
        }
        return pilotos;
    }

    /**
     * *
     * Verifica al usuario y devuelve un objeto con la información del usuario
     * recien logueado
     *
     * @param login
     * @param pwd
     * @return
     * @throws SQLException
     */
    public Agricultor verificar(String login, String pwd) throws SQLException {
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM agricultores WHERE dni = '" + login + "' OR email = '" + login + "'");
        while (result.next()) {
            if (result.getString("password").equals(utilidades.convertirSHA256(pwd))) {
                return new Agricultor(result.getInt("id"), result.getString("nombre"), result.getString("apellido"), result.getString("dni"), result.getString("email"), result.getString("password"));
            }
        }

        return null;
    }

    public Connection cerrarConexion() {
        try {
            this.conn.close();
            System.out.println("Cerrando conexion" + this.conn);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        this.conn = null;
        return this.conn;
    }

}
