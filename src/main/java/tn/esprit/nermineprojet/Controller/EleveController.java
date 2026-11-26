package tn.esprit.nermineprojet.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.nermineprojet.DTO.EleveDTO;
import tn.esprit.nermineprojet.Entities.Eleve;
import tn.esprit.nermineprojet.Services.EleveService;

import java.util.List;

@RestController
@RequestMapping("/api/eleves")
@RequiredArgsConstructor
public class EleveController {

    private final EleveService eleveService;

    @PostMapping
    public Eleve createEleve(@RequestBody EleveDTO dto) {

        Eleve eleve = Eleve.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .niveau(dto.getNiveau())
                .build();

        return eleveService.createEleve(eleve);
    }

    @GetMapping("/{id}")
    public Eleve getEleveById(@PathVariable Long id) {
        return eleveService.getEleveById(id);
    }

    @GetMapping
    public List<Eleve> getAllEleves() {
        return eleveService.getAllEleves();
    }
    @PutMapping("/{id}")
    public Eleve updateEleve(@PathVariable Long id,
                             @RequestBody EleveDTO dto) {
        Eleve eleve = Eleve.builder()
                .id(id)
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .niveau(dto.getNiveau())
                .build();
        return eleveService.updateEleve(eleve);
    }

    @DeleteMapping("/{id}")
    public Eleve deleteEleve(@PathVariable Long id) {
        return eleveService.deleteEleve(id);
    }
}