package br.com.Utilitarios;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.primefaces.model.DefaultStreamedContent;

/**
 *
 * @author Igor
 */
public class Utilidades {

    public String enviaEmail(String destinatario, String nomeDestinatario, String assunto, String msg ){
        SimpleEmail email = new SimpleEmail();
        
        try {

            email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
            email.setSmtpPort(587);
            email.addTo(destinatario, nomeDestinatario); //destinatário
            email.setFrom("adote1filhote@gmail.com", "Adote um Vira Lata"); // remetente
            email.setSubject(assunto); // assunto do e-mail
            email.setMsg(msg); //conteudo do e-mail
          //  email.setAuthentication("adote1filhote@gmail.com", "vaidarcerto");
            email.setAuthentication("igor.rogeriosilva@gmail.com", "#king542270544#");
            email.setSSL(true);   
            email.setTLS(true);
            email.send(); //envia o e-mail        

        } catch (EmailException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
            return "Problema ao enviar a mensagem, favor entrar em contato com a administraçao. "+ex.getMessage();
        }
        
        return "Um email foi enviado ao "+nomeDestinatario;
    }
    
}
