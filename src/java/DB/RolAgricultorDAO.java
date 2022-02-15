package DB;

import java.sql.Connection;
import modelo.RolAgricultor;

public class RolAgricultorDAO {
    private RolAgricultor ra;
    private Connection conn;
    
    public RolAgricultorDAO() {
    }

    public RolAgricultorDAO(RolAgricultor ra, Connection conn) {
        this.ra = ra;
        this.conn = conn;
    }

    public RolAgricultor getRa() {
        return ra;
    }

    public void setRa(RolAgricultor ra) {
        this.ra = ra;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    
}
