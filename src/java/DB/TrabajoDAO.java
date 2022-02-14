package DB;

import modelo.Trabajo;

public class TrabajoDAO {
    private Trabajo trabajo;

    public TrabajoDAO() {
    }

    public TrabajoDAO(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }
}
