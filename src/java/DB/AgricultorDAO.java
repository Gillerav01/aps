package DB;

import modelo.Agricultor;

public class AgricultorDAO {
    private Agricultor agricultor;

    public AgricultorDAO() {
    }

    public AgricultorDAO(Agricultor agricultor) {
        this.agricultor = agricultor;
    }

    public Agricultor getAgricultor() {
        return agricultor;
    }

    public void setAgricultor(Agricultor agricultor) {
        this.agricultor = agricultor;
    }
}
