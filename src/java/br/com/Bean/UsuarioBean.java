package br.com.Bean;

import java.io.Serializable;
import java.sql.Date;

public class UsuarioBean implements Serializable {
private static final long serialVersionUID = 1L;
    
    private int id;
    private String nome;
    private String login;
    private String senha;
    private String status;
    private String permissao;

    public UsuarioBean() {
    }

    private UsuarioBean(int idUsuario, String nomeUsuario,String login, String status, String permissao) {
        this.id = idUsuario;
        this.nome = nomeUsuario;
        this.login = login;
        this.status = status;
        this.permissao = permissao;
        
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }
   
    public String getSnGeral(String campo){
        
        if(campo.equals("S")){
            return "Sim";
        }else{
            return "Não";
        }
                
    }

}
