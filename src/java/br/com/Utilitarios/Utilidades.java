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

/**
 *
 * @author Igor
 */
public class Utilidades {

    public boolean enviaEmail(String destinatario, String nomeDestinatario, String assunto, String msg) {
        SimpleEmail email = new SimpleEmail();
        FacesContext contexto = FacesContext.getCurrentInstance();

        try {

            email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
            email.setSmtpPort(587);
            email.addTo(destinatario, nomeDestinatario); //destinatário
            email.setFrom("igor.rogeriosilva@gmail.com", "Adote um Vira Lata"); // remetente
            email.setSubject(assunto); // assunto do e-mail
            email.setMsg(msg); //conteudo do e-mail
            //  email.setAuthentication("adote1filhote@gmail.com", "vaidarcerto");
            email.setAuthentication("igor.rogeriosilva@gmail.com", "#KING542270544#");
            email.setSSL(true);
//            email.setTLS(true);
            email.send(); //envia o e-mail        
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
