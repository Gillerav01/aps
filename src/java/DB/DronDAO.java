package DB;

import java.sql.Connection;
import modelo.Dron;
public class DronDAO {
    private Dron dron;
    private Connection conn;


    public DronDAO() {
    }

    public DronDAO(Dron dron, Connection conn) {
        this.dron = dron;
        this.conn = conn;
    }

    public Dron getDron() {
        return dron;
    }

    public void setDron(Dron dron) {
        this.dron = dron;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
