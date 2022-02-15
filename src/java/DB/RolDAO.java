package DB;

import java.sql.Connection;
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
}
