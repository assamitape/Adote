package br.com.Utilitarios;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.primefaces.model.DefaultStreamedContent;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.DefaultAuthenticator;

/**
 *
 * @author Igor
 */
public class Utilidades {

    public boolean enviaEmail(String destinatario, String nomeDestinatario, String assunto, String msg) {
        SimpleEmail email = new SimpleEmail();
        FacesContext contexto = FacesContext.getCurrentInstance();

        try {

            email.setHostName("sv1.k2host-01.com.br"); // o servidor SMTP para envio do e-mail
            email.setSSLOnConnect(true);
            email.setSslSmtpPort( "465" );
            email.setAuthenticator( new DefaultAuthenticator( "adocao@adoteumviralata.com" ,  "kn23md@vi27" ) );
            email.addTo(destinatario, nomeDestinatario); //destinatário
            email.setFrom("adocao@adoteumviralata.com", "Adote um Vira Lata"); // remetente
            email.setSubject(assunto); // assunto do e-mail
            email.setMsg(msg); //conteudo do e-mail
            //  email.setAuthentication("adote1filhote@gmail.com", "vaidarcerto");
            //  email.setAuthentication("adoteumv@adoteumviralata.com", "kn23md@vi27");
            email.setSSL(true);  
            email.setTLS(true);  
            email.send(); //envia o e-mail        
            
            /*
            contas de email oficiais do adote
            email: adocao@adoteumviralata.com
            senha: kn23md@vi27
            
            */
            return true;
        } catch (EmailException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public static String cripto(String texto) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(texto.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }

        String senhahex = hexString.toString();

        return senhahex;

    }

    public static String gerarSenhaRandon() {

        Random gerador = new Random();
        return Integer.toString(Math.abs(gerador.hashCode()));


    }

    public DefaultStreamedContent getImageFromByte(byte[] image) {
        return new DefaultStreamedContent(new ByteArrayInputStream(image));
    }
}
