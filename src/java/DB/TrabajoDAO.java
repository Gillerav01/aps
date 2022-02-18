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
    
    public boolean crearTrabajo(Parcela parcela, Agricultor piloto, Trabajo trabajo) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("INSERT INTO `trabajos` (`idTrabajo`, `idParcela`, `idPiloto`, `idAgricultor`, `idDron`, `tipoTarea`, `fechaRegistro`, `fechaRealizacion`) VALUES (NULL, '" + parcela.getId() + "', '$idPiloto', '" + piloto.getId() + "', NULL, '" + trabajo.getTipoTrabajo() + "', current_timestamp(), NULL);");
            return true;
        }
    }

    public boolean borrarTrabajo(Trabajo trabajo) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("DELETE FROM `trabajos` WHERE idTrabajo = " + trabajo.getIdTrabajo() +"");
            return true;
        }
    }
    
    public ArrayList<Trabajo> verTrabajos() throws SQLException {
        ArrayList<Trabajo> trabajos = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos");
        while (result.next()) {
            trabajos.add(new Trabajo(result.getInt("idTrabajo"), result.getInt("idParcela"), result.getInt("idPiloto"), result.getInt("idAgricultor"), result.getInt("idDron"), result.getString("tipoTarea"), result.getDate("fechaRegistro"), result.getDate("fechaRealizacion")));
        }
        return trabajos;
    }

    public ArrayList<Trabajo> verTrabajosAgricultorPendiente(Agricultor agricultor) throws SQLException {
        ArrayList<Trabajo> trabajos = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos WHERE idAgricultor = " + agricultor.getId() + " AND fechaRealizacion IS null");
        while (result.next()) {
            trabajos.add(new Trabajo(result.getInt("idTrabajo"), result.getInt("idParcela"), result.getInt("idPiloto"), result.getInt("idAgricultor"), result.getInt("idDron"), result.getString("tipoTarea"), result.getDate("fechaRegistro"), result.getDate("fechaRealizacion")));
        }
        return trabajos;
    }

    public ArrayList<Trabajo> verTrabajosAgricultorFinalizado(Agricultor agricultor) throws SQLException {
        ArrayList<Trabajo> trabajos = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos WHERE idAgricultor = " + agricultor.getId() + " AND fechaRealizacion IS NOT null");
        while (result.next()) {
            trabajos.add(new Trabajo(result.getInt("idTrabajo"), result.getInt("idParcela"), result.getInt("idPiloto"), result.getInt("idAgricultor"), result.getInt("idDron"), result.getString("tipoTarea"), result.getDate("fechaRegistro"), result.getDate("fechaRealizacion")));
        }
        return trabajos;
    }

    public ArrayList<Trabajo> verTrabajosPilotoPendientes(Agricultor agricultor) throws SQLException {
        ArrayList<Trabajo> trabajos = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos WHERE idPiloto = " + agricultor.getId() + " AND fechaRealizacion IS null");
        while (result.next()) {
            trabajos.add(new Trabajo(result.getInt("idTrabajo"), result.getInt("idParcela"), result.getInt("idPiloto"), result.getInt("idAgricultor"), result.getInt("idDron"), result.getString("tipoTarea"), result.getDate("fechaRegistro"), result.getDate("fechaRealizacion")));
        }
        return trabajos;
    }

    public ArrayList<Trabajo> verTrabajosPilotoFinalizado(Agricultor agricultor) throws SQLException {
        ArrayList<Trabajo> trabajos = new ArrayList();
        Statement stmt = this.conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM trabajos WHERE idPiloto = " + agricultor.getId() + " AND fechaRealizacion IS NOT null");
        while (result.next()) {
            trabajos.add(new Trabajo(result.getInt("idTrabajo"), result.getInt("idParcela"), result.getInt("idPiloto"), result.getInt("idAgricultor"), result.getInt("idDron"), result.getString("tipoTarea"), result.getDate("fechaRegistro"), result.getDate("fechaRealizacion")));
        }
        return trabajos;
    }

    public boolean realizarTrabajo (Trabajo trabajo, Dron dron) throws SQLException{
         if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("UPDATE `trabajos` SET `idDron` = '" + dron.getId() + "', `fechaRealizacion` = current_timestamp() WHERE `trabajos`.`idTrabajo` = " + trabajo.getIdTrabajo());
            return true;
        }
    }
}