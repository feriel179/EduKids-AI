package tn.edukids.ms4_gamification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.edukids.ms4_gamification.dto.*;
import tn.edukids.ms4_gamification.entity.Badge;
import tn.edukids.ms4_gamification.entity.ProfilGamification;
import tn.edukids.ms4_gamification.exception.*;
import tn.edukids.ms4_gamification.mapper.GamificationMapper;
import tn.edukids.ms4_gamification.repository.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service @RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfilService {
    private final ProfilRepository profils;
    private final BadgeRepository badges;
    private final ParticipationRepository participations;
    private final GamificationMapper mapper;

    public List<ProfilResponse> findAll() {
        return profils.findAll().stream().map(mapper::toResponse).toList();
    }

    public ProfilResponse findById(Long id) {
        return mapper.toResponse(get(id));
    }

    @Transactional
    public ProfilResponse create(ProfilRequest request) {
        if (profils.existsByEleveId(request.eleveId()))
            throw new ConflictException("Cet élève possède déjà un profil");
        ProfilGamification profil = new ProfilGamification();
        profil.setEleveId(request.eleveId());
        profil.setBadges(resolveBadges(request.badgeIds()));
        return mapper.toResponse(profils.save(profil));
    }

    @Transactional
    public ProfilResponse update(Long id, ProfilRequest request) {
        ProfilGamification profil = get(id);
        if (profils.existsByEleveIdAndIdNot(request.eleveId(), id))
            throw new ConflictException("Cet élève possède déjà un profil");
        profil.setEleveId(request.eleveId());
        profil.setBadges(resolveBadges(request.badgeIds()));
        return mapper.toResponse(profil);
    }

    @Transactional
    public void delete(Long id) {
        ProfilGamification profil = get(id);
        if (participations.existsByProfilId(id))
            throw new ConflictException("Ce profil possède des participations");
        profil.getBadges().clear();
        profils.delete(profil);
    }

    private Set<Badge> resolveBadges(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) return new HashSet<>();
        List<Badge> found = badges.findAllById(ids);
        if (found.size() != ids.size())
            throw new InvalidRequestException("Au moins un badge est introuvable");
        return new HashSet<>(found);
    }

    private ProfilGamification get(Long id) {
        return profils.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profil", id));
    }
}
