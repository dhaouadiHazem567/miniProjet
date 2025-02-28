package org.example.miniprojet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("dhaouadi.hazem567@gmail.com");
        mailSender.send(message);
    }

    @Override
    public void sendMailToContact(String email, String username, String password) {
        String mailContent = "Hello " + username + ",\n\n"
                + "Your account has been successfully created.\n"
                + "Here are your login credentials:\n\n"
                + "Username: " + username + "\n"
                + "Password: " + password + "\n\n"
                + "For security reasons, please change your password as soon as possible.\n\n"
                + "Best regards,\n"
                + "Your Team";

        String subject = "Your Account Has Been Created";
        sendMail(email, subject, mailContent);
    }
}
