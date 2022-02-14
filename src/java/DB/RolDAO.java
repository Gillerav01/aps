package DB;

import modelo.Rol;

public class RolDAO {
    private Rol rol;

    public RolDAO() {
    }

    public RolDAO(Rol rol) {
        this.rol = rol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
