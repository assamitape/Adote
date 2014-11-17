
package br.com.DAO;
import br.com.Utilitarios.Conexao;
import br.com.Bean.AnimalBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimalDAO implements Serializable {
    private Conexao con;
private static final long serialVersionUID = 1L;
    public AnimalDAO() {
        con = new Conexao();
    }

    public boolean salvarAnimal(AnimalBean animal) throws SQLException{
        
        try {
            
            String sql = "INSERT INTO ANIMAL(DESCRICAO, IDADE, HISTORICO, STATUS, ID_CLIENTE, VACINADO, VERMIFUGADO, SEXO, FOTO_PRINCIPAL) VALUES"+
                         "(?,?,?,?,?,?,?,?,?);";
       
            /*FileInputStream fis; 
            
            File f = new File((String) animal.getFotoPrincipal());  
            fis = new FileInputStream(f);
              */          
            PreparedStatement stm = con.getConnection().prepareStatement(sql);
            
            stm.setString(1, animal.getDescricao());
            stm.setInt(2, animal.getIdade());
            stm.setString(3, animal.getHistorico());
            //UM NOVO ANIMAL SEMPRE É INSERIDO COM STATUS DE VALIDAÇÃO
            animal.setStatus("V");
            stm.setString(4, animal.getStatus());
            stm.setInt(5, animal.getIdCliente());
            stm.setString(6, animal.getVacinado());
            //stm.setBinaryStream(7, fis, fis.available());
            stm.setString(7, animal.getVermifugado());
            stm.setString(8, animal.getSexo());
            stm.setBytes(9, animal.getFotoPrincipal());
            
            stm.execute();
            con.getConnection().commit();
            stm.close();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean alterarAnimal(AnimalBean animal) throws SQLException{
        
        try{
            String sql = "UPDATE ANIMAL SET DESCRICAO = ? , IDADE = ? , HISTORICO = ? , STATUS = ? , VACINADO = ? , VERMIFUGADO = ? "+
                         ", SEXO = ? , FOTO_PRINCIPAL = ?  WHERE ID = ?";

            PreparedStatement stm = con.getConnection().prepareStatement(sql);
            stm.setString(1, animal.getDescricao());
            stm.setInt(2, animal.getIdade());
            stm.setString(3, animal.getHistorico());
            stm.setString(4, animal.getStatus());
            stm.setString(5, animal.getVacinado());
            stm.setString(6, animal.getVermifugado());
            stm.setString(7, animal.getSexo());
            stm.setBytes(8, animal.getFotoPrincipal());
            stm.setInt(9, animal.getId());
            
            stm.execute();
            con.getConnection().commit();
            stm.close();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean excluirAnimal(AnimalBean animal) throws SQLException{
        try{    
            String sql = "DELETE FROM ANIMAL WHERE ID = ?";

            PreparedStatement stm = con.getConnection().prepareStatement(sql);

            stm.setInt(1, animal.getId());
            stm.execute();
            con.getConnection().commit();
            stm.close();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }
    
    public List<AnimalBean> listarAnimalGeral() throws SQLException{
        
        List<AnimalBean> lista = new ArrayList<AnimalBean>();
        
        try {
            
        String sql = "SELECT ID, DESCRICAO, IDADE, HISTORICO, STATUS, ID_CLIENTE, VACINADO, VERMIFUGADO , SEXO FROM ANIMAL";
        PreparedStatement stm = con.getConnection().prepareStatement(sql);
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

    public List<AnimalBean> listarAnimalParaDoacao() throws SQLException{
        
        List<AnimalBean> lista = new ArrayList<AnimalBean>();
        
        try {
            
        String sql = "SELECT ID, DESCRICAO, IDADE, HISTORICO, STATUS, ID_CLIENTE, VACINADO, VERMIFUGADO, SEXO, FOTO_PRINCIPAL FROM ANIMAL WHERE STATUS = 'D' ";
        PreparedStatement stm = con.getConnection().prepareStatement(sql);
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
            animal.setFotoPrincipal(rs.getBytes("FOTO_PRINCIPAL"));
            lista.add(animal);
        }
        stm.close();
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
        
    }
    
//ATUALIZA O STATUS DO ANIMAL PARA EM ADOÇÃO PARA DEIXÁ-LO EM RESERVA
    public boolean reservaAnimal(AnimalBean animal) throws SQLException{
        
        try{
            String sql = "UPDATE ANIMAL SET STATUS = 'E'"+
                         "WHERE ID = ?";

            PreparedStatement stm = con.getConnection().prepareStatement(sql);
            
            stm.setInt(1, animal.getId());
            
            stm.execute();
            con.getConnection().commit();
            stm.close();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

//ATUALIZA O STATUS DO ANIMAL PARA AUTORIZADO PARA EXIBIR NO DELIVERY
    public boolean autorizaAnimal(AnimalBean animal) throws SQLException{
        
        try{
            String sql = "UPDATE ANIMAL SET STATUS = 'D'"+
                         "WHERE ID = ?";

            PreparedStatement stm = con.getConnection().prepareStatement(sql);
            
            stm.setInt(1, animal.getId());
            
            stm.execute();
            con.getConnection().commit();
            stm.close();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    public boolean sinalizaAdotado(AnimalBean animal) throws SQLException{
        
        try{
            String sql = "UPDATE ANIMAL SET STATUS = 'A'"+
                         "WHERE ID = ?";

            PreparedStatement stm = con.getConnection().prepareStatement(sql);
            
            stm.setInt(1, animal.getId());
            
            stm.execute();
            con.getConnection().commit();
            stm.close();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
}

