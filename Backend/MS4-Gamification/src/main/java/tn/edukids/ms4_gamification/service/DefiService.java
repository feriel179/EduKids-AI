package tn.edukids.ms4_gamification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.edukids.ms4_gamification.dto.*;
import tn.edukids.ms4_gamification.entity.Defi;
import tn.edukids.ms4_gamification.exception.*;
import tn.edukids.ms4_gamification.mapper.GamificationMapper;
import tn.edukids.ms4_gamification.repository.DefiRepository;
import tn.edukids.ms4_gamification.repository.ParticipationRepository;
import java.util.List;

@Service @RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefiService {
    private final DefiRepository defis;
    private final ParticipationRepository participations;
    private final GamificationMapper mapper;

    public List<DefiResponse> findAll() {
        return defis.findAll().stream().map(mapper::toResponse).toList();
    }

    public DefiResponse findById(Long id) {
        return mapper.toResponse(get(id));
    }

    @Transactional
    public DefiResponse create(DefiRequest request) {
        validateDates(request);
        Defi defi = new Defi();
        mapper.update(defi, request);
        return mapper.toResponse(defis.save(defi));
    }

    @Transactional
    public DefiResponse update(Long id, DefiRequest request) {
        validateDates(request);
        Defi defi = get(id);
        mapper.update(defi, request);
        return mapper.toResponse(defi);
    }

    @Transactional
    public void delete(Long id) {
        Defi defi = get(id);
        if (participations.existsByDefiId(id))
            throw new ConflictException("Ce défi possède des participations");
        defis.delete(defi);
    }

    private void validateDates(DefiRequest request) {
        if (request.dateDebut() != null && request.dateFin() != null
                && request.dateFin().isBefore(request.dateDebut()))
            throw new InvalidRequestException("dateFin doit être postérieure ou égale à dateDebut");
    }

    private Defi get(Long id) {
        return defis.findById(id).orElseThrow(() -> new ResourceNotFoundException("Défi", id));
    }
}
