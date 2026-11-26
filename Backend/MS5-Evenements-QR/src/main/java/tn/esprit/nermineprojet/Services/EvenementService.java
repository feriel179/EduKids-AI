package tn.esprit.nermineprojet.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.nermineprojet.Entities.Evenement;
import tn.esprit.nermineprojet.Repository.IEvenementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvenementService {

    private final IEvenementRepository evenementRepository;

    public Evenement createEvenement(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    public Evenement getEvenementById(Long id) {
        return evenementRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Evenement not found with id: " + id));
    }

    public Evenement updateEvenement(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    public Evenement deleteEvenement(Long id) {
        Evenement existing = getEvenementById(id);
        evenementRepository.deleteById(id);
        return existing;
    }

    public List<Evenement> getAllEvenements() {
        return evenementRepository.findAll();
    }
}