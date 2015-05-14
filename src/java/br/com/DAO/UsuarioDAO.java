package br.com.DAO;

import br.com.Utilitarios.Conexao;
import java.sql.PreparedStatement;
import br.com.Bean.UsuarioBean;
import br.com.Utilitarios.Utilidades;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    // private Conexao con;
    private Connection userConn;
    PreparedStatement stm = null;

    public UsuarioDAO() {
//        con = new Conexao();  
    }

    public boolean salvarUsuario(UsuarioBean usuario) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;

        try {
            String sql = "INSERT INTO USUARIO(NOME, LOGIN, SENHA, STATUS, PERMISSAO )"
                    + " VALUES(?,?,?,?,?);";

            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setString(1, usuario.getNome());
            stm.setString(2, usuario.getLogin());
            stm.setString(3, usuario.getSenha());
            stm.setString(4, usuario.getStatus());
            stm.setString(5, usuario.getPermissao());
            stm.executeUpdate();

            //con.getConnection().commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
            }
        }

    }

    public boolean alterarUsuario(UsuarioBean usuario) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;

        try {
            String sql = "UPDATE USUARIO SET NOME = ?, LOGIN = ?, SENHA = ?, "
                    + " STATUS = ?, PERMISSSAO = ? WHERE ID = ?;";

            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setString(1, usuario.getNome());
            stm.setString(2, usuario.getLogin());
            stm.setString(3, usuario.getSenha());
            stm.setString(4, usuario.getStatus());
            stm.setString(5, usuario.getPermissao());
            stm.setInt(6, usuario.getId());

            stm.executeUpdate();

            //con.getConnection().commit();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
            }
        }
    }

    public boolean excluirUsuario(UsuarioBean usuario) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;

        try {
            String sql = "DELETE FROM USUARIO WHERE ID = ?";

            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setInt(1, usuario.getId());
            stm.execute();
         //   con.commit();

            return true;

        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
            }
        }
    }

    public String logar(String usuario, String senha) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;

        try {

            UsuarioBean usuarioB = new UsuarioBean();
            boolean logou = false;
            // SELECT ID, NOME, LOGIN, SENHA, STATUS, PERMISSAO FROM USUARIO
            String sql = "SELECT ID, NOME, LOGIN, SENHA, STATUS, PERMISSAO FROM USUARIO WHERE upper(LOGIN) = upper(?) AND SENHA = ? AND STATUS = 'A'";

            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setString(1, usuario);
            stm.setString(2,Utilidades.cripto(senha));
//            stm.executeQuery();
            rs = stm.executeQuery();
            while (rs.next()) {
                logou = true;

                usuarioB.setId(rs.getInt("ID"));
                usuarioB.setNome(rs.getString("NOME"));
                usuarioB.setSenha(rs.getString("SENHA"));
                usuarioB.setStatus(rs.getString("STATUS"));
                usuarioB.setPermissao(rs.getString("PERMISSAO"));
            }

            if (logou && usuarioB.getId() > 0) {
                return "";
            } else {
                return "Login ou senha estão incorretos!";
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "ERRO AO TENTAR LOGAR!";
        } finally {
            Conexao.close(stm);
            if (!isConnSupplied) {
                Conexao.close(con);
                Conexao.close(stm);
                Conexao.close(rs);

            }
        }

    }

    public boolean retornaUsuario(int idUsuario) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;

        UsuarioBean usuario = new UsuarioBean();
        int existe = 0;

        try {

            String sql = "SELECT ID, NOME FROM USUARIO WHERE ID = ? ";
            con = isConnSupplied ? userConn : Conexao.getConnection();
            stm = con.prepareStatement(sql);

            stm.setInt(1, idUsuario);

            rs = stm.executeQuery();

            while (rs.next()) {
                existe = 1;

                usuario.setId(rs.getInt("ID"));
                usuario.setNome(rs.getString("NOME"));

            }

            if (existe > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
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

    public String verificaUsuarioExistente(String usuario) throws SQLException {
        final boolean isConnSupplied = (userConn != null);
        PreparedStatement stm = null;
        Connection con = null;
        ResultSet rs = null;

        try {

            UsuarioBean usuarioB = new UsuarioBean();
            boolean achou = false;

            String sql = "SELECT COUNT(*) FROM USUARIO WHERE LOGIN = ? AND STATUS = 'A'";

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
                return "usuário já cadastrado!";
            } else {
                return "";
            }
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao tentar validar o login!";
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

            stm.setString(1, novaSenha);
            stm.setString(2,Utilidades.cripto(usuarioEmail) );

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
