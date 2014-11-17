package br.com.Bean;
import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.criteria.CriteriaBuilder;


public class AnimalBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private int    id;
    private String descricao;
    
    private String historico;
    private String status;
    private int    idade;
    private String vacinado;
    private String vermifugado;
    private int    idCliente;
    private byte[] fotoPrincipal;
    private String sexo;

    public AnimalBean() {
    }
    
    public AnimalBean(int id, String descricao, int idade, String historico, String status, int idCliente, String vacinado, String vermifugado) {
        this.id = id;
        this.descricao = descricao;
        this.idade = idade;
        this.historico = historico;
        this.status = status;
        this.idCliente = idCliente;
        this.vacinado = vacinado;
        this.vermifugado = vermifugado;
    }

    public byte[] getFotoPrincipal() {
        return fotoPrincipal;
    }

    public void setFotoPrincipal(byte[] fotoPrincipal) {
        this.fotoPrincipal = fotoPrincipal;
    }
    
  
    public String getSexo() {
        return sexo;
    }
    
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
            
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getVacinado() {
        return vacinado;
    }

    public void setVacinado(String vacinado) {
        this.vacinado = vacinado;
    }

    public String getVermifugado() {
        return vermifugado;
    }

    public void setVermifugado(String vermifugado) {
        this.vermifugado = vermifugado;
    }

    public String getCampoSn(String valor){
        if(valor.equals("S")){
            return "Sim";
        }else{  
            return "Não";
        }
    }
    
    public String getSexoExtenso(){
     
      switch(this.sexo){
          case "M":
              return "Macho";
          case "F":
              return "Fêmea";
          default:
              return"Valor Inválido";
      }
    }

    public String getStausExtenso(){

      switch(this.status){   
          case "V":
              return "Em Validação";
          case "A":
              return "Adotado";
          case "D":
              return "Disponível";
          default:
              return "";
      }      
    }    
    public String getIdadeExtenso(){

        switch (this.idade){
            case 1:
                return "De 0 mês a 3 meses";
            case 2:
                return "De 4 meses a 7 meses";
            case 3:
                return "De 8 meses a 1 ano";
            case 4:
                return "De 2 anos a 4 anos";
            case 5:    
                return "Acima de 4 anos";
            default:
                return "";
        }  
    }
}
