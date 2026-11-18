
package com.edukids.cours.service;

import com.edukids.cours.entity.Cours;
import com.edukids.cours.repository.CoursRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@Service
public class CoursService {

    private final CoursRepository coursRepository;

    public CoursService(CoursRepository coursRepository) {
        this.coursRepository = coursRepository;
    }

    // CREATE
    public Cours ajouterCours(Cours cours) {
        return coursRepository.save(cours);
    }

    // READ ALL
    public List<Cours> getTousLesCours() {
        return coursRepository.findAll();
    }

    // READ BY ID
    public Cours getCoursById(UUID id) {
        return coursRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cours introuvable"));
    }

    // UPDATE
    public Cours modifierCours(UUID id, Cours nouveauCours) {
        Cours cours = getCoursById(id);

        cours.setTitre(nouveauCours.getTitre());
        cours.setDescription(nouveauCours.getDescription());
        cours.setStatut(nouveauCours.getStatut());
        cours.setEnseignantId(nouveauCours.getEnseignantId());

        if (nouveauCours.getNiveau() != null) {
            cours.setNiveau(nouveauCours.getNiveau());
        }

        if (nouveauCours.getMatiere() != null) {
            cours.setMatiere(nouveauCours.getMatiere());
        }

        return coursRepository.save(cours);
    }

    // DELETE
    public void supprimerCours(UUID id) {
        Cours cours = getCoursById(id);
        coursRepository.delete(cours);
    }
}
