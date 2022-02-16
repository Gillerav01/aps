package DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Agricultor;
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
    
    public boolean crearParcela(Parcela parcela, Agricultor agricultor) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("INSERT INTO `parcelas` (`idParcela`, `nomParcela`, `area`, `idAgricultor`, `direccionArchivo`, `provincia`, `municipio`, `puntos`) VALUES (NULL, '" + parcela.getNomParcela() + "', '" + parcela.getArea() + "', '" + agricultor.getId() + "', '" + parcela.getDireccionArchivo() + "', '" + parcela.getProvincia()+ "', '" + parcela.getMunicipio() + "', '" + parcela.getPuntos() + "');");
            return true;
        }
    }

    public boolean borrarParcela(Parcela parcela) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("DELETE FROM `parcelas` WHERE `parcelas`.`idParcela` = " + parcela.getId() + ";");
            return true;
        }
    }

    public boolean modificarParcela(Parcela parcela, Parcela parcelaModificada) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("UPDATE `parcelas` SET `nomParcela` = '" + parcelaModificada.getNomParcela() + "' WHERE `parcelas`.`idParcela` = " + parcela.getId() + ";");
            return true;
        }
    }
    
}
