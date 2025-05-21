package com.example.livraision_back.controller;

import com.example.livraision_back.dto.ClientDTO;
import com.example.livraision_back.model.Client;
import com.example.livraision_back.service.ClientService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    // CREATE
    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody ClientDTO clientDTO) {
        // Check if a client already exists with this email
        boolean exists = clientService.existsByEmail(clientDTO.getEmail());

        if (exists) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("An account with this email already exists.");
        }

        Client savedClient = clientService.save(clientDTO);

        // Generate 4-digit OTP
       // String otp = String.valueOf((int)(Math.random() * 9000) + 1000);

        // Save OTP to database or memory (e.g., Redis) with expiration
        //otpService.saveOtp(savedClient.getTelephone(), otp);

        // Format Moroccan phone number
        //String formattedPhoneNumber = formatMoroccanPhoneNumber(savedClient.getTelephone());

        // Send SMS
        //sendSms(formattedPhoneNumber, "Votre code OTP est : " + otp);

        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    /**
     * Format a Moroccan phone number to international format.
     * Example: 0612345678 => +212612345678
     */
    private String formatMoroccanPhoneNumber(String localPhoneNumber) {
        if (localPhoneNumber.startsWith("0")) {
            return "+212" + localPhoneNumber.substring(1);
        }
        // If already international format, return as-is
        return localPhoneNumber;
    }

    // READ - Get all clients
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
    @GetMapping("/paged")
    public ResponseEntity<Page<Client>> getClientsPaged(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Client> clientsPage = clientService.findAll(pageable);
        return new ResponseEntity<>(clientsPage, HttpStatus.OK);
    }

    // READ - Get client by ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.findById(id);
        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        Client updatedClient = clientService.update(id, clientDTO);
        if (updatedClient != null) {
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        boolean deleted = clientService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/search")
    public ResponseEntity<Page<Client>> searchClients(
        @RequestBody ClientDTO filter,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Client> result = clientService.searchClients(filter, pageable);
        return ResponseEntity.ok(result);
    }

}
