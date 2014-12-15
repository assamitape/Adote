
package br.com.Utilitarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {
    private final String driver = "org.postgresql.Driver";
    private final String url ="jdbc:postgresql://localhost:5432/adoteumv_adoteumcao";
    private final String usuario = "postgres";
    private final String senha = "542270544";
    private Connection con;

    public Conexao() {
        try { 
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnection(){
        if(con == null){
            try {
                con = DriverManager.getConnection(url, usuario, senha);
                con.setAutoCommit(false);
            } catch (SQLException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return con;
    }
    
    public void desconecta(){
        try {
            con.close();
            con = null;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
