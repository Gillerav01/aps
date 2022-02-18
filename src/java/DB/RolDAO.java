package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Agricultor;
import modelo.Parcela;
import modelo.Rol;

public class RolDAO {

    private Rol rol;
    private Connection conn;

    public RolDAO() {
    }

    public RolDAO(Rol rol, Connection conn) {
        this.rol = rol;
        this.conn = conn;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public boolean crearRol(Rol rol) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("INSERT INTO `roles` (`idRol`, `nombreRol`) VALUES (NULL, '" + rol.getNombreRol() + "');");
            return true;
        }
    }

    public boolean borrarRol(Rol rol) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("DELETE FROM `roles` WHERE `roles`.`idRol` = " + rol.getIdRol() + "");
            return true;
        }
    }

    public boolean modificarRol(Rol rol, Rol rolModificado) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("UPDATE `roles` SET `nombreRol` = '" + rolModificado.getNombreRol() + "' WHERE `roles`.`idRol` = " + rol.getIdRol() + ";");
            return true;
        }
    }

    public ArrayList<Rol> verRoles() throws SQLException {
        ArrayList<Rol> roles = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM roles");
        while (result.next()) {
            roles.add(new Rol(result.getInt("idRol"), result.getString("nombreRol")));
        }
        return roles;
    }

    public ArrayList<Rol> verRolesUsuario(Agricultor agricultor) throws SQLException {
        ArrayList<Rol> roles = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT roles.idRol, roles.nombreRol FROM agricultores, roles, rolesagricultores WHERE agricultores.id = " + agricultor.getId() + " AND roles.idRol = rolesagricultores.idRol AND agricultores.id = rolesagricultores.idAgricultor");
        while (result.next()) {
            roles.add(new Rol(result.getInt("idRol"), result.getString("nombreRol")));
        }
        return roles;
    }

    public ArrayList<Rol> verRolesNoPoseidos(Agricultor agricultor) throws SQLException {
        ArrayList<Rol> roles = new ArrayList();
        ArrayList<Rol> verRolesNoPoseidos = new ArrayList();
        ArrayList<Rol> rolesUsuario = verRolesUsuario(agricultor);
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT nombreRol FROM roles");
        while (result.next()) {
            roles.add(new Rol(result.getInt("idRol"), result.getString("nombreRol")));
        }
        for (Rol rol : roles) {
            for (Rol rolesU : rolesUsuario) {
                if (rol.getIdRol() != rolesU.getIdRol()) {
                    verRolesNoPoseidos.add(rol);
                }
            }
        }
        return verRolesNoPoseidos;
    }
    
        public ArrayList<Rol> verRolesPoseidos(Agricultor agricultor) throws SQLException {
        ArrayList<Rol> roles = new ArrayList();
        ArrayList<Rol> verRolesPoseidos = new ArrayList();
        ArrayList<Rol> rolesUsuario = verRolesUsuario(agricultor);
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT nombreRol FROM roles");
        while (result.next()) {
            roles.add(new Rol(result.getInt("idRol"), result.getString("nombreRol")));
        }
        for (Rol rol : roles) {
            for (Rol rolesU : rolesUsuario) {
                if (rol.getIdRol() == rolesU.getIdRol()) {
                    verRolesPoseidos.add(rol);
                }
            }
        }
        return verRolesPoseidos;
    }

}
