package br.com.Bean;
import java.io.Serializable;
/**
 *
 * @author Igor
 */
public class UsuarioLogadoBean implements Serializable{

    private static UsuarioLogadoBean instancia = null;
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String    telefone;
    private String snAtivo;
    

    public UsuarioLogadoBean() {
    }

    public static UsuarioLogadoBean getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioLogadoBean();
        }
        return instancia;
    }

    public static UsuarioLogadoBean limpaCliente() {
        if (instancia != null) {
            instancia = null;
            instancia = new UsuarioLogadoBean();
        }
        return instancia;
    }

    private UsuarioLogadoBean(int idCliente, String nomeCliente, String snAtivo) {
        this.id = idCliente;
        this.nome = nomeCliente;
        this.snAtivo = snAtivo;
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
