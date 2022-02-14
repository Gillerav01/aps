package modelo;

public class RolAgricultor {
    private int idAgricultor;
    private int idRol;

    public RolAgricultor() {
    }

    public RolAgricultor(int idAgricultor, int idRol) {
        this.idAgricultor = idAgricultor;
        this.idRol = idRol;
    }
    
    public int getIdAgricultor() {
        return idAgricultor;
    }

    public void setIdAgricultor(int idAgricultor) {
        this.idAgricultor = idAgricultor;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
}
