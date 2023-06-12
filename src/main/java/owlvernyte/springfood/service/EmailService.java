package owlvernyte.springfood.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import owlvernyte.springfood.entity.EmailDescription;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public String sendEmail(EmailDescription details) throws Exception {
        String body = "";
        body += "From: " + details.getName() + " " + "(" + details.getEmail() + ")" + "\n\n";
        body += "Content: " + details.getBody();

        // Creating a simple mail message
        SimpleMailMessage mailMessage
                = new SimpleMailMessage();

        // Setting up necessary details
        mailMessage.setFrom(sender);
        mailMessage.setTo(sender);
        mailMessage.setText(body);
        mailMessage.setSubject(details.getSubject());

        // Sending the mail
        mailSender.send(mailMessage);
        return "Mail Sent Successfully...";

    }

    public String sendHtmlEmail(EmailDescription details) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(sender));
        message.setRecipients(MimeMessage.RecipientType.TO, sender);
        message.setSubject("[Spring Food] " + details.getSubject());

        String htmlContent = "<p>From: " + details.getName() + " (" + details.getEmail() + ")" + "</p><br>" +
                "<h1>" + details.getSubject() + "</h1>" +
                "<p>" + details.getBody() + "</p>";
        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);
        return "Success";
    }

}
