package DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Agricultor;
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

    public boolean crearDron(Dron dron, Agricultor agricultor) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("INSERT INTO `drones` (`id`, `idPiloto`, `modeloDron`, `marcaDron`) VALUES (NULL, '"
                    + agricultor.getId()
                    + "', '"
                    + dron.getModeloDron()
                    + "', '"
                    + dron.getMarcaDron()
                    + "');");
            return true;
        }
    }

    public boolean borrarDron(Dron dron) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("DELETE FROM `drones` WHERE `drones`.`id` = "
                    + dron.getId() + "");
            return true;
        }
    }

    public boolean modificarDron(Dron dron, Dron dronModificado) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("UPDATE `drones` SET `modeloDron` = '" + dronModificado.getModeloDron()
                    + "', `marcaDron` = '" + dronModificado.getMarcaDron()
                    + "' WHERE `drones`.`id` = " + dron.getId()
                    + ";");
            return true;
        }
    }

}
