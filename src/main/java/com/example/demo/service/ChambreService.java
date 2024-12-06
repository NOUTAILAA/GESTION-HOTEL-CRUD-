package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Chambre;
import com.example.demo.repository.ChambreRepository;

@Service
public class ChambreService {
    @Autowired
    private ChambreRepository chambreRepository;

    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }

    public Chambre getChambreById(Long id) {
        return chambreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));
    }

    public Chambre createChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    public Chambre updateChambre(Long id, Chambre updatedChambre) {
        Chambre existingChambre = getChambreById(id);
        existingChambre.setType(updatedChambre.getType());
        existingChambre.setPrix(updatedChambre.getPrix());
        existingChambre.setDisponible(updatedChambre.getDisponible());
        return chambreRepository.save(existingChambre);
    }

    public void deleteChambre(Long id) {
        chambreRepository.deleteById(id);
    }
}
