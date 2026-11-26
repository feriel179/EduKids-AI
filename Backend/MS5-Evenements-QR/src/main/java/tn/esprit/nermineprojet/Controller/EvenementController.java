package tn.esprit.nermineprojet.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.nermineprojet.DTO.EvenementDTO;
import tn.esprit.nermineprojet.Entities.Evenement;
import tn.esprit.nermineprojet.Services.EvenementService;

import java.util.List;

@RestController
@RequestMapping("/api/evenements")
@RequiredArgsConstructor
public class EvenementController {

    private final EvenementService evenementService;

    @PostMapping
    public Evenement createEvenement(@RequestBody EvenementDTO dto) {

        Evenement evenement = Evenement.builder()
                .titre(dto.getTitre())
                .description(dto.getDescription())
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .type(dto.getType())
                .capacite(dto.getCapacite())
                .statut(dto.getStatut())
                .build();

        return evenementService.createEvenement(evenement);
    }

    @GetMapping("/{id}")
    public Evenement getEvenementById(@PathVariable Long id) {
        return evenementService.getEvenementById(id);
    }

    @GetMapping
    public List<Evenement> getAllEvenements() {
        return evenementService.getAllEvenements();
    }

    @PutMapping("/{id}")
    public Evenement updateEvenement(@PathVariable Long id,
                                     @RequestBody EvenementDTO dto) {
        Evenement evenement = Evenement.builder()
                .id(id)
                .titre(dto.getTitre())
                .description(dto.getDescription())
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .type(dto.getType())
                .capacite(dto.getCapacite())
                .statut(dto.getStatut())
                .build();
        return evenementService.updateEvenement(evenement);
    }

    @DeleteMapping("/{id}")
    public Evenement deleteEvenement(@PathVariable Long id) {
        return evenementService.deleteEvenement(id);
    }
}