package Email;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Email {

    public static void enviaEmail(String destinatario, File certificado, String nome) throws UnsupportedEncodingException, Exception {

        splashEmail sEmail = new splashEmail();

        try {

            new Thread() {
                @Override
                public void run() {

                    try {

                        sEmail.setVisible(true);
                        System.out.println("Enviando...");

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

                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("tccanhembicco@gmail.com", "Anhembi Morumbi - TCC / CCO"));
                        message.setRecipients(Message.RecipientType.TO,
                                InternetAddress.parse(destinatario));
                        message.setSubject("Certificado Digital");
                        message.setText("Segue seu certificado digital em anexo.");

                        MimeBodyPart messageBodyPart = new MimeBodyPart();

                        Multipart multipart = new MimeMultipart();

                        messageBodyPart = new MimeBodyPart();
                        String fileName = nome + ".cer";
                        DataSource source = new FileDataSource(certificado);
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(fileName);
                        multipart.addBodyPart(messageBodyPart);

                        message.setContent(multipart);

                        Transport.send(message);

                        sEmail.setVisible(false);
                        sEmail.dispose();

                        System.out.println("Enviado com sucesso!");
                        JOptionPane.showMessageDialog(null, "Email Enviado com Sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);

                    } catch (MessagingException ex) {
                        Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
                        sEmail.setVisible(false);
                        sEmail.dispose();
                        JOptionPane.showMessageDialog(null, "Falha no Envio do E-mail\n" + ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
                        sEmail.setVisible(false);
                        sEmail.dispose();
                        JOptionPane.showMessageDialog(null, "Falha no Envio do E-mail\n" + ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        sEmail.setVisible(false);
                        sEmail.dispose();
                        JOptionPane.showMessageDialog(null, "Falha no Envio do E-mail\n" + ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }.start();

        } catch (Exception e) {
            throw e;
        }
    }
}
