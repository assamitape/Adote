package br.com.DAO;

import br.com.Bean.AnimalBean;
import br.com.Utilitarios.Conexao;
import java.sql.PreparedStatement;
import br.com.Bean.ClienteBean;
import br.com.Bean.ClienteConsultaBean;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.com.Utilitarios.Utilidades;

public class ClienteDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    // private Conexao con;
    private Connection userConn;
    PreparedStatement stm = null;

    public ClienteDAO() {
        //con = new Conexao();  
    }

    public boolean salvarCliente(ClienteBean cliente) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        try {
            String sql = "INSERT INTO CLIENTE(NOME, EMAIL, SENHA, TELEFONE, SN_ATIVO , ESTADO, CIDADE, BAIRRO )"
                    + " VALUES(?,?,?,?,?,?,?,?);";

            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setString(1, cliente.getNome());
            stm.setString(2, cliente.getEmail());
            stm.setString(3, Utilidades.cripto(cliente.getSenha()));
            stm.setString(4, cliente.getTelefone());
            cliente.setSnAtivo("S");
            stm.setString(5, cliente.getSnAtivo());
            stm.setString(6, cliente.getEstado());
            stm.setString(7, cliente.getCidade());
            stm.setString(8, cliente.getBairro());

            stm.execute();
            //con.commit();
            stm.close();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
            }
        }
    }

    public boolean alterarCliente(ClienteBean cliente) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;

        try {
            String sql = "UPDATE CLIENTE SET NOME = ?, EMAIL = ?, TELEFONE = ?, "
                    + " SN_ATIVO = ?, ESTADO = ?, CIDADE = ?, BAIRRO = ? WHERE ID = ?;";

            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setString(1, cliente.getNome());
            stm.setString(2, cliente.getEmail());
            stm.setString(3, cliente.getTelefone());
            stm.setString(4, cliente.getSnAtivo());
            stm.setString(5, cliente.getEstado());
            stm.setString(6, cliente.getCidade());
            stm.setString(7, cliente.getBairro());
            stm.setInt(8, cliente.getId());

            stm.execute();

            //con.commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
            }
        }
    }

    public String excluirCliente(ClienteBean cliente) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        PreparedStatement stmAnimalCli = null;
        ResultSet rsAnimalCli = null;
        String idsAnimais = "";

        try {

            String sqlAnimalCli = "select id from animal where id_cliente = ?";
            
            con = isConnSupplied ? userConn : Conexao.getConnection();
            stmAnimalCli = con.prepareStatement(sqlAnimalCli);
            
            stmAnimalCli.setInt(1, cliente.getId());
            
            rsAnimalCli = stmAnimalCli.executeQuery();

            //lista os animais desse dono
            while (rsAnimalCli.next()) {
                if (idsAnimais.isEmpty()) {
                    idsAnimais = rsAnimalCli.getString("id");
                } else {
                    idsAnimais = idsAnimais + ", " + rsAnimalCli.getString("id");
                }
            }

            if (!idsAnimais.isEmpty()) {
                return "Não foi possível deletar, este cliente possui os animais " + idsAnimais;
            } else {

                String sql = "DELETE FROM CLIENTE WHERE ID = ?";
                stm = con.prepareStatement(sql);

                stm.setInt(1, cliente.getId());
                stm.execute();
                //con.commit();

                return "";
            }
        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Aconteceu um Erro Na tentativa de deleção, Favor reportar o problema.";
        } finally {
            Conexao.close(stm);
            Conexao.close(stmAnimalCli);
            Conexao.close(rsAnimalCli);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
            }
        }
    }

    public boolean ativarCliente(ClienteBean cliente) throws SQLException {

        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;

        try {
            String sql = "UPDATE CLIENTE SET SN_ATIVO = 'S' WHERE ID = ?";

            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setInt(1, cliente.getId());
            stm.execute();
            //con.commit();

            return true;

        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
            }
        }
    }

    public boolean desativarCliente(ClienteBean cliente) throws SQLException {

        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;

        try {
            String sql = "UPDATE CLIENTE SET SN_ATIVO = 'N' WHERE ID = ?";

            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setInt(1, cliente.getId());
            stm.execute();
            //con.commit();

            return true;

        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
            }
        }
    }

    public List<ClienteBean> listarClientes() throws SQLException {

        List<ClienteBean> lista = new ArrayList<>();
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;

        try {

            String sql = "SELECT ID, NOME, EMAIL, TELEFONE, SN_ATIVO, ESTADO, CIDADE, BAIRRO FROM CLIENTE ORDER BY ID ";
            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            rs = stm.executeQuery();

            while (rs.next()) {

                ClienteBean cliente = new ClienteBean();

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

            return lista;

        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return lista;

        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
                Conexao.close(rs);
            }
        }
    }

    public List<AnimalBean> listarAnimalCliente() throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;

        List<AnimalBean> lista = new ArrayList<AnimalBean>();

        con = isConnSupplied ? userConn : Conexao.getConnection();
        ClienteBean cliente = ClienteBean.getInstancia();

        try {

            String sql = "SELECT ID, DESCRICAO, IDADE, HISTORICO, STATUS, ID_CLIENTE, "
                    + "          VACINADO, VERMIFUGADO, SEXO "
                    + "     FROM ANIMAL "
                    + "    WHERE id_cliente = ? "
                    + "    ORDER BY DT_AUTORIZACAO DESC";
            stm = con.prepareStatement(sql);
            stm.setInt(1, cliente.getId());
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

        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return lista;

        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
                Conexao.close(rs);
            }
        }
        return lista;

    }

    public String logar(String usuario, String senha) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;

        try {

            ClienteBean cliente = ClienteBean.getInstancia();
            boolean logou = false;

            String sql = "SELECT ID, NOME, SENHA, EMAIL, TELEFONE, SN_ATIVO, ESTADO, CIDADE, BAIRRO FROM CLIENTE WHERE EMAIL = ? AND SENHA = ? AND SN_ATIVO = 'S'";

            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, usuario);
            stm.setString(2, Utilidades.cripto(senha));

            rs = stm.executeQuery();

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

            if (logou && cliente.getId() > 0) {
                return "";
            } else {
                return "Email ou senha estão incorretos!";
            }

        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao tentar conectar";

        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
                Conexao.close(rs);
            }
        }
    }

    public boolean retornaCliente(int idCliente) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;

        ClienteConsultaBean clienteCon = ClienteConsultaBean.getInstancia();
        int existe = 0;

        try {

            String sql = "SELECT ID, NOME, SN_ATIVO, EMAIL, TELEFONE, ESTADO, CIDADE, BAIRRO FROM CLIENTE WHERE ID = ? ";
            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, idCliente);
            rs = stm.executeQuery();

            while (rs.next()) {
                existe = 1;

                clienteCon.setId(rs.getInt("ID"));
                clienteCon.setNome(rs.getString("NOME"));
                clienteCon.setSnAtivo(rs.getString("SN_ATIVO"));
                clienteCon.setEmail(rs.getString("EMAIL"));
                clienteCon.setTelefone(rs.getString("TELEFONE"));
                clienteCon.setEstado(rs.getString("ESTADO"));
                clienteCon.setCidade(rs.getString("CIDADE"));
                clienteCon.setBairro(rs.getString("BAIRRO"));

            }

            if (existe > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
                Conexao.close(rs);
            }
        }
    }

    public String verificaClienteExistente(String usuario) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;

        try {

            ClienteBean cliente = ClienteBean.getInstancia();
            boolean achou = false;

            String sql = "SELECT COUNT(*) FROM CLIENTE WHERE EMAIL = ? AND SN_ATIVO = 'S'";

            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setString(1, usuario);

            rs = stm.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    achou = true;
                } else {
                    achou = false;
                }
            }

            if (achou) {
                return "Email já cadastrado!";
            } else {
                return "";
            }
        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao tentar validar o email!";

        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
                Conexao.close(rs);
            }
        }

    }

    public boolean alterarSenha(String usuarioEmail, String novaSenha) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;

        try {
            String sql = "UPDATE CLIENTE SET SENHA = ? WHERE EMAIL = ?;";

            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setString(1, Utilidades.cripto(novaSenha));
            stm.setString(2, usuarioEmail);

            stm.execute();

            //con.commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
                Conexao.close(rs);
            }
        }
    }
}
