package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Utilisateur;
import com.example.demo.repository.UtilisateurRepository;

@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur updateUtilisateur(Long id, Utilisateur updatedUtilisateur) {
        Utilisateur existingUtilisateur = getUtilisateurById(id);
        existingUtilisateur.setNomUtilisateur(updatedUtilisateur.getNomUtilisateur());
        existingUtilisateur.setMotDePasse(updatedUtilisateur.getMotDePasse());
        existingUtilisateur.setRole(updatedUtilisateur.getRole());
        return utilisateurRepository.save(existingUtilisateur);
    }

    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }
}
