package tn.esprit.nermineprojet.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.nermineprojet.DTO.ParticipationDTO;
import tn.esprit.nermineprojet.Entities.Eleve;
import tn.esprit.nermineprojet.Entities.Evenement;
import tn.esprit.nermineprojet.Entities.Participation;
import tn.esprit.nermineprojet.Services.ParticipationService;

import java.util.List;

@RestController
@RequestMapping("/api/participations")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    // =====================================================
    // POST : CREATE PARTICIPATION
    // =====================================================

    @PostMapping
    public Participation createParticipation(
            @RequestBody ParticipationDTO dto) {

        Participation participation = Participation.builder()

                .dateParticipation(
                        dto.getDateParticipation()
                )

                .statut(
                        dto.getStatut()
                )

                .eleve(
                        Eleve.builder()
                                .id(dto.getEleveId())
                                .build()
                )

                .evenement(
                        Evenement.builder()
                                .id(dto.getEvenementId())
                                .build()
                )

                .build();

        return participationService
                .createParticipation(participation);
    }


    // =====================================================
    // GET : GET PARTICIPATION BY ID
    // =====================================================

    @GetMapping("/{id}")
    public Participation getParticipationById(
            @PathVariable Long id) {

        return participationService
                .getParticipationById(id);
    }


    // =====================================================
    // GET : GET ALL PARTICIPATIONS
    // =====================================================

    @GetMapping
    public List<Participation> getAllParticipations() {

        return participationService
                .getAllParticipations();
    }


    // =====================================================
    // PUT : UPDATE PARTICIPATION
    // =====================================================

    @PutMapping("/{id}")
    public Participation updateParticipation(
            @PathVariable Long id,
            @RequestBody ParticipationDTO dto) {

        Participation participation = Participation.builder()
                .dateParticipation(dto.getDateParticipation())
                .statut(dto.getStatut())
                .eleve(Eleve.builder()
                        .id(dto.getEleveId())
                        .build())
                .evenement(Evenement.builder()
                        .id(dto.getEvenementId())
                        .build())
                .build();

        return participationService.updateParticipation(id, participation);
    }


    // =====================================================
    // DELETE : DELETE PARTICIPATION
    // =====================================================

    @DeleteMapping("/{id}")
    public Participation deleteParticipation(
            @PathVariable Long id) {

        return participationService
                .deleteParticipation(id);
    }
}