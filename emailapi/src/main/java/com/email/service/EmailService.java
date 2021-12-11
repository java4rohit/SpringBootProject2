package com.email.service;

import org.springframework.stereotype.Service;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
@Service
public class EmailService {

    public  boolean sendEmail(String message, String subject, String to) {
        //variable for gmail
        boolean f=false;
        String from="rohitforaxel@gmail.com";
        String host="smtp.gmail.com";
        //get the system properties9

        Properties properties=System.getProperties();
        System.out.println("PROPERTIES"+properties);

        //setting important information to properties object

        //host set
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        //Set1:get the Session object :
        Session session=Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("rohitforaxel@gmail.com", "rohitrohit1#");
            }

        });

        session.setDebug(true);

        //set 2: Compose the message [text , multi, media]
        MimeMessage mimeMessage =new MimeMessage(session);

        //from email
        try {

            mimeMessage.setFrom(from);
            //adding recipient
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

            //adding subject to  messgae
            mimeMessage.setSubject(subject);

            //adding text to message
            mimeMessage.setText(message);

            //send
            //step 3:send the message using transport class
            Transport.send(mimeMessage);
            System.out.println("send success.......................");
            f=true;

        } catch (MessagingException e) {

            e.printStackTrace();
        }
        return f;
    }





}
