package DB;

import modelo.Parcela;

public class ParcelaDAO {
    private Parcela parcela;

    public ParcelaDAO() {
    }

    public ParcelaDAO(Parcela parcela) {
        this.parcela = parcela;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }
    
    
}
