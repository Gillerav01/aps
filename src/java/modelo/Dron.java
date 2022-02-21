package modelo;
 
public class Dron {
    private int id;
    private int idPiloto;
    private String modeloDron;
    private String marcaDron;

    public Dron() {
    }

    public Dron(int idPiloto, String modeloDron, String marcaDron) {
        this.idPiloto = idPiloto;
        this.modeloDron = modeloDron;
        this.marcaDron = marcaDron;
    }
    
    public Dron(int id, int idPiloto, String modeloDron, String marcaDron) {
        this.id = id;
        this.idPiloto = idPiloto;
        this.modeloDron = modeloDron;
        this.marcaDron = marcaDron;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPiloto() {
        return idPiloto;
    }

    public void setIdPiloto(int idPiloto) {
        this.idPiloto = idPiloto;
    }

    public String getModeloDron() {
        return modeloDron;
    }

    public void setModeloDron(String modeloDron) {
        this.modeloDron = modeloDron;
    }

    public String getMarcaDron() {
        return marcaDron;
    }

    public void setMarcaDron(String marcaDron) {
        this.marcaDron = marcaDron;
    }

    @Override
    public String toString() {
        return "Dron{" + "id=" + id + ", idPiloto=" + idPiloto + ", modeloDron=" + modeloDron + ", marcaDron=" + marcaDron + '}';
    }
    
}
