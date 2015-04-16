
package br.com.Controller;

import br.com.Bean.ClienteBean;
import br.com.DAO.ClienteDAO;
import br.com.Utilitarios.Utilidades;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean(name="clienteController")
//@ApplicationScoped
@SessionScoped
public class ClienteController implements Serializable{
    private static final long serialVersionUID = 1L;
    private ClienteBean cliente = ClienteBean.getInstancia();
    private DataModel listaCliente;
    private DataModel listaAnimalCliente;
    private boolean exibeBtnSalvar;
    private boolean exibeBtnAlterar;    
 //   private boolean exibe
    private boolean isValidaForm;
    private boolean isExibeEntrar;
    
    /* campos de envio de email*/
    private String msg;
    /* campos de envio de email*/
    public ClienteLogadoController user = new ClienteLogadoController();

    @PostConstruct
    void initialiseSession() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        exibeEntrar();
    }

    public boolean isIsExibeEntrar() {
        return isExibeEntrar;
    }

    public void setIsExibeEntrar(boolean isExibeEntrar) {
        this.isExibeEntrar = isExibeEntrar;
    }
    
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ClienteLogadoController getUser() {
        return user;
    }

    public void setUser(ClienteLogadoController user) {
        this.user = user;
    }
    
    public DataModel getListaAnimalCliente() {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            
            listaAnimalCliente = new ListDataModel(clienteDAO.listarAnimalCliente());
            return listaAnimalCliente;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAnimalCliente;        
    }

    public void setListaAnimalCliente(DataModel listaAnimalCliente) {
        this.listaAnimalCliente = listaAnimalCliente;
    }
    
    public ClienteController() {
        cliente = ClienteBean.getInstancia();
    }

    public ClienteBean getCliente() {
        return cliente;
    }

    public void setCliente(ClienteBean cliente) {
        this.cliente = cliente;
    }

    public boolean isExibeBtnSalvar() {
        return exibeBtnSalvar;
    }

    public void setExibeBtnSalvar(boolean exibeBtnSalvar) {
        this.exibeBtnSalvar = exibeBtnSalvar;
    }

    public boolean isExibeBtnAlterar() {
        return exibeBtnAlterar;
    }

    public void setExibeBtnAlterar(boolean exibeBtnAlterar) {
        this.exibeBtnAlterar = exibeBtnAlterar;
    }

    public DataModel getListaCliente() {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            
            clienteDAO.listarClientes();
            listaCliente = new ListDataModel(clienteDAO.listarClientes());
            return listaCliente;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaCliente;        
    }

    public void setListaCliente(DataModel listaCliente) {
        this.listaCliente = listaCliente;
    }
    
    public String salvaCliente(){
        ClienteDAO clienteDAO = new ClienteDAO();
        FacesContext contexto = FacesContext.getCurrentInstance();
        validaCadastro();
        if (isValidaForm){
            try {
                if(clienteDAO.salvarCliente(cliente)){
                    contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados cadastrados com sucesso!", ""));
                    return "Dados cadastrados com sucesso!";
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro ao cadastrar o cliente, Verifique os dados informados.", ""));
        return "Erro ao cadastrar o cliente, Verifique os dados informados.";        
    }

    public String alteraCliente(){
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            if(clienteDAO.alterarCliente(cliente)){
                FacesContext contexto = FacesContext.getCurrentInstance();
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "alterado com sucesso!", ""));
                return "/meusAnimais?faces-redirect=true";
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
        
    }
    
    public String deletaCliente(){
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            if(clienteDAO.excluirCliente(cliente)){
                FacesContext contexto = FacesContext.getCurrentInstance();
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "deletado com sucesso!", ""));
                //return "/listarclientes?faces-redirect=true";
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
         
    }
    public void novoCliente(){
        cliente = ClienteBean.limpaCliente();
        escondeBntAlterar();
    }

    public void retornaCliente(int idCliente) throws SQLException{
        ClienteDAO clienteDAO = new ClienteDAO();
        
        if(!clienteDAO.retornaCliente(idCliente)){
            FacesContext contexto = FacesContext.getCurrentInstance();
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário não encontrado!", ""));

        }    
    }
   
    public void atualizaCadCliente(){
        if (user.getClienteLogado()){
          setExibeBtnAlterar(true);
          setExibeBtnSalvar(false);
            
        }else{
            setExibeBtnAlterar(false);
            setExibeBtnSalvar(true);
        }
    }
    
    public void escondeBntAlterar(){
        setExibeBtnAlterar(false);
        setExibeBtnSalvar(true);
    }
    
    public void escondeBtnSalvar() throws IOException{
       FacesContext contexto = FacesContext.getCurrentInstance();
       
        if (cliente == null ){  
          contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Um registro precisa ser selecionado!", ""));            
          
        }else{ 
          setExibeBtnAlterar(true);
          setExibeBtnSalvar(false);
          FacesContext.getCurrentInstance().getExternalContext().redirect("./cadclientes.jsf");
        }

    }
    
//    public void logar() throws IOException{
//       FacesContext contexto = FacesContext.getCurrentInstance();
//       ClienteDAO clienteDAO = new ClienteDAO();
//       String errLogin = null;
//       
//       if (validaForm()){
//           
//       }       
//       
//       user.fazerLogin(user.getEmail(), cliente.getSenha());
//       
//       if (errLogin != null && errLogin != "" ){  
//          contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errLogin, ""));            
//          return "";
//       }else{
//          return "/meusAnimais?faces-redirect=true";  
//       }
//
//       return user.fazerLogin(cliente.getEmail(), cliente.getSenha());
//       
//    }

    public boolean verificaClienteExistente(){
       FacesContext contexto = FacesContext.getCurrentInstance();
       ClienteDAO clienteDAO = new ClienteDAO();
       String err = null;

       
        try {
            err = clienteDAO.verificaClienteExistente(cliente.getEmail());
        } catch (SQLException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       if (err != null && err != "" ){  
          contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, err, ""));            
          return false;
       }else{
          return true;  
       }
    }
    
    public boolean validaEmail(String email){
        if (email != null && email.length() > 0) {  
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);  
            Matcher matcher = pattern.matcher(email);  
            if (!matcher.matches()) {  
                FacesContext contexto = FacesContext.getCurrentInstance();
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato inválido de email!", ""));            
                return false;
            }  
        }
        return true;
    }
    
    public String salvarELogar() throws IOException{
        String retorno;
        
        validaCadastro();
        retorno = salvaCliente();
        
        if (retorno.isEmpty() || retorno.equals("")){
           // user.fazerLogin(cliente.getEmail(), cliente.getSenha());
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("./meusAnimais.jsf");
        //    return "/meusAnimais?faces-redirect=true";
            
        }
        return "";
    } 
 
    public void validaCadastro(){
        
        this.isValidaForm = true;
        
        if (!validaEmail(cliente.getEmail() ) ){
            this.isValidaForm = false;
            return;
        }
        
        if(!verificaClienteExistente()){
            this.isValidaForm = false;
            return;
        }
        
    }
    
    public void enviarMsg(){
      Utilidades util = new Utilidades();
      FacesContext contexto = FacesContext.getCurrentInstance();
      String retornoMsg;
      
      if(!this.getMsg().isEmpty() ){
        retornoMsg = util.enviaEmail(cliente.getEmail(), cliente.getNome(), "Adoção", this.getMsg() );

        if(!retornoMsg.isEmpty()){
          contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, retornoMsg, ""));
        }       
          
      }else{
          contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "É necessário informar um conteúdo para a mensagem.", ""));
      }
    }

    public void verificaLogado(){
        
        if (user.getClienteLogado()){
            escondeEntrar();
        }else{
            exibeEntrar();
        }
    }
    
//    public void logout(){
//        user.logout();
//    }
    
    public void logout(){
        user.logout();
        cliente = null;
        user = null;
    }

    public String controlaTelaLogin() throws IOException{
        
        user.controlaTelaLogin();
        return "";
    }

    

    
    public void exibeEntrar(){
        setIsExibeEntrar(Boolean.TRUE);
    }
    
    public void escondeEntrar()
    {
        setIsExibeEntrar(Boolean.FALSE);
    }
}

