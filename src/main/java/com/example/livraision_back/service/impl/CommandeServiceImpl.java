package com.example.livraision_back.service.impl;

import com.example.livraision_back.dto.CommandeDTO;
import com.example.livraision_back.dto.DashboardVendeurResponse;
import com.example.livraision_back.mapper.CommandeMapper;
import com.example.livraision_back.model.*;
import com.example.livraision_back.repository.ClientRepository;
import com.example.livraision_back.repository.CommandeRepository;
import com.example.livraision_back.repository.NotificationRepository;
import com.example.livraision_back.service.CommandeService;
import com.example.livraision_back.service.PushNotificationService;
import com.example.livraision_back.specification.CommandeSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class CommandeServiceImpl implements CommandeService {
    private final CommandeRepository commandeRepository;
    private final PushNotificationService pushService; // Le service qu'on va créer juste en dessous
    private final ClientRepository clientRepository;
    private final CommandeMapper commandeMapper;
    private final NotificationRepository notificationRepository;

    public CommandeServiceImpl(CommandeRepository commandeRepository, PushNotificationService pushService, ClientRepository clientRepository, CommandeMapper commandeMapper, NotificationRepository notificationRepository) {
        this.commandeRepository = commandeRepository;
        this.pushService = pushService;
        this.clientRepository = clientRepository;
        this.commandeMapper = commandeMapper;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Page<CommandeDTO> findAll(int page, int size) {
        // Création du PageRequest
        PageRequest pageRequest = PageRequest.of(page, size);

        // Récupération paginée depuis le repository
        Page<Commande> commandes = commandeRepository.findAllWithLignesAndProduits(pageRequest);

        // Conversion en DTO
        Page<CommandeDTO> dtoPage = commandes.map(commandeMapper::toDTO);

        return dtoPage;
    }


    @Override
    public List<CommandeDTO> findAll() {
        List<Commande> commandes = commandeRepository.findAll();

        // Conversion en DTO
        List<CommandeDTO> dtoList = commandes.stream()
            .map(commandeMapper::toDTO)
            .collect(Collectors.toList());

        return dtoList;
    }
    @Override
    public DashboardVendeurResponse getDashboard(Long vendeurId) {
        DashboardVendeurResponse dto = new DashboardVendeurResponse();

        // === 1) Dates utiles ===
        ZoneId zone = ZoneId.systemDefault(); // adapte si tu veux ZoneId.of("Africa/Casablanca")

        LocalDate today = LocalDate.now(zone);
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1);

        // Lundi de la semaine courante
        LocalDate startOfWeekDate = today.with(java.time.DayOfWeek.MONDAY);
        LocalDateTime startOfWeek = startOfWeekDate.atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusWeeks(1);

        // === 2) Volume commandes ===
        Long total = commandeRepository.countByVendeur_Id(vendeurId);
        Long todayCount = commandeRepository.countByVendeur_IdAndDateCommandeBetween(
            vendeurId, startOfToday, endOfToday);
        Long weekCount = commandeRepository.countByVendeur_IdAndDateCommandeBetween(
            vendeurId, startOfWeek, endOfWeek);

        dto.setTotalCommandes(total);
        dto.setToday(todayCount);
        dto.setWeek(weekCount);

        // === 3) Statuts ===
        Map<String, Long> statusMap = new HashMap<>();
        for (StatutCommande statut : StatutCommande.values()) {
            Long count = commandeRepository.countByVendeur_IdAndStatut(vendeurId, statut);
            statusMap.put(statut.name(), count);
        }
        dto.setStatus(statusMap);

        // === 4) Revenus ===
        Double revenueTotal = commandeRepository.sumTotalByVendeur(vendeurId);
        Double revenueToday = commandeRepository.sumTotalByVendeurAndDateBetween(
            vendeurId, startOfToday, endOfToday);
        Double revenueWeek = commandeRepository.sumTotalByVendeurAndDateBetween(
            vendeurId, startOfWeek, endOfWeek);
        Double avgBasket = commandeRepository.avgTotalByVendeur(vendeurId);

        dto.setRevenueTotal(revenueTotal);
        dto.setRevenueToday(revenueToday);
        dto.setRevenueWeek(revenueWeek);
        dto.setAvgBasket(avgBasket);

        // === 5) Moyens de paiement (en %) ===
        Long cashCount = commandeRepository.countByVendeur_IdAndModePaiement(vendeurId, "CASH");
        Long cardCount = commandeRepository.countByVendeur_IdAndModePaiement(vendeurId, "CARTE");

        long totalPaid = (cashCount != null ? cashCount : 0L)
            + (cardCount != null ? cardCount : 0L);

        DashboardVendeurResponse.PaymentStatsDTO paymentStats = new DashboardVendeurResponse.PaymentStatsDTO();
        if (totalPaid > 0) {
            double cashPercent = (cashCount * 100.0) / totalPaid;
            double cardPercent = (cardCount * 100.0) / totalPaid;
            paymentStats.setCash(cashPercent);
            paymentStats.setCard(cardPercent);
        } else {
            paymentStats.setCash(0.0);
            paymentStats.setCard(0.0);
        }
        dto.setPaymentStats(paymentStats);

        return dto;
    }
    // Méthode pour créer une commande texte libre
    public Commande creerCommandeTexteLibre(String loginClient, String texteLibre, String adresseLivraison) {

        // Récupérer le client
        Client u = clientRepository.findByLogin(loginClient)
            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));



        // Créer la ligneCommande
        LigneCommande ligne = new LigneCommande();
        ligne.setDescriptionLibre(texteLibre);
        ligne.setQuantite(1);
        ligne.setPrixUnitaire(0);

        // Créer la commande
        Commande commande = new Commande();
        commande.setClient(u);
        commande.setDateCommande(java.time.LocalDateTime.now());
        commande.setStatut(StatutCommande.EN_ATTENTE);
        commande.setLivraisonAdresse(adresseLivraison);
        commande.setLignes(java.util.List.of(ligne));
        commandeRepository.save(commande);
        Notification notif = new Notification();
        notif.setObject("Nouvelle commande");
        notif.setMessage("Nouvelle commande à livrer (#" + commande.getId() + ")");
        notif.setRole(RoleUtilisateur.LIVREUR);
        notif.setIdObject(commande.getId());
        notif.setOpened(false);

        notificationRepository.save(notif);
        // --- AJOUT POUR LE PUSH MOBILE ---
        // On envoie au "topic" livreurs. Tous les livreurs abonnés le recevront.
        pushService.sendPushToTopic(
            "livreurs",
            "Nouvelle commande !",
            "Une nouvelle commande est disponible à l'adresse : " + adresseLivraison
        );
        return commande;
    }
    @Override
    @Transactional(readOnly = true)
    public CommandeDTO findById(Long id) {

        Commande commande = commandeRepository.findByIdWithLignes(id)
            .orElseThrow(() ->
                new RuntimeException("Commande introuvable avec id : " + id)
            );
        commande.getLignes().forEach(l ->
            System.out.println("DESC = " + l.getDescriptionLibre())
        );

        return commandeMapper.toDTO(commande);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeDTO> findByLivreur(Long livreurId) {
        return commandeRepository.findByLivreur_Id(livreurId)
            .stream()
            .map(commandeMapper::toLivreurDTO)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeDTO> findByLivreurOrStatut(
        Long livreurId,
        StatutCommande statut
    ) {
        Specification<Commande> spec =
            CommandeSpecification.livreurOrStatut(livreurId, statut);

        return commandeRepository.findAll(spec)
            .stream()
            .map(commandeMapper::toLivreurDTO)
            .toList();
    }


}
