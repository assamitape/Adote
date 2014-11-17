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
    private Conexao con; 

    public ClienteDAO() {
        con = new Conexao();  
    }
    
    
    public boolean salvarCliente(ClienteBean cliente) {
     
        try {
            String sql = "INSERT INTO CLIENTE(NOME, EMAIL, SENHA, TELEFONE, SN_ATIVO)"+
                    " VALUES(?,?,?,?,?);";
            
            PreparedStatement stm = con.getConnection().prepareStatement(sql);
            stm.setString(1,cliente.getNome());
            stm.setString(2, cliente.getEmail());
            stm.setString(3, cliente.getSenha());
            stm.setString(4, cliente.getTelefone());
            cliente.setSnAtivo("S");
            stm.setString(5, cliente.getSnAtivo());
                    
            stm.execute();
            con.getConnection().commit();
            //stm.close();
            return true;       
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }
    
    public boolean alterarCliente(ClienteBean cliente){

        try {
            String sql = "UPDATE CLIENTE SET NOME = ?, EMAIL = ?, TELEFONE = ?, "+
                    " SN_ATIVO = ? WHERE ID = ?;";
            
            PreparedStatement stm = con.getConnection().prepareStatement(sql);
            
            stm.setString(1,cliente.getNome());
            stm.setString(2, cliente.getEmail());
            stm.setString(3, cliente.getTelefone());
            stm.setString(4,cliente.getSnAtivo() );
            stm.setInt(5,cliente.getId() );
    
            stm.execute();
            con.getConnection().commit();
            stm.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        return false;
    }
    public boolean excluirCliente(ClienteBean cliente) {
        try{
            String sql = "DELETE FROM CLIENTE WHERE ID = ?";
        
            PreparedStatement stm = con.getConnection().prepareStatement(sql);

            stm.setInt(1, cliente.getId());
            stm.execute();
            con.getConnection().commit();
            stm.close();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public List<ClienteBean> listarClientes() throws SQLException{
        
        List<ClienteBean> lista = new ArrayList<ClienteBean>();
        
        try {
            
        String sql = "SELECT ID, NOME, EMAIL, TELEFONE, SN_ATIVO FROM CLIENTE";
        PreparedStatement stm = con.getConnection().prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            ClienteBean cliente = ClienteBean.getInstancia();
            
            cliente.setId(rs.getInt("ID"));
            cliente.setNome(rs.getString("NOME"));
            cliente.setEmail(rs.getString("EMAIL"));
            cliente.setTelefone(rs.getString("TELEFONE"));
            cliente.setSnAtivo(rs.getString("SN_ATIVO"));
            
            lista.add(cliente);
        }
        stm.close();
        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
        
    }
    
    public List<AnimalBean> listarAnimalCliente() throws SQLException{
        
        List<AnimalBean> lista = new ArrayList<AnimalBean>();
        ClienteBean cliente = ClienteBean.getInstancia();
        
        try {
            
        String sql = "SELECT ID, DESCRICAO, IDADE, HISTORICO, STATUS, ID_CLIENTE, VACINADO, VERMIFUGADO, SEXO FROM ANIMAL WHERE id_cliente = ? ";
        PreparedStatement stm = con.getConnection().prepareStatement(sql);
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
        stm.close();
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
        
    }
    
    public String logar(String usuario, String senha){
        try {
            
        ClienteBean cliente = ClienteBean.getInstancia();
        boolean logou = false;
        
        String sql = "SELECT ID, NOME, EMAIL, TELEFONE, SN_ATIVO FROM CLIENTE WHERE EMAIL = ? AND SENHA = ? AND SN_ATIVO = 'S'";
        PreparedStatement stm = con.getConnection().prepareStatement(sql);
        stm.setString(1, usuario);
        stm.setString(2, senha);
        ResultSet rs = stm.executeQuery();
        
        
        while (rs.next()) {
            logou = true;    
           
            cliente.setId(rs.getInt("ID"));
            cliente.setNome(rs.getString("NOME"));
            cliente.setEmail(rs.getString("EMAIL"));
            cliente.setTelefone(rs.getString("TELEFONE"));
            cliente.setSnAtivo(rs.getString("SN_ATIVO"));
        }
        stm.close();
        if(logou && cliente.getId() > 0){
            return "";
        }else{
            return "Email ou senha estão incorretos!";
        }
        
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "ERRO AO TENTAR LOGAR!";
            
        }
        
    }

    public boolean retornaCliente(int idCliente) throws SQLException{
        
        
        ClienteBean cliente = ClienteBean.getInstancia();
        int existe = 0;
        
        try {
            
            String sql = "SELECT ID, NOME, SN_ATIVO, EMAIL, TELEFONE FROM CLIENTE WHERE ID = ? ";
            PreparedStatement stm = con.getConnection().prepareStatement(sql);
            stm.setInt(1,idCliente);
            ResultSet rs = stm.executeQuery();

           

            while (rs.next()) {
                existe = 1;
                AnimalBean animal = new AnimalBean();

                cliente.setId(rs.getInt("ID"));
                cliente.setNome(rs.getString("NOME"));
                cliente.setSnAtivo(rs.getString("SN_ATIVO"));
                cliente.setEmail(rs.getString("EMAIL"));
                cliente.setTelefone(rs.getString("TELEFONE"));


            }
        stm.close();
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(existe > 0){
            return true;
        }else{
            return false;
        }
        
        
    }
    public String verificaClienteExistente(String usuario){
        try {
            
        ClienteBean cliente = ClienteBean.getInstancia();
        boolean achou = false;
        
        String sql = "SELECT COUNT(*) FROM CLIENTE WHERE EMAIL = ? AND SN_ATIVO = 'S'";
        PreparedStatement stm = con.getConnection().prepareStatement(sql);
        stm.setString(1, usuario);
        
        ResultSet rs = stm.executeQuery();
        
        
        while (rs.next()) {
            if (rs.getInt(1) > 0){
                achou = true;
            }else{
                achou = false;
            }
        }
        stm.close();
        
        if(achou){
            return "Email já cadastrado!";
        }else{
            return "";
        }
        
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao tentar validar o email!";
        }
        
    }

}
