package br.com.Controller;

/**
 *
 * @author Igor
 */

  
import br.com.Bean.ClienteBean;
import br.com.DAO.ClienteDAO;
import br.com.Utilitarios.Utilidades;
import java.io.IOException;  
import java.io.Serializable;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger; 
import javax.annotation.PostConstruct;  
import javax.faces.application.FacesMessage;  
import javax.faces.bean.ManagedBean;  
import javax.faces.bean.SessionScoped;  
import javax.faces.context.FacesContext;  
  
/** 
* @author Cristian Urbainski 
* @since 01/05/2012 
*/  
@ManagedBean(name="clienteLogadoController") 
@SessionScoped  
public class ClienteLogadoController implements Serializable  {
    
    private ClienteBean user;
    private Boolean clienteLogado = Boolean.FALSE;  
      
    private static ClienteLogadoController instance;  
  
    @PostConstruct   
    public void inicializa()  
    {  
        user = new ClienteBean(); 
        clienteLogado = Boolean.FALSE;  
        instance = this;  
    }  
      
    public static ClienteLogadoController getInstance() throws Exception  
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
            FacesContext.getCurrentInstance().getExternalContext().redirect("./login.jsf");
            
        } catch (IOException ex) {
            Logger.getLogger(ClienteLogadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }  

    public Boolean getClienteLogado() {
        return clienteLogado;
    }

    public void setClienteLogado(Boolean clienteLogado) {
        this.clienteLogado = clienteLogado;
    }
      
    public void fazerLogin(String usuario, String senha)  
    {  
        String errLogin = null;
        ClienteDAO clienteDAO = new ClienteDAO();
        
        try {  

            errLogin = clienteDAO.logar(usuario, senha);
            setUser(ClienteBean.getInstancia());
            
            
            if (errLogin.isEmpty())   
            {  
                setClienteLogado(Boolean.TRUE);  
                FacesContext.getCurrentInstance().getExternalContext().redirect("./meusAnimais.jsf");
            }  
            else  
            {  
                setClienteLogado(Boolean.FALSE);  
                FacesContext.getCurrentInstance().addMessage(null,   
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,errLogin, ""));  
               
                
            }  
        } catch (Exception e) {  
            FacesContext.getCurrentInstance().addMessage(null,   
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao efetuar login, tente novamente."));  
            
        }  
    }  
    
    public String controlaTelaLogin() throws IOException{
        if(clienteLogado)  
        {  
            FacesContext.getCurrentInstance().addMessage(null,   
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Você já está conectado ao sistema."));
            FacesContext.getCurrentInstance().getExternalContext().redirect("./meusAnimais.jsf");
        }  
        return "";  
        
    }
    
    public boolean verificaLogado()   
    {  
       // fazerLogin(user.getEmail(), user.getSenha());
        if(!clienteLogado)  
        {  
//            UsuarioLogadoController admin = new UsuarioLogadoController();
//            admin.verificaLogado();
            logout();
            return false;
        }  
        return true;  
    }  
      
    public ClienteBean getUser() {  
        return user;  
    }  
  
    public void setUser(ClienteBean user) {  
        this.user = user;  
    }  

    public String gerarSenhaRandon(){
        
        UUID uuid = UUID.randomUUID();  
        String myRandom = uuid.toString().substring(0,10);  
        return myRandom;
        
    }    
    public void esqueciSenha(String user){
        ClienteDAO clienteDAO = new ClienteDAO();
        Utilidades util = new Utilidades();
        String novaSenha = gerarSenhaRandon();
        try {
            if(clienteDAO.alterarSenha(user, novaSenha)){
                util.enviaEmail(user, "", "Atualização de senha", "Sua senha foi atualizada. Ao realizar o login novamente, será possível "
                        + "modificar para uma senha de sua preferência."+"\n"+" Nova senha: "+novaSenha);
                FacesContext contexto = FacesContext.getCurrentInstance();
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uma nova senha foi gerada e enviada para o endereço "+user, ""));
                    
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteLogadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} 