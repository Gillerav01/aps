package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Agricultor;
import modelo.Rol;
import modelo.Trabajo;

public class TrabajoDAO {

    private Trabajo trabajo;
    private Connection conn;

    public TrabajoDAO() {
    }

    public TrabajoDAO(Trabajo trabajo, Connection conn) {
        this.trabajo = trabajo;
        this.conn = conn;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public boolean crearTrabajo(Rol rol) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("INSERT INTO `roles` (`idRol`, `nombreRol`) VALUES (NULL, '" + rol.getNombreRol() + "');");
            return true;
        }
    }

    public boolean borrarTrabajo(Rol rol) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("DELETE FROM `roles` WHERE `roles`.`idRol` = " + rol.getIdRol() + "");
            return true;
        }
    }

    public boolean modificarTrabajo(Rol rol, Rol rolModificado) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("UPDATE `roles` SET `nombreRol` = '" + rolModificado.getNombreRol() + "' WHERE `roles`.`idRol` = " + rol.getIdRol() + ";");
            return true;
        }
    }

    public ArrayList<Rol> verTrabajos() throws SQLException {
        ArrayList<Rol> roles = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM roles");
        while (result.next()) {
            roles.add(new Rol(result.getInt("idRol"), result.getString("nombreRol")));
        }
        return roles;
    }

    public ArrayList<Rol> verTrabajosAgricultorPendiente(Agricultor agricultor) throws SQLException {
        ArrayList<Rol> roles = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos WHERE idAgricultor = " + agricultor.getId() + " AND fechaRealizacion IS null");
        while (result.next()) {
            roles.add(new Rol(result.getInt("idRol"), result.getString("nombreRol")));
        }
        return roles;
    }

    public ArrayList<Rol> verTrabajosAgricultorFinalizado(Agricultor agricultor) throws SQLException {
        ArrayList<Rol> roles = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos WHERE idAgricultor = " + agricultor.getId() + " AND fechaRealizacion IS NOT null");
        while (result.next()) {
            roles.add(new Rol(result.getInt("idRol"), result.getString("nombreRol")));
        }
        return roles;
    }

    public ArrayList<Rol> verTrabajosPilotoPendientes(Agricultor agricultor) throws SQLException {
        ArrayList<Rol> roles = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos WHERE idPiloto = " + agricultor.getId() + " AND fechaRealizacion IS null");
        while (result.next()) {
            roles.add(new Rol(result.getInt("idRol"), result.getString("nombreRol")));
        }
        return roles;
    }

    public ArrayList<Rol> verTrabajosPilotoFinalizado(Agricultor agricultor) throws SQLException {
        ArrayList<Rol> roles = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos WHERE idPiloto = " + agricultor.getId() + " AND fechaRealizacion IS NOT null");
        while (result.next()) {
            roles.add(new Rol(result.getInt("idRol"), result.getString("nombreRol")));
        }
        return roles;
    }

}
