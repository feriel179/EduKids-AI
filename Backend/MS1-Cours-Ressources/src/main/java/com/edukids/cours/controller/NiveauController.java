
package com.edukids.cours.controller;

import com.edukids.cours.entity.Niveau;
import com.edukids.cours.repository.NiveauRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/niveaux")
public class NiveauController {

    private final NiveauRepository repository;

    public NiveauController(NiveauRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Niveau> lister() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Niveau ajouter(@RequestBody Niveau niveau) {
        return repository.save(niveau);
    }
}
