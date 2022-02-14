package DB;

import modelo.Dron;
public class DronDAO {
    private Dron dron;

    public DronDAO() {
    }

    public DronDAO(Dron dron) {
        this.dron = dron;
    }

    public Dron getDron() {
        return dron;
    }

    public void setDron(Dron dron) {
        this.dron = dron;
    }
}
