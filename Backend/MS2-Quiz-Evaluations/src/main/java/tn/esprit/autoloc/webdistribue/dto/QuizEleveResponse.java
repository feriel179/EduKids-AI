package tn.esprit.autoloc.webdistribue.dto;

import tn.esprit.autoloc.webdistribue.entities.TypeQuestion;

import java.util.List;

/** Vue élève pour passer le quiz : les bonnes réponses sont masquées. */
public record QuizEleveResponse(
        Long id, String titre, Integer dureeMin, int nbQuestions, float pointsTotal,
        List<QuestionEleve> questions
) {
    public record QuestionEleve(Long id, String enonce, TypeQuestion type, Float points, Integer ordre,
                                List<ChoixEleve> choix) {}

    public record ChoixEleve(Long id, String texte) {}
}
