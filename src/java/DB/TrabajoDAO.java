package DB;

import java.sql.Connection;
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
}
