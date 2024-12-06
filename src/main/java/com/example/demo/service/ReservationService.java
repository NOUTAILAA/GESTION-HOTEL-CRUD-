package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Chambre;
import com.example.demo.entity.Client;
import com.example.demo.entity.Reservation;
import com.example.demo.repository.ChambreRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ReservationRepository;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
    }

    public Reservation createReservation(Reservation reservation) {
        // Validation des entités associées (Client et Chambre)
        Client client = clientRepository.findById(reservation.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        Chambre chambre = chambreRepository.findById(reservation.getChambre().getId())
                .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));

        if (!chambre.getDisponible()) {
            throw new RuntimeException("Chambre non disponible");
        }

        chambre.setDisponible(false); // Réserver la chambre
        chambreRepository.save(chambre);

        reservation.setClient(client);
        reservation.setChambre(chambre);
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        Reservation existingReservation = getReservationById(id);

        // Validation des nouvelles valeurs
        if (updatedReservation.getClient() != null) {
            Client client = clientRepository.findById(updatedReservation.getClient().getId())
                    .orElseThrow(() -> new RuntimeException("Client non trouvé"));
            existingReservation.setClient(client);
        }

        if (updatedReservation.getChambre() != null) {
            Chambre chambre = chambreRepository.findById(updatedReservation.getChambre().getId())
                    .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));
            existingReservation.setChambre(chambre);
        }

        existingReservation.setDateDebut(updatedReservation.getDateDebut());
        existingReservation.setDateFin(updatedReservation.getDateFin());
        existingReservation.setPreferences(updatedReservation.getPreferences());

        return reservationRepository.save(existingReservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = getReservationById(id);
        Chambre chambre = reservation.getChambre();

        if (chambre != null) {
            chambre.setDisponible(true); // Libérer la chambre
            chambreRepository.save(chambre);
        }

        reservationRepository.deleteById(id);
    }
}
