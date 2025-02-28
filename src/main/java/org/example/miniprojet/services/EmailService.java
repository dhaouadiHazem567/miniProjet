package org.example.miniprojet.services;

public interface EmailService {

    void sendMail(String toEmail, String subject, String body);
    void sendMailToContact(String email, String username, String contactOwnerUsername);

}
