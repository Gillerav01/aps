package modelo;

import java.sql.Date;

public class Trabajo {
    private int idTrabajo;
    private int idParcela;
    private int idPiloto;
    private int idAgricultor;
    private int idDron;
    private String tipoTrabajo; 
    private Date fechaRegistro;
    private Date fechaRealizacion;

    public Trabajo() {
    }

    public Trabajo(int idParcela, int idPiloto, int idAgricultor, int idDron, Date fechaRegistro, Date fechaRealizacion) {
        this.idParcela = idParcela;
        this.idPiloto = idPiloto;
        this.idAgricultor = idAgricultor;
        this.idDron = idDron;
        this.fechaRegistro = fechaRegistro;
        this.fechaRealizacion = fechaRealizacion;
    }

    public Trabajo(int idParcela, int idPiloto, int idAgricultor, int idDron, String tipoTrabajo, Date fechaRegistro, Date fechaRealizacion) {
        this.idParcela = idParcela;
        this.idPiloto = idPiloto;
        this.idAgricultor = idAgricultor;
        this.idDron = idDron;
        this.tipoTrabajo = tipoTrabajo;
        this.fechaRegistro = fechaRegistro;
        this.fechaRealizacion = fechaRealizacion;
    }
    
    public Trabajo(int idTrabajo, int idParcela, int idPiloto, int idAgricultor, int idDron, String tipoTrabajo, Date fechaRegistro, Date fechaRealizacion) {
        this.idTrabajo = idTrabajo;
        this.idParcela = idParcela;
        this.idPiloto = idPiloto;
        this.idAgricultor = idAgricultor;
        this.idDron = idDron;
        this.tipoTrabajo = tipoTrabajo;
        this.fechaRegistro = fechaRegistro;
        this.fechaRealizacion = fechaRealizacion;
    }
    
    public Trabajo(int idTrabajo, int idParcela, int idPiloto, int idAgricultor, int idDron, Date fechaRegistro, Date fechaRealizacion) {
        this.idTrabajo = idTrabajo;
        this.idParcela = idParcela;
        this.idPiloto = idPiloto;
        this.idAgricultor = idAgricultor;
        this.idDron = idDron;
        this.fechaRegistro = fechaRegistro;
        this.fechaRealizacion = fechaRealizacion;
    }

    public int getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(int idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public int getIdParcela() {
        return idParcela;
    }

    public void setIdParcela(int idParcela) {
        this.idParcela = idParcela;
    }

    public int getIdPiloto() {
        return idPiloto;
    }

    public void setIdPiloto(int idPiloto) {
        this.idPiloto = idPiloto;
    }

    public int getIdAgricultor() {
        return idAgricultor;
    }

    public void setIdAgricultor(int idAgricultor) {
        this.idAgricultor = idAgricultor;
    }
    
    public int getIdDron() {
        return idDron;
    }

    public void setIdDron(int idDron) {
        this.idDron = idDron;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(Date fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public String getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(String tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }
    
}
