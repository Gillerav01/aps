package DB;

import java.sql.Connection;
import modelo.Parcela;

public class ParcelaDAO {
    private Parcela parcela;
    private Connection conn;

    
    public ParcelaDAO() {
    }

    public ParcelaDAO(Parcela parcela, Connection conn) {
        this.parcela = parcela;
        this.conn = conn;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
