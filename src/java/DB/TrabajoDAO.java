package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Agricultor;
import modelo.Dron;
import modelo.Parcela;
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

    /**
     * *
     * Crea un nuevo trabajo
     *
     * @param parcela
     * @param agricultor
     * @param piloto
     * @param trabajo
     * @return
     * @throws SQLException
     */
    public boolean crearTrabajo(Parcela parcela, Agricultor agricultor, Agricultor piloto, Trabajo trabajo) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("INSERT INTO `trabajos` (`idTrabajo`, `idParcela`, `idPiloto`, `idAgricultor`, `idDron`, `tipoTarea`, `fechaRegistro`, `fechaRealizacion`) VALUES (NULL, '" + parcela.getId() + "', '" + piloto.getId() + "', '" + agricultor.getId() + "', NULL, '" + trabajo.getTipoTrabajo() + "', 'CURRENT_TIMESTAMP()', NULL);");
            return true;
        }
    }

    /**
     * *
     * Borra un trabajo
     *
     * @param trabajo
     * @return
     * @throws SQLException
     */
    public boolean borrarTrabajo(Trabajo trabajo) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("DELETE FROM `trabajos` WHERE idTrabajo = " + trabajo.getIdTrabajo() + "");
            return true;
        }
    }

    /**
     * *
     * Te permite ver todos los trabajos
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<Trabajo> verTrabajos() throws SQLException {
        ArrayList<Trabajo> trabajos = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos");
        while (result.next()) {
            trabajos.add(new Trabajo(result.getInt("idTrabajo"), result.getInt("idParcela"), result.getInt("idPiloto"), result.getInt("idAgricultor"), result.getInt("idDron"), result.getString("tipoTarea"), result.getDate("fechaRegistro"), result.getDate("fechaRealizacion")));
        }
        return trabajos;
    }

    /**
     * *
     * Te permite ver todos los trabajos pendientes de un agricultor
     *
     * @param agricultor
     * @return
     * @throws SQLException
     */
    public ArrayList<Trabajo> verTrabajosAgricultorPendiente(Agricultor agricultor) throws SQLException {
        ArrayList<Trabajo> trabajos = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos WHERE idAgricultor = " + agricultor.getId() + " AND fechaRealizacion IS null");
        while (result.next()) {
            trabajos.add(new Trabajo(result.getInt("idTrabajo"), result.getInt("idParcela"), result.getInt("idPiloto"), result.getInt("idAgricultor"), result.getInt("idDron"), result.getString("tipoTarea"), result.getDate("fechaRegistro"), result.getDate("fechaRealizacion")));
        }
        return trabajos;
    }

    /**
     * *
     * Te permite ver los trabajos de un agricultor que han sido finalizados
     *
     * @param agricultor
     * @return
     * @throws SQLException
     */
    public ArrayList<Trabajo> verTrabajosAgricultorFinalizado(Agricultor agricultor) throws SQLException {
        ArrayList<Trabajo> trabajos = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos WHERE idAgricultor = " + agricultor.getId() + " AND fechaRealizacion IS NOT null");
        while (result.next()) {
            trabajos.add(new Trabajo(result.getInt("idTrabajo"), result.getInt("idParcela"), result.getInt("idPiloto"), result.getInt("idAgricultor"), result.getInt("idDron"), result.getString("tipoTarea"), result.getDate("fechaRegistro"), result.getDate("fechaRealizacion")));
        }
        return trabajos;
    }

    /**
     * *
     * Te permite ver los trabajos pendiente de un piloto
     *
     * @param agricultor
     * @return
     * @throws SQLException
     */
    public ArrayList<Trabajo> verTrabajosPilotoPendientes(Agricultor agricultor) throws SQLException {
        ArrayList<Trabajo> trabajos = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos WHERE idPiloto = " + agricultor.getId() + " AND fechaRealizacion IS null");
        while (result.next()) {
            trabajos.add(new Trabajo(result.getInt("idTrabajo"), result.getInt("idParcela"), result.getInt("idPiloto"), result.getInt("idAgricultor"), result.getInt("idDron"), result.getString("tipoTarea"), result.getDate("fechaRegistro"), result.getDate("fechaRealizacion")));
        }
        return trabajos;
    }

    /**
     * *
     * Te permite ver los trabajos que ha finalizado un piloto
     *
     * @param agricultor
     * @return
     * @throws SQLException
     */
    public ArrayList<Trabajo> verTrabajosPilotoFinalizado(Agricultor agricultor) throws SQLException {
        ArrayList<Trabajo> trabajos = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos WHERE idPiloto = " + agricultor.getId() + " AND fechaRealizacion IS NOT null");
        while (result.next()) {
            trabajos.add(new Trabajo(result.getInt("idTrabajo"), result.getInt("idParcela"), result.getInt("idPiloto"), result.getInt("idAgricultor"), result.getInt("idDron"), result.getString("tipoTarea"), result.getDate("fechaRegistro"), result.getDate("fechaRealizacion")));
        }
        return trabajos;
    }

    /**
     * *
     * Te permite realizar un trabajo
     *
     * @param trabajo
     * @param dron
     * @return
     * @throws SQLException
     */
    public boolean realizarTrabajo(Trabajo trabajo, Dron dron) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("UPDATE `trabajos` SET `idDron` = '" + dron.getId() + "', `fechaRealizacion` = current_timestamp() WHERE `trabajos`.`idTrabajo` = " + trabajo.getIdTrabajo());
            return true;
        }
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
