package tn.esprit.nermineprojet.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.nermineprojet.Entities.Eleve;
import tn.esprit.nermineprojet.Entities.Evenement;
import tn.esprit.nermineprojet.Entities.Participation;
import tn.esprit.nermineprojet.Repository.IEleveRepository;
import tn.esprit.nermineprojet.Repository.IEvenementRepository;
import tn.esprit.nermineprojet.Repository.IParticipationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final IParticipationRepository participationRepository;
    private final IEleveRepository eleveRepository;
    private final IEvenementRepository evenementRepository;

    // =========================
    // CREATE
    // =========================
    public Participation createParticipation(Participation participation) {

        // Vérifier que l'élève existe
        Long eleveId = participation.getEleve().getId();

        Eleve eleve = eleveRepository.findById(eleveId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Eleve introuvable avec id : " + eleveId
                        )
                );

        // Vérifier que l'événement existe
        Long evenementId = participation.getEvenement().getId();

        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Evenement introuvable avec id : " + evenementId
                        )
                );

        participation.setEleve(eleve);
        participation.setEvenement(evenement);

        return participationRepository.save(participation);
    }

    // =========================
    // GET BY ID
    // =========================
    public Participation getParticipationById(Long id) {

        return participationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Participation introuvable avec id : " + id
                        )
                );
    }

    // =========================
    // GET ALL
    // =========================
    public List<Participation> getAllParticipations() {

        return participationRepository.findAll();
    }

    // =========================
    // UPDATE
    // =========================
    public Participation updateParticipation(
            Long id,
            Participation participation) {

        // IMPORTANT :
        // On récupère la participation EXISTANTE
        Participation existing =
                participationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Participation introuvable avec id : " + id
                                )
                        );

        // Vérifier l'élève
        Long eleveId = participation.getEleve().getId();

        Eleve eleve = eleveRepository.findById(eleveId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Eleve introuvable avec id : " + eleveId
                        )
                );

        // Vérifier l'événement
        Long evenementId = participation.getEvenement().getId();

        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Evenement introuvable avec id : " + evenementId
                        )
                );

        // Modifier les valeurs de l'ancienne participation
        existing.setDateParticipation(
                participation.getDateParticipation()
        );

        existing.setStatut(
                participation.getStatut()
        );

        existing.setEleve(eleve);

        existing.setEvenement(evenement);

        // Sauvegarder la participation existante
        return participationRepository.save(existing);
    }

    // =========================
    // DELETE
    // =========================
    public Participation deleteParticipation(Long id) {

        Participation existing =
                participationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Participation introuvable avec id : " + id
                                )
                        );

        participationRepository.delete(existing);

        return existing;
    }
}