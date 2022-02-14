package DB;

import modelo.RolAgricultor;

public class RolAgricultorDAO {
    private RolAgricultor ra;

    public RolAgricultorDAO() {
    }

    public RolAgricultorDAO(RolAgricultor ra) {
        this.ra = ra;
    }

    public RolAgricultor getRa() {
        return ra;
    }

    public void setRa(RolAgricultor ra) {
        this.ra = ra;
    }
}
