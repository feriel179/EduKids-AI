package tn.esprit.autoloc.webdistribue.dto;

import tn.esprit.autoloc.webdistribue.entities.StatutQuiz;

import java.util.List;

/** Vue enseignant : contient les bonnes réponses. */
public record QuizResponse(
        Long id, String titre, Long chapitreId, Integer dureeMin, StatutQuiz statut,
        int nbQuestions, float pointsTotal, List<QuestionResponse> questions
) {}
