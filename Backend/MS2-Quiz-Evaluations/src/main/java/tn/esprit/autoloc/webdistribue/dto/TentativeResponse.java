package tn.esprit.autoloc.webdistribue.dto;

import java.time.LocalDateTime;

/** Ligne d'historique. score = note sur 20. */
public record TentativeResponse(
        Long id, Long quizId, String quizTitre, Long eleveId, Float score, LocalDateTime dateSoumission
) {}
