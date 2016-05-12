package Email;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email {

    public static void enviaEmail(String destinatario, File certificado) throws UnsupportedEncodingException {

        final String username = "tccanhembicco@gmail.com";
        final String password = "vilaolimpia";

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tccanhembicco@gmail.com", "Anhembi Morumbi - TCC / CCO"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destinatario));
            message.setSubject("Certificado Digital");
            message.setText("Segue seu certificado digital em anexo.");

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            String fileName = "Certificado.cer";
            DataSource source = new FileDataSource(certificado);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            System.out.println("Enviando...");

            Transport.send(message);

            System.out.println("Enviado com sucesso!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
