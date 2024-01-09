package com.joaonini75.auctionpi.utils;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    private final JavaMailSenderImpl mailSender;

    private static final String SENDER = System.getenv("AuctionPI_MAIL_USER");
    private static final String PWD = System.getenv("AuctionPI_MAIL_PWD");

    public EmailService() {
        this.mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(SENDER);
        mailSender.setPassword(PWD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    }

    public String sendSimpleMail(String recipient, String msgBody, String subject) {
        System.out.println("\n\nSENDER: " + SENDER + "\n\n");

        // Creating a simple mail message
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        // Setting up necessary details
        mailMessage.setFrom(SENDER);
        mailMessage.setTo(recipient);
        mailMessage.setText(msgBody);
        mailMessage.setSubject(subject);

        // Sending the mail
        mailSender.send(mailMessage);
        return "Mail Sent Successfully...";


        /* Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }*/
    }

    public String sendHTMLMail(String recipient, String msgBody, String subject) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress(SENDER));
            message.setRecipients(MimeMessage.RecipientType.TO, recipient);
            message.setSubject(subject);
            message.setContent(msgBody, "text/html; charset=utf-8");

            mailSender.send(message);
            return "Mail Sent Successfully...";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error while Sending Mail";
        }
    }
}
