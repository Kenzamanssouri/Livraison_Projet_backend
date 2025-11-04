package com.example.livraision_back.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendValidationEmail(String toEmail, String vendeurNom) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Validation de votre compte vendeur");
        message.setText("Bonjour " + vendeurNom + ",\n\n" +
            "Votre compte vendeur a été validé avec succès par l’administrateur.\n\n" +
            "Cordialement,\nL'équipe de livraison");

        mailSender.send(message);
    }


    public void sendRefusEmail(String toEmail, String vendeurNom,String motif) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Rejet de votre compte vendeur");
        message.setText("Bonjour " + vendeurNom + ",\n\n" +
            "Votre compte vendeur a été reeté par l’administrateur.\n\n" +
            "pour la raison suivante :"+motif+",\n\n" +
            "Cordialement,\nL'équipe de livraison");

        mailSender.send(message);
    }
}
