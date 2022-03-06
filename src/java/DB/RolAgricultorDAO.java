package DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Agricultor;
import modelo.Parcela;
import modelo.Rol;
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

    /**
     * *
     * Le otogar un rol al agricultor seleccionado
     *
     * @param idRol
     * @param rol
     * @return
     * @throws SQLException
     */
    public boolean otogarRol(int idRol, int idAgricultor) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("INSERT INTO `rolesagricultores` (`idAgricultor`, `idRol`) VALUES ('" + idAgricultor + "', '" + idRol + "');");
            return true;
        }
    }

    /**
     * *
     * Le quita un rol al agricultor seleccionado
     *
     * @param rol
     * @param agricultor
     * @return
     * @throws SQLException
     */
    public boolean quitarRol(int idRol, int idAgricultor) throws SQLException {
        if (this.conn == null) {
            System.out.println("No existe una conexión con la base de datos.");
            return false;
        } else {
            Statement st = this.conn.createStatement();
            st.executeUpdate("DELETE FROM `rolesagricultores` WHERE `rolesagricultores`.`idAgricultor` = " + idAgricultor + " AND `rolesagricultores`.`idRol` = " + idRol + "");
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
