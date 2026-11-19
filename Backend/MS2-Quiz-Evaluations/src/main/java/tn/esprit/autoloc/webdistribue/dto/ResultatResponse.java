package tn.esprit.autoloc.webdistribue.dto;

import java.time.LocalDateTime;
import java.util.List;

/** Résultat détaillé d'une tentative (note + correction question par question). */
public record ResultatResponse(
        Long tentativeId, Long quizId, String quizTitre, Long eleveId, LocalDateTime dateSoumission,
        float pointsObtenus, float pointsTotal, float noteSur20,
        int nbBonnesReponses, int nbQuestions,
        List<CorrectionQuestion> corrections
) {
    public record CorrectionQuestion(
            Long questionId, String enonce, boolean correct, float pointsObtenus, float pointsMax,
            List<String> reponseEleve, List<String> bonnesReponses
    ) {}
}
