package br.com.Controller;

import br.com.Bean.AdocaoBean;
import br.com.DAO.AdocaoDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean
@SessionScoped
public class AdocaoController {
    private AdocaoBean adocao;
    private DataModel listaAdocao;

    public AdocaoController() {
        adocao = new AdocaoBean();
    }
    
    public AdocaoBean getAdocao() {
        return adocao;
    }

    public void setAdocao(AdocaoBean adocao) {
        this.adocao = adocao;
    }

    public DataModel getListaAdocao() {
        try {
            AdocaoDAO adocaoDAO = new AdocaoDAO();
            
            listaAdocao = new ListDataModel(adocaoDAO.listarAdocao());
            return listaAdocao;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAdocao;        
    }

    public void setListaAdocao(DataModel listaAdocao) {
        this.listaAdocao = listaAdocao;
    }
    
    public String salvarAdocao(){
        AdocaoDAO adocaoDAO = new AdocaoDAO();
        if(adocaoDAO.inserirAdocao(adocao)){
            FacesContext contexto = FacesContext.getCurrentInstance();
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Adoção cadastro com sucesso!", ""));
            return "listarclientes";
        }

        return "erro";
        
    }

    public String concretizaAdocao(){
        AdocaoDAO adocaoDAO = new AdocaoDAO();
        if(adocaoDAO.concretizaAdocao(adocao)){
            FacesContext contexto = FacesContext.getCurrentInstance();
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Adoção confirmada com sucesso!", ""));
            return "listarclientes";
        }

        return "erro";
        
    }
    
    public String cancelarAdocao(){
        AdocaoDAO adocaoDAO = new AdocaoDAO();
        if(adocaoDAO.cancelarAdocao(adocao)){
            FacesContext contexto = FacesContext.getCurrentInstance();
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancelado com sucesso!", ""));
            return "listarAdocao";
        }

        return "erro";
         
    }
    
}
