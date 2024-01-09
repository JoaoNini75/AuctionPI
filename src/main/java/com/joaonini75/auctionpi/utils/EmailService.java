package com.joaonini75.auctionpi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String SENDER = System.getenv("AuctionPI_MAIL_USER");

    public EmailService() {
        this.javaMailSender = new JavaMailSenderImpl();
    }

    public String sendSimpleMail(String recipient, String msgBody,
                                 String subject, String attachment) {

        System.out.println("\n\n     " + javaMailSender == null);
        System.out.println("         SENDER: " + SENDER + "\n\n");

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(SENDER);
            mailMessage.setTo(recipient);
            mailMessage.setText(msgBody);
            mailMessage.setSubject(subject);

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";


        /* Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }*/
    }
}
