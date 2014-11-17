package br.com.Bean;

import java.sql.Date;

public class AdocaoBean {
    private int    id;
    private int    idAnimal;
    private int    idCliente;
    private String status;
    private Date   dtInicioAdocao;

    public java.util.Date getDtInicioAdocao() {
        return dtInicioAdocao;
    }

    public void setDtInicioAdocao(java.util.Date dtInicioAdocao) {
        this.dtInicioAdocao = new java.sql.Date(dtInicioAdocao.getTime());
    }

    public java.util.Date getDtfinalAdocao() {
        return dtfinalAdocao;
    }

    public void setDtfinalAdocao(java.util.Date dtfinalAdocao) {
        this.dtfinalAdocao = new java.sql.Date(dtfinalAdocao.getTime());
    }
    private Date   dtfinalAdocao;

    public AdocaoBean() { 
    }

    public AdocaoBean(int id, int idAnimal, int idCliente, String status, java.util.Date dtInicioAdocao, java.util.Date dtFinalAdocao) {
        this.id = id;
        this.idAnimal = idAnimal;
        this.idCliente = idCliente;
        this.status = status;
        this.dtInicioAdocao = new java.sql.Date(dtInicioAdocao.getTime());
        this.dtfinalAdocao = new java.sql.Date(dtfinalAdocao.getTime());
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
