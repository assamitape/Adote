package br.com.DAO;

import br.com.Bean.AnimalBean;
import br.com.Utilitarios.Conexao;
import java.sql.PreparedStatement;
import br.com.Bean.ClienteBean;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO implements Serializable {
 private static final long serialVersionUID = 1L;
    private Conexao con; 
    PreparedStatement stm = null;
            
    public ClienteDAO() {
        con = new Conexao();  
    }
    
    
    public boolean salvarCliente(ClienteBean cliente) throws SQLException {
     
        try {
            String sql = "INSERT INTO CLIENTE(NOME, EMAIL, SENHA, TELEFONE, SN_ATIVO , ESTADO, CIDADE, BAIRRO )"+
                    " VALUES(?,?,?,?,?,?,?,?);";
            
            stm = con.getConnection().prepareStatement(sql);
            stm.setString(1,cliente.getNome());
            stm.setString(2, cliente.getEmail());
            stm.setString(3, cliente.getSenha());
            stm.setString(4, cliente.getTelefone());
            cliente.setSnAtivo("S");
            stm.setString(5, cliente.getSnAtivo());
            stm.setString(6, cliente.getEstado());
            stm.setString(7, cliente.getCidade());
            stm.setString(8, cliente.getBairro());
            
            stm.execute();
            con.getConnection().commit();
            stm.close();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            stm.close();
          //  con.getConnection().close();
            
        }
        return false;
        
    }
    
    public boolean alterarCliente(ClienteBean cliente) throws SQLException{

        try {
            String sql = "UPDATE CLIENTE SET NOME = ?, EMAIL = ?, TELEFONE = ?, "+
                    " SN_ATIVO = ?, ESTADO = ?, CIDADE = ?, BAIRRO = ? WHERE ID = ?;";
            
            stm = con.getConnection().prepareStatement(sql);
            
            stm.setString(1,cliente.getNome());
            stm.setString(2, cliente.getEmail());
            stm.setString(3, cliente.getTelefone());
            stm.setString(4,cliente.getSnAtivo() );
            stm.setString(5,cliente.getEstado());
            stm.setString(6,cliente.getCidade());
            stm.setString(7,cliente.getBairro());
            stm.setInt(8,cliente.getId() );
    
            stm.execute();

            con.getConnection().commit();
           
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
          
            stm.close();
          //  con.getConnection().close();
        }
        return false;
    }
    public boolean excluirCliente(ClienteBean cliente) throws SQLException {
        try{
            String sql = "DELETE FROM CLIENTE WHERE ID = ?";
        
            stm = con.getConnection().prepareStatement(sql);

            stm.setInt(1, cliente.getId());
            stm.execute();
            con.getConnection().commit();
          
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            stm.close();
         //   con.getConnection().close();
        }
        
        return false;
    }
    
    public List<ClienteBean> listarClientes() throws SQLException{
        
        List<ClienteBean> lista = new ArrayList<ClienteBean>();
        
        try {
            
        String sql = "SELECT ID, NOME, EMAIL, TELEFONE, SN_ATIVO, ESTADO, CIDADE, BAIRRO FROM CLIENTE";
        stm = con.getConnection().prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            ClienteBean cliente = ClienteBean.getInstancia();
            
            cliente.setId(rs.getInt("ID"));
            cliente.setNome(rs.getString("NOME"));
            cliente.setEmail(rs.getString("EMAIL"));
            cliente.setTelefone(rs.getString("TELEFONE"));
            cliente.setSnAtivo(rs.getString("SN_ATIVO"));
            cliente.setEstado(rs.getString("ESTADO"));
            cliente.setCidade(rs.getString("CIDADE"));
            cliente.setBairro(rs.getString("BAIRRO"));
            
            lista.add(cliente);
        }
       
        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            stm.close();
        //    con.getConnection().close();
        }
        return lista;
        
    }
    
    public List<AnimalBean> listarAnimalCliente() throws SQLException{
        
        List<AnimalBean> lista = new ArrayList<AnimalBean>();
        ClienteBean cliente = ClienteBean.getInstancia();
        
        try {
            
        String sql = "SELECT ID, DESCRICAO, IDADE, HISTORICO, STATUS, ID_CLIENTE, "
                + "          VACINADO, VERMIFUGADO, SEXO "
                + "     FROM ANIMAL "
                + "    WHERE id_cliente = ? "
                + "    ORDER BY DT_AUTORIZACAO DESC";
        stm = con.getConnection().prepareStatement(sql);
        stm.setInt(1, cliente.getId());
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            AnimalBean animal = new AnimalBean();
            
            animal.setId(rs.getInt("ID"));
            animal.setDescricao(rs.getString("DESCRICAO"));
            animal.setIdade(rs.getInt("IDADE"));
            animal.setHistorico(rs.getString("HISTORICO"));
            animal.setStatus(rs.getString("STATUS"));
            animal.setIdCliente(rs.getInt("ID_CLIENTE"));
            animal.setVacinado(rs.getString("VACINADO"));
            animal.setVermifugado(rs.getString("VERMIFUGADO"));
            animal.setSexo(rs.getString("SEXO"));
            
            lista.add(animal);
        }
        
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            stm.close();
           // con.getConnection().close();
        }
        return lista;
        
    }
    
    public String logar(String usuario, String senha) throws SQLException{
        try {
            
        ClienteBean cliente = ClienteBean.getInstancia();
        boolean logou = false;
        
        String sql = "SELECT ID, NOME, SENHA, EMAIL, TELEFONE, SN_ATIVO, ESTADO, CIDADE, BAIRRO FROM CLIENTE WHERE EMAIL = ? AND SENHA = ? AND SN_ATIVO = 'S'";
        stm = con.getConnection().prepareStatement(sql);
        stm.setString(1, usuario);
        stm.setString(2, senha);
        ResultSet rs = stm.executeQuery();
        
        
        while (rs.next()) {
            logou = true;    
           
            cliente.setId(rs.getInt("ID"));
            cliente.setNome(rs.getString("NOME"));
            cliente.setEmail(rs.getString("EMAIL"));
            cliente.setSenha(rs.getString("SENHA"));
            cliente.setTelefone(rs.getString("TELEFONE"));
            cliente.setSnAtivo(rs.getString("SN_ATIVO"));
            cliente.setEstado(rs.getString("ESTADO"));
            cliente.setCidade(rs.getString("CIDADE"));
            cliente.setBairro(rs.getString("BAIRRO"));
        }
        
        if(logou && cliente.getId() > 0){
            return "";
        }else{
            return "Email ou senha estão incorretos!";
        }
        
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "ERRO AO TENTAR LOGAR!";
            
        }finally{
            stm.close();
        //    con.getConnection().close();
        }
        
    }

    public boolean retornaCliente(int idCliente) throws SQLException{
        
        
        ClienteBean cliente = ClienteBean.getInstancia();
        int existe = 0;
        
        try {
            
            String sql = "SELECT ID, NOME, SN_ATIVO, EMAIL, TELEFONE, ESTADO, CIDADE, BAIRRO FROM CLIENTE WHERE ID = ? ";
            stm = con.getConnection().prepareStatement(sql);
            stm.setInt(1,idCliente);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                existe = 1;

                cliente.setId(rs.getInt("ID"));
                cliente.setNome(rs.getString("NOME"));
                cliente.setSnAtivo(rs.getString("SN_ATIVO"));
                cliente.setEmail(rs.getString("EMAIL"));
                cliente.setTelefone(rs.getString("TELEFONE"));
                cliente.setEstado(rs.getString("ESTADO"));
                cliente.setCidade(rs.getString("CIDADE"));
                cliente.setBairro(rs.getString("BAIRRO"));

            }
        
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            stm.close();
            con.getConnection().close();
        }
        
        if(existe > 0){
            return true;
        }else{
            return false;
        }
        
        
    }
    public String verificaClienteExistente(String usuario) throws SQLException{
        try {
            
        ClienteBean cliente = ClienteBean.getInstancia();
        boolean achou = false;
        
        String sql = "SELECT COUNT(*) FROM CLIENTE WHERE EMAIL = ? AND SN_ATIVO = 'S'";
        stm = con.getConnection().prepareStatement(sql);
        stm.setString(1, usuario);
        
        ResultSet rs = stm.executeQuery();
        
        
        while (rs.next()) {
            if (rs.getInt(1) > 0){
                achou = true;
            }else{
                achou = false;
            }
        }
        
        
        if(achou){
            return "Email já cadastrado!";
        }else{
            return "";
        }
        
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao tentar validar o email!";
        }finally{
            stm.close();
            con.getConnection().close();
        }
        
    }
    
    public boolean alterarSenha(String usuarioEmail, String novaSenha) throws SQLException{

        try {
            String sql = "UPDATE CLIENTE SET SENHA = ? WHERE EMAIL = ?;";
            
            stm = con.getConnection().prepareStatement(sql);
            
            stm.setString(1, novaSenha);
            stm.setString(2, usuarioEmail);
    
            stm.execute();

            con.getConnection().commit();
           
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
          
            stm.close();
            con.getConnection().close();
        }
        return false;
    }

}
