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
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "clienteLogadoController")
@SessionScoped
public class ClienteLogadoController implements Serializable {

    private ClienteBean user;
    private String email;
    private Boolean clienteLogado = Boolean.FALSE;

    private static ClienteLogadoController instance;

    @PostConstruct
    public void inicializa() {
        user = new ClienteBean();
        clienteLogado = Boolean.FALSE;
        instance = this;
    }

    public static ClienteLogadoController getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("Não há usuario logado no sistema!");
        }
        return instance;
    }

    public void logout() {
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

    public void fazerLogin(String usuario, String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String errLogin = null;
        ClienteDAO clienteDAO = new ClienteDAO();

        try {

            errLogin = clienteDAO.logar(usuario, senha);
            setUser(ClienteBean.getInstancia());

            if (errLogin.isEmpty()) {
                setClienteLogado(Boolean.TRUE);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./meusAnimais.jsf");
            } else {
                setClienteLogado(Boolean.FALSE);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, errLogin, ""));

            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao efetuar login, tente novamente."));

        }
    }

    public String controlaTelaLogin() throws IOException {
        if (clienteLogado) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Você já está conectado ao sistema."));
            FacesContext.getCurrentInstance().getExternalContext().redirect("./meusAnimais.jsf");
        }
        return "";

    }

    public boolean verificaLogado() {
        // fazerLogin(user.getEmail(), user.getSenha());
        if (!clienteLogado) {
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

    public void esqueciSenha(String email) {
        ClienteDAO clienteDAO = new ClienteDAO();
        Utilidades util = new Utilidades();
        String novaSenha = Utilidades.gerarSenhaRandon();
        FacesContext contexto = FacesContext.getCurrentInstance();

        try {
            if (clienteDAO.alterarSenha(email, novaSenha)) {
                if (util.enviaEmail(email, "", "Atualização de senha", "Sua senha foi atualizada. Ao realizar o login novamente, será possível "
                        + "modificar par    a uma senha de sua preferência." + "\n" + " Nova senha: " + novaSenha)) {
                    contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Uma nova senha foi gerada e enviada para o endereço " + email, ""));
                }else{
                    contexto.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha no envio do email, por favor contacte a administração!" + email, ""));
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteLogadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
