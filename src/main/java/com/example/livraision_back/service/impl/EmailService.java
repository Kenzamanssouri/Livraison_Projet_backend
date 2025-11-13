package com.example.livraision_back.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendValidationEmailVendeur(String toEmail, String vendeurNom) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Validation de votre compte vendeur");
        message.setText("Bonjour " + vendeurNom + ",\n\n" +
            "Votre compte vendeur a été validé avec succès par l’administrateur.\n\n" +
            "Cordialement,\nL'équipe de livraison");

        mailSender.send(message);
    }

    public void sendValidationEmailLivreur(String toEmail, String vendeurNom) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Validation de votre compte Livreur");
        message.setText("Bonjour " + vendeurNom + ",\n\n" +
            "Votre compte Livreur a été validé avec succès par l’administrateur.\n\n" +
            "Cordialement,\nL'équipe de livraison");

        mailSender.send(message);
    }
    public void sendCreationLivreur(String toEmail, String vendeurNom) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Creation de votre compte Livreur");
        message.setText("Bonjour " + vendeurNom + ",\n\n" +
            "Votre compte livreur a été crée avec succès mais il fallait attendtre validation de l’administrateur.\n\n" +
            "Cordialement,\nL'équipe de livraison");

        mailSender.send(message);
    }
    public void sendCreationVendeur(String toEmail, String vendeurNom) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Creation de votre compte Vendeur");
        message.setText("Bonjour " + vendeurNom + ",\n\n" +
            "Votre compte vendeur a été crée avec succès mais il fallait attendtre validation de l’administrateur.\n\n" +
            "Cordialement,\nL'équipe de livraison");

        mailSender.send(message);
    }

    public void sendRefusEmailVendeur(String toEmail, String vendeurNom,String motif) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Rejet de votre compte vendeur");
        message.setText("Bonjour " + vendeurNom + ",\n\n" +
            "Votre compte vendeur a été reeté par l’administrateur.\n\n" +
            "pour la raison suivante :"+motif+",\n\n" +
            "Cordialement,\nL'équipe de livraison");

        mailSender.send(message);
    }

    public void sendRefusEmailLivreur(String toEmail, String vendeurNom,String motif) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Rejet de votre compte livreur");
        message.setText("Bonjour " + vendeurNom + ",\n\n" +
            "Votre compte livreur a été reeté par l’administrateur.\n\n" +
            "pour la raison suivante :"+motif+",\n\n" +
            "Cordialement,\nL'équipe de livraison");

        mailSender.send(message);
    }
}
