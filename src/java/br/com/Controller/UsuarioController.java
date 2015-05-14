
package br.com.Controller;

import br.com.Bean.UsuarioBean;
import br.com.Bean.UsuarioBean;
import br.com.DAO.UsuarioDAO;
import br.com.DAO.UsuarioDAO;
import br.com.Utilitarios.Utilidades;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean(name="usuarioController")
//@ApplicationScoped
@SessionScoped
public class UsuarioController implements Serializable{
    private static final long serialVersionUID = 1L;
    private UsuarioBean usuario = new UsuarioBean();
    
    public UsuarioLogadoController user = new UsuarioLogadoController();


    public UsuarioLogadoController getUser() {
        return user;
    }

    public void setUser(UsuarioLogadoController user) {
        this.user = user;
    }
    
        public UsuarioController() {
    }
    
    public String salvaUsuario(){
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            if(usuarioDAO.salvarUsuario(usuario)){
//                    FacesContext contexto = FacesContext.getCurrentInstance();
//                    contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "cadastro com sucesso!", ""));
                
            }
            
            return "Erro ao cadastrar o usuario, Verifique os dados informados.";
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public String alteraUsuario(){
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            if(usuarioDAO.alterarUsuario(usuario)){
                FacesContext contexto = FacesContext.getCurrentInstance();
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "alterado com sucesso!", ""));
                //return "/listarusuarios?faces-redirect=true";
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String deletaUsuario(){
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            if(usuarioDAO.excluirUsuario(usuario)){
                FacesContext contexto = FacesContext.getCurrentInstance();
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "deletado com sucesso!", ""));
                //return "/listarusuarios?faces-redirect=true";
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
         return "";
    }

    public void retornaUsuario(int idUsuario) throws SQLException{
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if(!usuarioDAO.retornaUsuario(idUsuario)){
            FacesContext contexto = FacesContext.getCurrentInstance();
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário não encontrado!", ""));

        }    
    }

//    public boolean verificaUsuarioExistente(){
//       FacesContext contexto = FacesContext.getCurrentInstance();
//       String err = null;
//
//              
//       if (err != null && err != "" ){  
//          contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, err, ""));            
//          return false;
//       }else{
//          return true;  
//       }
//    }
    
//    public boolean validaEmail(String email){
//        if (email != null && email.length() > 0) {  
//            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
//            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);  
//            Matcher matcher = pattern.matcher(email);  
//            if (!matcher.matches()) {  
//                FacesContext contexto = FacesContext.getCurrentInstance();
//                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato inválido de email!", ""));            
//                return false;
//            }  
//        }
//        return true;
//    } 
    
/*
    public String validaSessao(){
        return user.verificaLogado();
    }
    
    public String logout(){
        return user.logout();
    }*/
    
    public void logout(){
        usuario = null;
        user.logout();
        
    }

    public String controlaTelaLogin() throws IOException{
        
        user.controlaTelaLogin();
        return "";
    }
    
}

