
package br.com.Controller;

import br.com.Bean.ClienteBean;
import br.com.DAO.ClienteDAO;
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
    
    /* campos de envio de email*/
    private String msg;
    /* campos de envio de email*/
    public UsuarioLogadoController user = new UsuarioLogadoController();


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UsuarioLogadoController getUser() {
        return user;
    }

    public void setUser(UsuarioLogadoController user) {
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
        
        if (isValidaForm){
            if(clienteDAO.salvarCliente(cliente)){
//                    FacesContext contexto = FacesContext.getCurrentInstance();
//                    contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "cadastro com sucesso!", ""));
                    return "";
            }
        }
        return "Erro ao cadastrar o cliente, Verifique os dados informados.";        
    }

    public String alteraCliente(){
        ClienteDAO clienteDAO = new ClienteDAO();
            if(clienteDAO.alterarCliente(cliente)){
                FacesContext contexto = FacesContext.getCurrentInstance();
                contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "alterado com sucesso!", ""));
                //return "/listarclientes?faces-redirect=true";
            }
        return "";
        
    }
    
    public String deletaCliente(){
        ClienteDAO clienteDAO = new ClienteDAO();
        if(clienteDAO.excluirCliente(cliente)){
            FacesContext contexto = FacesContext.getCurrentInstance();
            contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "deletado com sucesso!", ""));
            //return "/listarclientes?faces-redirect=true";
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

       
       err = clienteDAO.verificaClienteExistente(cliente.getEmail());
       
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
    
    public void salvarELogar() throws IOException{
        String retorno;
        
        validaCadastro();
        retorno = salvaCliente();
        
        if (retorno.isEmpty() && retorno.equals("")){
            //user.fazerLogin(cliente.getEmail(), cliente.getSenha());
            user.setUsuarioLogado(Boolean.TRUE);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./meusAnimais.jsf");
        }        
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
      
      retornoMsg = util.enviaEmail(cliente.getEmail(), cliente.getNome(), "Adoção", msg );
      
      if(!retornoMsg.isEmpty()){
        contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, retornoMsg, ""));
      }       
    }
/*
    public String validaSessao(){
        return user.verificaLogado();
    }
    
    public String logout(){
        return user.logout();
    }*/
    
    public void logout(){
        user.logout();
        cliente = null;
    }

    public String controlaTelaLogin() throws IOException{
        
        user.controlaTelaLogin();
        return "";
    }
    
}

