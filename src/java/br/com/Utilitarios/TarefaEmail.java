package br.com.Utilitarios;

import br.com.Bean.AnimalBean;
import br.com.Bean.ClienteConsultaBean;
import br.com.DAO.AnimalDAO;
import br.com.DAO.ClienteDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author igor
 */
public class TarefaEmail implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
//        AnimalDAO animal = new AnimalDAO();
//        ClienteDAO cliente = new ClienteDAO();
//        Utilidades util = new Utilidades();
//        try {
//
//            for (AnimalBean animalB : animal.listarAnimalExibicaoVencida()) {
//                //retorna dono do animal
//                cliente.retornaCliente(animalB.getIdCliente());
//                //obtem uma instancia de cliente bean para consulta sem interferir no sistema
//                ClienteConsultaBean cliConBean = ClienteConsultaBean.getInstancia();
//                //tira o animal da página de animais disponiveis
//                animal.negaAnimal(animalB);
//                //envia email para o dono, avisando desativação
//                util.enviaEmail(cliConBean.getEmail(), cliConBean.getNome(), "Aviso de remoção", "Devido ao tempo de disponibilidade"
//                        + " dos seus animais sem adoção, eles será removidos da grade de animais disponível. Demais dúvidas entrar em contato com a administração.");
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(TarefasAgendadas.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println("meu job quartz!!!!!!!!!!!!!!!");
    }
}
