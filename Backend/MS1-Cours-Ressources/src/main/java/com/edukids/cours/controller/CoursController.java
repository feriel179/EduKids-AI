
package com.edukids.cours.controller;

import com.edukids.cours.entity.Cours;
import com.edukids.cours.service.CoursService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cours")
public class CoursController {

    private final CoursService coursService;

    public CoursController(CoursService coursService) {
        this.coursService = coursService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Cours> ajouter(@RequestBody Cours cours) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(coursService.ajouterCours(cours));
    }

    // READ ALL
    @GetMapping
    public List<Cours> lister() {
        return coursService.getTousLesCours();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Cours consulter(@PathVariable UUID id) {
        return coursService.getCoursById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Cours modifier(
            @PathVariable UUID id,
            @RequestBody Cours cours) {
        return coursService.modifierCours(id, cours);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable UUID id) {
        coursService.supprimerCours(id);
        return ResponseEntity.noContent().build();
    }
}
