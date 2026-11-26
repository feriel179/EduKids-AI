package tn.edukids.ms4_gamification.dto;

import tn.edukids.ms4_gamification.entity.StatutParticipation;
import java.time.LocalDate;

public record ParticipationResponse(Long id, Long profilId, Long defiId,
                                    StatutParticipation statut, int score,
                                    int pointsGagnes, LocalDate dateParticipation) {}
