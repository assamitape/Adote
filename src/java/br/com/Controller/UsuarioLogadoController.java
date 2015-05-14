package br.com.Controller;

/**
 *
 * @author Igor
 */

  
import br.com.Bean.UsuarioBean;
import br.com.DAO.UsuarioDAO;
import br.com.Utilitarios.Utilidades;
import java.io.IOException;  
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger; 
import javax.annotation.PostConstruct;  
import javax.faces.application.FacesMessage;  
import javax.faces.bean.ManagedBean;  
import javax.faces.bean.SessionScoped;  
import javax.faces.context.FacesContext;  

@ManagedBean(name="usuarioLogadoController")   
@SessionScoped  

/*  CLASSE QUE CONTROLA O ADMINISTRADOR LOGADO */
public class UsuarioLogadoController implements Serializable  {
    
    private UsuarioBean user;
    private Boolean usuarioLogado = Boolean.FALSE;  
      
    private static UsuarioLogadoController instance;  
  
    @PostConstruct   
    public void inicializa()   
    {  
        user = new UsuarioBean(); 
        usuarioLogado = Boolean.FALSE;  
        instance = this; 
        
    }  
      
    public static UsuarioLogadoController getInstance() throws Exception  
    {  
        if(instance == null)  
        {  
            throw new Exception("Não há usuario logado no sistema!");  
        }  
        return instance;  
    }  
      
    public void logout()  
    {
        user = null;  
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("./loginAdmin.jsf");
            
        } catch (IOException ex) {
            Logger.getLogger(UsuarioLogadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }  

    public Boolean getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Boolean usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
      
    public void fazerLogin(String usuario, String senha)  
    {  
        String errLogin = null;
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {  

            errLogin = usuarioDAO.logar(usuario, senha);
            setUser(user);
            
            if (errLogin.isEmpty())   
            {  
                setUsuarioLogado(Boolean.TRUE);  
                FacesContext.getCurrentInstance().getExternalContext().redirect("./administracao.jsf");
            }  
            else  
            {  
                setUsuarioLogado(Boolean.FALSE);  
                FacesContext.getCurrentInstance().addMessage(null,   
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,errLogin, ""));  
               
                
            }  
        } catch (Exception e) {  
            FacesContext.getCurrentInstance().addMessage(null,   
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao efetuar login, tente novamente."));  
            
        }  
    }  
    
    public String controlaTelaLogin() throws IOException{
        if(usuarioLogado)  
        {  
            FacesContext.getCurrentInstance().addMessage(null,   
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Você já está conectado ao sistema."));
            FacesContext.getCurrentInstance().getExternalContext().redirect("./administracao.jsf");
        }  
        return "";  
        
    }
    
    public String verificaLogado()   
    {  
       // fazerLogin(user.getEmail(), user.getSenha());
        if(!usuarioLogado)  
        {  
            logout();
        }  
        return "";  
    }  
      
    public UsuarioBean getUser() {  
        return user;  
    }  
  
    public void setUser(UsuarioBean user) {  
        this.user = user;  
    }  
    /*
    public void esqueciSenha(String user){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Utilidades util = new Utilidades();
        String novaSenha = util.gerarSenhaRandon();
        try {
            if(usuarioDAO.alterarSenha(user, novaSenha)){
                util.enviaEmail(user, "", "Atualização de senha", "Sua senha foi atualizada. Ao realizar o login novamente, será possível "
                        + "modificar para uma senha de sua preferência."+"\n"+" Nova senha: "+novaSenha);
                FacesContext contexto = FacesContext.getCurrentInstance();
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uma nova senha foi gerada e enviada para o endereço "+user, ""));
                    
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteLogadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      */
} 