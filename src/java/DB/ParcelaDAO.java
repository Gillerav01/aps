package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Agricultor;
import modelo.Dron;
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

    /**
     * *
     * Crea una parcela nueva asignandosela al agricultor logueado
     *
     * @param parcela
     * @param agricultor
     * @return
     * @throws SQLException
     */
    public boolean crearParcela(Parcela parcela, Agricultor agricultor) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("INSERT INTO `parcelas` (`idParcela`, `nomParcela`, `area`, `idAgricultor`, `direccionArchivo`, `provincia`, `municipio`, `puntos`) VALUES (NULL, '" + parcela.getNomParcela() + "', '" + parcela.getArea() + "', '" + agricultor.getId() + "', '" + parcela.getDireccionArchivo() + "', '" + parcela.getProvincia() + "', '" + parcela.getMunicipio() + "', '" + parcela.getPuntos() + "');");
            return true;
        }
    }

    /**
     * *
     * Borra una parcela
     *
     * @param parcela
     * @return
     * @throws SQLException
     */
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

    /**
     * *
     * Modifica una parcela
     *
     * @param parcela
     * @param parcelaModificada
     * @return
     * @throws SQLException
     */
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

    public ArrayList<Parcela> recuperarParcelas(Agricultor agricultor) throws SQLException {
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM parcelas WHERE idAgricultor = " + agricultor.getId());
        ArrayList<Parcela> parcelas = new ArrayList();
        while (result.next()) {
            parcelas.add(new Parcela(result.getInt("idParcela"), result.getString("nomParcela"), result.getInt("area"),result.getInt("idAgricultor"), result.getString("direccionArchivo"), result.getInt("provincia"), result.getInt("municipio"), result.getString("puntos")));
        }
        return parcelas;
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
