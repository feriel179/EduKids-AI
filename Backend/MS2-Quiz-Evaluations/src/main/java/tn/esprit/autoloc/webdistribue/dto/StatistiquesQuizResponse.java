package tn.esprit.autoloc.webdistribue.dto;


/** Suivi des résultats d'un quiz (côté enseignant). Notes sur 20. */
public record StatistiquesQuizResponse(
        Long quizId, String quizTitre, long nbTentatives, long nbEleves,
        Float moyenne, Float meilleureNote, Float plusBasseNote, double tauxReussite
) {}
