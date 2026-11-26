package tn.esprit.nermineprojet.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.nermineprojet.Entities.Eleve;
import tn.esprit.nermineprojet.Repository.IEleveRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EleveService {

    private final IEleveRepository eleveRepository;

    public Eleve createEleve(Eleve eleve) {
        return eleveRepository.save(eleve);
    }

    public Eleve getEleveById(Long id) {
        return eleveRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Eleve not found with id: " + id));
    }

    public Eleve updateEleve(Eleve eleve) {
        return eleveRepository.save(eleve);
    }

    public Eleve deleteEleve(Long id) {
        Eleve existing = getEleveById(id);
        eleveRepository.deleteById(id);
        return existing;
    }

    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }
}