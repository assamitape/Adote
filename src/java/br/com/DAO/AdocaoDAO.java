package br.com.DAO;

import br.com.Bean.AdocaoBean;
import br.com.Utilitarios.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdocaoDAO {
    Conexao con;

    public AdocaoDAO() {
        con = new Conexao();
    }
    
    public boolean inserirAdocao(AdocaoBean adocao){
        try {
            String sql = "INSERT INTO ADOCAO(ID_ANIMAL, ID_CLIENTE, STATUS, DT_INICIO_ADOCAO, DT_FINAL_ADOCAO)"+
                    " VALUES(?,?,?,?,?);";
            
            PreparedStatement stm = con.getConnection().prepareStatement(sql);
            stm.setInt(1,adocao.getIdAnimal());
            stm.setInt(2,   adocao.getIdCliente()); 
            //sempre que uma adoção é criada ela inicia no status 2(em processo)
            adocao.setStatus("2");
            stm.setString(3,adocao.getStatus());
            stm.setDate(4, (java.sql.Date) adocao.getDtInicioAdocao());
            stm.setDate(5, (java.sql.Date) adocao.getDtfinalAdocao());
                    
            stm.execute();
            con.getConnection().commit();
            return true;       
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }
    
    public boolean cancelarAdocao(AdocaoBean adocao){

        try {
            String sql = "UPDATE ADOCAO SET STATUS = '3' WHERE ID = ? ;";
            
            PreparedStatement stm = con.getConnection().prepareStatement(sql);
            
            stm.setInt(1,adocao.getId());
    
            stm.execute();
            con.getConnection().commit();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        return false;
    }
    
    public boolean concretizaAdocao(AdocaoBean adocao){

        try {
            String sql = "UPDATE ADOCAO SET STATUS = '1' WHERE ID = ? ;";
            
            PreparedStatement stm = con.getConnection().prepareStatement(sql);
            
            stm.setInt(1,adocao.getId());
    
            stm.execute();
            con.getConnection().commit();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        return false;
    }
    
    public List<AdocaoBean> listarAdocao() throws SQLException{
        
        List<AdocaoBean> lista = new ArrayList<AdocaoBean>();
        
        try {
            
        String sql = "SELECT * FROM ADOCAO";
        PreparedStatement stm = con.getConnection().prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        
        con.getConnection().commit();
        
        while (rs.next()) {
            AdocaoBean adocao = new AdocaoBean();
            
            adocao.setId(rs.getInt("ID"));
            adocao.setIdAnimal(rs.getInt("ID_ANIMAL"));
            adocao.setIdCliente(rs.getInt("ID_CLIENTE"));
            adocao.setStatus(rs.getString("STATUS"));
            adocao.setDtInicioAdocao(rs.getDate("DT_INICIO_ADOCAO"));
            adocao.setDtInicioAdocao(rs.getDate("DT_FINAL_ADOCAO"));            
            
            lista.add(adocao);
        }
        
        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
        
    }
    
}

