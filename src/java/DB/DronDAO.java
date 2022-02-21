package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    /**
     * *
     * Crea un dron y se lo asigna al agricultor que lo ha creado
     *
     * @param dron
     * @param agricultor
     * @return
     * @throws SQLException
     */
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

    /**
     * *
     * Borra un dron
     *
     * @param dron
     * @return
     * @throws SQLException
     */
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

    /**
     * *
     * Modifica un dron
     *
     * @param dron
     * @param dronModificado
     * @return
     * @throws SQLException
     */
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

    public ArrayList<Dron> recuperarDrones(Agricultor agricultor) throws SQLException {
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM drones WHERE idPiloto = " + agricultor.getId());
        ArrayList<Dron> drones = new ArrayList();
        while (result.next()) {
            drones.add(new Dron(result.getInt("id"), result.getInt("idPiloto"), result.getString("modeloDron"), result.getString("marcaDron")));
        }
        return drones;
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
