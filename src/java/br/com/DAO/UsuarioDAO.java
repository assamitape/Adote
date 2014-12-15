package br.com.DAO;

import br.com.Bean.AnimalBean;
import br.com.Utilitarios.Conexao;
import java.sql.PreparedStatement;
import br.com.Bean.UsuarioBean;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO implements Serializable {
 private static final long serialVersionUID = 1L;
    private Conexao con; 
    PreparedStatement stm = null;
            
    public UsuarioDAO() {
        con = new Conexao();  
    }
    
    public boolean salvarUsuario(UsuarioBean usuario) throws SQLException {
     
        try {
            String sql = "INSERT INTO USUARIO(NOME, LOGIN, SENHA, STATUS, PERMISSAO )"+
                    " VALUES(?,?,?,?,?);";
            
            stm = con.getConnection().prepareStatement(sql);
            stm.setString(1,usuario.getNome());
            stm.setString(2,usuario.getLogin());
            stm.setString(3,usuario.getSenha());
            stm.setString(4,usuario.getStatus());
            stm.setString(5, usuario.getPermissao());
            stm.executeUpdate();

            //con.getConnection().commit();
            
            return true;       
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            stm.close();
        }
        return false;
        
    }
    
    public boolean alterarUsuario(UsuarioBean usuario) throws SQLException{

        try {
            String sql = "UPDATE USUARIO SET NOME = ?, LOGIN = ?, SENHA = ?, "+
                    " STATUS = ?, PERMISSSAO = ? WHERE ID = ?;";
            
            stm = con.getConnection().prepareStatement(sql);
            
            stm.setString(1,usuario.getNome());
            stm.setString(2,usuario.getLogin());
            stm.setString(3,usuario.getSenha());
            stm.setString(4,usuario.getStatus());
            stm.setString(5,usuario.getPermissao());
            stm.setInt(6,usuario.getId() );
    
            stm.executeUpdate();

            //con.getConnection().commit();
           
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        
        }finally{
            stm.close();
        }
        return false;
    }
    
    public boolean excluirUsuario(UsuarioBean usuario) throws SQLException {
        try{
            String sql = "DELETE FROM USUARIO WHERE ID = ?";
        
            stm = con.getConnection().prepareStatement(sql);

            stm.setInt(1, usuario.getId());
            stm.execute();
            con.getConnection().commit();
          
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            stm.close();
        }
        
        return false;
    }
  
  
    public String logar(String usuario, String senha) throws SQLException{
        try {
            
        UsuarioBean usuarioB = new UsuarioBean();
        boolean logou = false;
        // SELECT ID, NOME, LOGIN, SENHA, STATUS, PERMISSAO FROM USUARIO
        String sql = "SELECT ID, NOME, LOGIN, SENHA, STATUS, PERMISSAO FROM USUARIO WHERE LOGIN = ? AND SENHA = ? AND STATUS = 'A'";
        stm = con.getConnection().prepareStatement(sql);
        stm.setString(1, usuario);
        stm.setString(2, senha);
        ResultSet rs = stm.executeQuery();
        
        
        while (rs.next()) {
            logou = true;    
           
            usuarioB.setId(rs.getInt("ID"));
            usuarioB.setNome(rs.getString("NOME"));
            usuarioB.setSenha(rs.getString("SENHA"));
            usuarioB.setStatus(rs.getString("STATUS"));
            usuarioB.setPermissao(rs.getString("PERMISSAO"));
        }
        
        if(logou && usuarioB.getId() > 0){
            return "";
        }else{
            return "Login ou senha estão incorretos!";
        }
        
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "ERRO AO TENTAR LOGAR!";
            
        }finally{
            stm.close();
        }
        
    }

    public boolean retornaUsuario(int idUsuario) throws SQLException{
        
        
        UsuarioBean usuario = new UsuarioBean();
        int existe = 0;
        
        try {
            
            String sql = "SELECT ID, NOME FROM USUARIO WHERE ID = ? ";
            stm = con.getConnection().prepareStatement(sql);
            stm.setInt(1,idUsuario);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                existe = 1;

                usuario.setId(rs.getInt("ID"));
                usuario.setNome(rs.getString("NOME"));

            }
        
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            stm.close();
        }
        
        if(existe > 0){
            return true;
        }else{
            return false;
        }
        
        
    }
    public String verificaUsuarioExistente(String usuario) throws SQLException{
        try {
            
        UsuarioBean usuarioB = new UsuarioBean();
        boolean achou = false;
        
        String sql = "SELECT COUNT(*) FROM USUARIO WHERE LOGIN = ? AND STATUS = 'A'";
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
            return "usuário já cadastrado!";
        }else{
            return "";
        }
        
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao tentar validar o login!";
        }finally{
            stm.close();
        }
        
    }

}
