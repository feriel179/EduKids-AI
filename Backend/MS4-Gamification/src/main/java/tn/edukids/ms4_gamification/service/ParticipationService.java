package tn.edukids.ms4_gamification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.edukids.ms4_gamification.dto.*;
import tn.edukids.ms4_gamification.entity.ParticipationDefi;
import tn.edukids.ms4_gamification.exception.ResourceNotFoundException;
import tn.edukids.ms4_gamification.mapper.GamificationMapper;
import tn.edukids.ms4_gamification.repository.*;
import java.util.List;

@Service @RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipationService {
    private final ParticipationRepository participations;
    private final ProfilRepository profils;
    private final DefiRepository defis;
    private final GamificationMapper mapper;

    public List<ParticipationResponse> findAll() {
        return participations.findAll().stream().map(mapper::toResponse).toList();
    }

    public ParticipationResponse findById(Long id) {
        return mapper.toResponse(get(id));
    }

    @Transactional
    public ParticipationResponse create(ParticipationRequest request) {
        ParticipationDefi participation = new ParticipationDefi();
        apply(participation, request);
        return mapper.toResponse(participations.save(participation));
    }

    @Transactional
    public ParticipationResponse update(Long id, ParticipationRequest request) {
        ParticipationDefi participation = get(id);
        apply(participation, request);
        return mapper.toResponse(participation);
    }

    @Transactional
    public void delete(Long id) {
        participations.delete(get(id));
    }

    private void apply(ParticipationDefi participation, ParticipationRequest request) {
        participation.setProfil(profils.findById(request.profilId())
                .orElseThrow(() -> new ResourceNotFoundException("Profil", request.profilId())));
        participation.setDefi(defis.findById(request.defiId())
                .orElseThrow(() -> new ResourceNotFoundException("Défi", request.defiId())));
        participation.setScore(request.score());
    }

    private ParticipationDefi get(Long id) {
        return participations.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Participation", id));
    }
}
