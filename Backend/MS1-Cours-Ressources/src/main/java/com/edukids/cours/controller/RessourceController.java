
package com.edukids.cours.controller;

import com.edukids.cours.entity.Ressource;
import com.edukids.cours.entity.Cours;
import com.edukids.cours.repository.RessourceRepository;
import com.edukids.cours.repository.CoursRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ressources")
public class RessourceController {

    private final RessourceRepository ressourceRepository;
    private final CoursRepository coursRepository;

    public RessourceController(
            RessourceRepository ressourceRepository,
            CoursRepository coursRepository) {

        this.ressourceRepository = ressourceRepository;
        this.coursRepository = coursRepository;
    }

    // 1. GET - Afficher toutes les ressources
    @GetMapping
    public List<Ressource> lister() {
        return ressourceRepository.findAll();
    }

    // 2. POST - Ajouter une ressource
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ressource ajouter(@RequestBody Ressource ressource) {

        if (ressource.getCours() == null ||
                ressource.getCours().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "cours.id obligatoire");
        }

        UUID coursId = ressource.getCours().getId();

        Cours cours = coursRepository.findById(coursId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cours introuvable"));

        ressource.setCours(cours);

        return ressourceRepository.save(ressource);
    }

    // 3. GET - Afficher une ressource par ID
    @GetMapping("/{id}")
    public Ressource trouver(@PathVariable UUID id) {

        return ressourceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ressource introuvable"));
    }

    // 4. PUT - Modifier une ressource
    @PutMapping("/{id}")
    public Ressource modifier(
            @PathVariable UUID id,
            @RequestBody Ressource nouvelleRessource) {

        Ressource ressource = trouver(id);

        ressource.setTitre(nouvelleRessource.getTitre());
        ressource.setType(nouvelleRessource.getType());
        ressource.setUrl(nouvelleRessource.getUrl());

        return ressourceRepository.save(ressource);
    }

    // 5. DELETE - Supprimer une ressource
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable UUID id) {

        Ressource ressource = trouver(id);
        ressourceRepository.delete(ressource);
    }
}
