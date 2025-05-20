package com.example.livraision_back.service;

import com.example.livraision_back.model.Client;
import com.example.livraision_back.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final ClientRepository clientRepository;
    private final SmsService smsService; // À implémenter ou simuler

    /**
     * Envoie un code de réinitialisation au numéro fourni s’il existe.
     */
    public void sendResetCode(String telephone) {
        Optional<Client> clientOpt = clientRepository.findByTelephone(telephone);

        if (clientOpt.isEmpty()) {
            // Par sécurité, on ne révèle pas si le numéro n’existe pas
            return;
        }

        String code = generateResetCode();

        Client client = clientOpt.get();
        client.setResetCode(code);
        client.setResetCodeExpiry(LocalDateTime.now().plusMinutes(10)); // expire dans 10 minutes

        clientRepository.save(client);

        smsService.sendSms(telephone, "Votre code de réinitialisation est : " + code);
    }

    /**
     * Vérifie le code reçu par l'utilisateur et met à jour son mot de passe si valide.
     */
    public boolean resetPassword(String telephone, String code, String nouveauMotDePasse) {
        Optional<Client> clientOpt = clientRepository.findByTelephone(telephone);

        if (clientOpt.isEmpty()) return false;

        Client client = clientOpt.get();

        if (client.getResetCode() == null || client.getResetCodeExpiry() == null) return false;

        if (!client.getResetCode().equals(code)) return false;

        if (client.getResetCodeExpiry().isBefore(LocalDateTime.now())) return false;

        // Code valide : on met à jour le mot de passe
        client.setMotDePasse(nouveauMotDePasse); // 🔐 À encoder avec BCrypt si tu veux
        client.setResetCode(null);
        client.setResetCodeExpiry(null);

        clientRepository.save(client);
        return true;
    }

    /**
     * Génère un code aléatoire à 6 chiffres.
     */
    private String generateResetCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
}
