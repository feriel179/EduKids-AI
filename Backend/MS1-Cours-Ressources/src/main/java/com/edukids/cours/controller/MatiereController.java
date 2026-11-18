
package com.edukids.cours.controller;

import com.edukids.cours.entity.Matiere;
import com.edukids.cours.repository.MatiereRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/matieres")
public class MatiereController {

    private final MatiereRepository repository;

    public MatiereController(MatiereRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Matiere> lister() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Matiere ajouter(@RequestBody Matiere matiere) {
        return repository.save(matiere);
    }
}
