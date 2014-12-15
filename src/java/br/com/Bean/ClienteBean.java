package br.com.Bean;

import java.io.Serializable;
import java.sql.Date;

public class ClienteBean implements Serializable {
private static final long serialVersionUID = 1L;
    private static ClienteBean instancia = null;
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String snAtivo;
    private String estado;
    private String cidade;
    private String bairro;
    private String msgEmail;
    
    

    public ClienteBean() {
    }

    public static ClienteBean getInstancia() {
        if (instancia == null) {
            instancia = new ClienteBean();
        }
        return instancia;
    }

    public static ClienteBean limpaCliente() {
        if (instancia != null) {
            instancia = null;
            instancia = new ClienteBean();
        }
        return instancia;
    }

    private ClienteBean(int idCliente, String nomeCliente, String snAtivo) {
        this.id = idCliente;
        this.nome = nomeCliente;
        this.snAtivo = snAtivo;
    }

    public String getMsgEmail() {
        return msgEmail;
    }

    public void setMsgEmail(String msgEmail) {
        this.msgEmail = msgEmail;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSnAtivo() {
        return snAtivo;
    }

    public void setSnAtivo(String snAtivo) {
        this.snAtivo = snAtivo;
    }
    
    public String getSnGeral(String campo){
        
        if(campo.equals("S")){
            return "Sim";
        }else{
            return "Não";
        }
                
    }

}
