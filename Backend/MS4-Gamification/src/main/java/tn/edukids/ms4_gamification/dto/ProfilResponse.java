package tn.edukids.ms4_gamification.dto;

import tn.edukids.ms4_gamification.entity.Niveau;
import java.time.LocalDate;
import java.util.Set;

public record ProfilResponse(Long id, Long eleveId, int totalPoints, Niveau niveau,
                             LocalDate dateCreation, Set<Long> badgeIds) {}
