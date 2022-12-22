import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SecretSanta {
  public static void sendEmails(List<String> emailAddresses, String senderEmail, String senderPassword) { 
  
        Collections.shuffle(emailAddresses);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        //relies on which provider you are using to send your mail
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        //you need to allow less secure apps to access your email account
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, senderPassword);
                    }
                });

        for (int i = 0; i < emailAddresses.size(); i++) {
            String recipient = emailAddresses.get(i);
            String secretSanta = emailAddresses.get((i + 1) % emailAddresses.size());

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                //set subject of mail
                message.setSubject("Secret Santa Bot");
                //set text message
                message.setText("Selam Saygın Home Grubu Üyesi,\n\nSecret Santa'da sana " + secretSanta + " çıktı.\n\nMerry Christmas!\n\nHediyeleri Aralık ayının 31'inde yapacağımız partide birbirimize teslim edeceğiz\n\nLimitin 149TL olduğunu unutma.\n\nSecret Santa Bot.");

                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }
    }
}
