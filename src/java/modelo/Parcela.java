package modelo;

public class Parcela {
    private int id;
    private String nomParcela;
    private int area;
    private int idAgricultor;
    private String direccionArchivo;
    private int provincia;
    private int municipio;
    private String puntos;

    public Parcela() {
    }

    public Parcela(String nomParcela, int area, int idAgricultor, String direccionArchivo, int provincia, int municipio, String puntos) {
        this.nomParcela = nomParcela;
        this.area = area;
        this.idAgricultor = idAgricultor;
        this.direccionArchivo = direccionArchivo;
        this.provincia = provincia;
        this.municipio = municipio;
        this.puntos = puntos;
    }
    
    public Parcela(int id, String nomParcela, int area, int idAgricultor, String direccionArchivo, int provincia, int municipio, String puntos) {
        this.id = id;
        this.nomParcela = nomParcela;
        this.area = area;
        this.idAgricultor = idAgricultor;
        this.direccionArchivo = direccionArchivo;
        this.provincia = provincia;
        this.municipio = municipio;
        this.puntos = puntos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomParcela() {
        return nomParcela;
    }

    public void setNomParcela(String nomParcela) {
        this.nomParcela = nomParcela;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getIdAgricultor() {
        return idAgricultor;
    }

    public void setIdAgricultor(int idAgricultor) {
        this.idAgricultor = idAgricultor;
    }

    public String getDireccionArchivo() {
        return direccionArchivo;
    }

    public void setDireccionArchivo(String direccionArchivo) {
        this.direccionArchivo = direccionArchivo;
    }

    public int getProvincia() {
        return provincia;
    }

    public void setProvincia(int provincia) {
        this.provincia = provincia;
    }

    public int getMunicipio() {
        return municipio;
    }

    public void setMunicipio(int municipio) {
        this.municipio = municipio;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "Parcela{" + "id=" + id + ", nomParcela=" + nomParcela + ", area=" + area + ", idAgricultor=" + idAgricultor + ", direccionArchivo=" + direccionArchivo + ", provincia=" + provincia + ", municipio=" + municipio + ", puntos=" + puntos + '}';
    }
    
    
    
}