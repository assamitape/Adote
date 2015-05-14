package br.com.DAO;

import br.com.Utilitarios.Conexao;
import br.com.Bean.AnimalBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
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
import java.sql.Connection;

public class AnimalDAO implements Serializable {

    // private Conexao con;
    private Connection userConn;

    private static final long serialVersionUID = 1L;

    public AnimalDAO() {
        //    con = new Conexao();
    }

    public boolean salvarAnimal(AnimalBean animal) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;

        try {

            String sql = "INSERT INTO ANIMAL( "
                    + "DESCRICAO, IDADE, HISTORICO, STATUS, ID_CLIENTE, VACINADO, "
                    + "VERMIFUGADO, SEXO, FOTO_PRINCIPAL, FOTO1, FOTO2, FOTO3, DT_CADASTRO, "
                    + "DT_AUTORIZACAO) VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            /*FileInputStream fis; 
            
             File f = new File((String) animal.getFotoPrincipal());  
             fis = new FileInputStream(f);
             */
            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

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
            stm.setBytes(10, animal.getFoto1());
            stm.setBytes(11, animal.getFoto2());
            stm.setBytes(12, animal.getFoto3());
            stm.setDate(13, new Date(System.currentTimeMillis()));  //obtendo o sysdate
            stm.setDate(14, null);  //essa data só será preenchida na autorização 
            stm.execute();
//            con.commit();
            stm.close();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
            }
        }
    }

    public boolean alterarAnimal(AnimalBean animal) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;

        try {
            String sql = "UPDATE ANIMAL SET DESCRICAO = ? , IDADE = ? , HISTORICO = ? , STATUS = ? , VACINADO = ? , VERMIFUGADO = ? "
                    + ", SEXO = ? , FOTO_PRINCIPAL = ?, FOTO1 = ?, FOTO2 = ?, FOTO3 = ?  WHERE ID = ?";

            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setString(1, animal.getDescricao());
            stm.setInt(2, animal.getIdade());
            stm.setString(3, animal.getHistorico());
            stm.setString(4, animal.getStatus());
            stm.setString(5, animal.getVacinado());
            stm.setString(6, animal.getVermifugado());
            stm.setString(7, animal.getSexo());
            stm.setBytes(8, animal.getFotoPrincipal());
            stm.setBytes(9, animal.getFoto1());
            stm.setBytes(10, animal.getFoto2());
            stm.setBytes(11, animal.getFoto3());
            stm.setInt(12, animal.getId());

            stm.execute();
            //  con.commit();
            stm.close();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
            }
        }
    }

    public boolean excluirAnimal(AnimalBean animal) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;

        try {
            String sql = "DELETE FROM ANIMAL WHERE ID = ?";

            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setInt(1, animal.getId());
            stm.execute();
            //  con.commit();
            stm.close();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
            }
        }
    }

    public List<AnimalBean> listarAnimalGeral() throws SQLException {

        List<AnimalBean> lista = new ArrayList<>();
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT ID, DESCRICAO, IDADE, HISTORICO, STATUS, ID_CLIENTE, VACINADO, VERMIFUGADO , SEXO FROM ANIMAL";
            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            rs = stm.executeQuery();

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
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(rs);
            }
        }
        return lista;
    }

    public List<AnimalBean> listarAnimalParaDoacao() throws SQLException {

        List<AnimalBean> lista = new ArrayList<AnimalBean>();
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;
        try {

            String sql = "SELECT A.ID, A.DESCRICAO, A.IDADE, A.HISTORICO, A.STATUS, A.ID_CLIENTE, "
                    + "   A.VACINADO, A.VERMIFUGADO, A.SEXO, A.FOTO_PRINCIPAL, A.FOTO1, A.FOTO2, A.FOTO3, "
                    + "   C.BAIRRO, C.CIDADE, C.ESTADO"
                    + "   FROM ANIMAL A, CLIENTE C WHERE A.ID_CLIENTE = C.ID AND A.STATUS = 'D' "
                    + " ORDER BY DT_AUTORIZACAO DESC";
            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            rs = stm.executeQuery();

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
                animal.setFoto1(rs.getBytes("FOTO1"));
                animal.setFoto2(rs.getBytes("FOTO2"));
                animal.setFoto3(rs.getBytes("FOTO3"));
                animal.setCliBairro(rs.getString("BAIRRO"));
                animal.setCliCidade(rs.getString("CIDADE"));
                animal.setCliEstado(rs.getString("ESTADO"));
                lista.add(animal);
            }
            stm.close();

        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(rs);
            }
        }
        return lista;

    }

//ATUALIZA O STATUS DO ANIMAL PARA EM ADOÇÃO PARA DEIXÁ-LO EM RESERVA
    public boolean reservaAnimal(AnimalBean animal) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;

        try {
            String sql = "UPDATE ANIMAL SET STATUS = 'E'"
                    + "WHERE ID = ?";
            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setInt(1, animal.getId());

            stm.execute();
            //  con.commit();
            stm.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
            }
        }
    }

//ATUALIZA O STATUS DO ANIMAL PARA AUTORIZADO PARA EXIBIR NO DELIVERY
    public boolean autorizaAnimal(AnimalBean animal) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        try {
            String sql = "UPDATE ANIMAL SET STATUS = 'D'"
                    + "WHERE ID = ?";
            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setInt(1, animal.getId());

            stm.execute();
            //  con.commit();
            stm.close();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
            }
        }
    }

    public boolean negaAnimal(AnimalBean animal) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        try {
            String sql = "UPDATE ANIMAL SET STATUS = 'N' "
                    + "WHERE ID = ?";
            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setInt(1, animal.getId());

            stm.execute();
            //  con.commit();
            stm.close();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        } finally {
            Conexao.close(stm);

            if (!isConnSupplied) {
                Conexao.close(con);
            }
        }
    }

    public boolean sinalizaAdotado(AnimalBean animal) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        try {
            String sql = "UPDATE ANIMAL "
                    + " SET STATUS = 'A', "
                    + " DT_AUTORIZACAO = " + new Date(System.currentTimeMillis())
                    + "WHERE ID = ?";
            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setInt(1, animal.getId());

            stm.execute();
            // con.commit();
            stm.close();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
            }
        }
    }

    public List<AnimalBean> listarAnimalExibicaoVencida() throws SQLException {

        List<AnimalBean> lista = new ArrayList<>();
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT ID, DESCRICAO, ID_CLIENTE FROM ANIMAL where status = 'D' and cast(dt_autorizacao as date) + interval '1 week' >= ?";
            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);
            
            stm.setDate(1, new Date(System.currentTimeMillis()) );
            
            rs = stm.executeQuery();

            while (rs.next()) {
                AnimalBean animal = new AnimalBean();

                animal.setId(rs.getInt("ID"));
                animal.setDescricao(rs.getString("DESCRICAO"));
                animal.setIdCliente(rs.getInt("ID_CLIENTE"));

                lista.add(animal);
            }
            stm.close();
            return lista;
        } catch (Exception ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(rs);
            }
        }
        return lista;
    }

}
