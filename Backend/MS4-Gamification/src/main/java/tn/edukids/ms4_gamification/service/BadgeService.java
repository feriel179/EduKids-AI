package tn.edukids.ms4_gamification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.edukids.ms4_gamification.dto.*;
import tn.edukids.ms4_gamification.entity.Badge;
import tn.edukids.ms4_gamification.exception.ResourceNotFoundException;
import tn.edukids.ms4_gamification.mapper.GamificationMapper;
import tn.edukids.ms4_gamification.repository.BadgeRepository;
import tn.edukids.ms4_gamification.repository.ProfilRepository;
import java.util.List;

@Service @RequiredArgsConstructor
@Transactional(readOnly = true)
public class BadgeService {
    private final BadgeRepository badges;
    private final ProfilRepository profils;
    private final GamificationMapper mapper;

    public List<BadgeResponse> findAll() {
        return badges.findAll().stream().map(mapper::toResponse).toList();
    }

    public BadgeResponse findById(Long id) {
        return mapper.toResponse(get(id));
    }

    @Transactional
    public BadgeResponse create(BadgeRequest request) {
        Badge badge = new Badge();
        mapper.update(badge, request);
        return mapper.toResponse(badges.save(badge));
    }

    @Transactional
    public BadgeResponse update(Long id, BadgeRequest request) {
        Badge badge = get(id);
        mapper.update(badge, request);
        return mapper.toResponse(badge);
    }

    @Transactional
    public void delete(Long id) {
        Badge badge = get(id);
        profils.findDistinctByBadges_Id(id).forEach(profil -> profil.getBadges().remove(badge));
        badges.delete(badge);
    }

    private Badge get(Long id) {
        return badges.findById(id).orElseThrow(() -> new ResourceNotFoundException("Badge", id));
    }
}
