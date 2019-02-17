package rsparano;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
* Java Program to send text mail using default SMTP server and without authentication.
* You need mail.jar, smtp.jar and activation.jar to run this program.
*
* @author Javin Paul
* Edited by R. Sparano
*/
public class EmailSender{

    public static void main(String args[]) {
        String to = "Roseanne.Sparano@oracle.com";            // sender email
        String from = "Roseanne.sparano@oracle.com";       // receiver email
        String host = "internal-mail-router.oracle.com";    // mail server host

       Properties properties = System.getProperties();
       properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties); // default session

        try {
            MimeMessage message = new MimeMessage(session);        // email message
            message.setFrom(new InternetAddress(from));                    // setting header fields
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("System Information from rsparano-us"); // subject line
         
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            
            // Now set the actual message
            messageBodyPart.setText("Here is the latest System information from rsparano-us.");
            
            // actual mail body
            //message.setText("You can send mail from Java program by using mail API, but you need"
              //      + "couple of more JAR files e.g. smtp.jar and activation.jar");
         
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
         
            
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "C:\\Users\\rsparano\\scripts\\SysINfo\\slc10yvl_SysOutput.txt";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);
            // Send message
            Transport.send(message);
            System.out.println("Email Sent successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
