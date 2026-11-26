package tn.esprit.autoloc.webdistribue.services;

import tn.esprit.autoloc.webdistribue.entities.Choix;
import tn.esprit.autoloc.webdistribue.entities.Question;
import tn.esprit.autoloc.webdistribue.entities.Quiz;
import tn.esprit.autoloc.webdistribue.entities.StatutQuiz;
import tn.esprit.autoloc.webdistribue.exceptions.BusinessException;

/** Règles de cohérence d'une question selon son type. */
public final class QuestionValidator {

    private QuestionValidator() {}

    public static void verifier(Question q) {
        long nbChoix = q.getChoix().size();
        long nbCorrects = q.getChoix().stream().filter(c -> Boolean.TRUE.equals(c.getEstCorrect())).count();
        String nom = "Question \"" + q.getEnonce() + "\" : ";

        switch (q.getType()) {
            case QCM_UNIQUE -> {
                if (nbChoix < 2) throw new BusinessException(nom + "un QCM doit avoir au moins 2 choix.");
                if (nbCorrects != 1) throw new BusinessException(nom + "un QCM_UNIQUE doit avoir exactement 1 bonne réponse.");
            }
            case QCM_MULTIPLE -> {
                if (nbChoix < 2) throw new BusinessException(nom + "un QCM doit avoir au moins 2 choix.");
                if (nbCorrects < 1) throw new BusinessException(nom + "un QCM_MULTIPLE doit avoir au moins 1 bonne réponse.");
            }
            case VRAI_FAUX -> {
                if (nbChoix != 2) throw new BusinessException(nom + "une question VRAI_FAUX doit avoir exactement 2 choix.");
                if (nbCorrects != 1) throw new BusinessException(nom + "une question VRAI_FAUX doit avoir exactement 1 bonne réponse.");
            }
            case TEXTE_LIBRE -> {
                if (nbCorrects < 1) throw new BusinessException(nom + "indiquez au moins une réponse acceptée (choix estCorrect = true).");
            }
        }
        for (Choix c : q.getChoix()) {
            if (c.getTexte() == null || c.getTexte().isBlank()) {
                throw new BusinessException(nom + "un choix ne peut pas être vide.");
            }
        }
    }

    public static void verifierPublication(Quiz quiz) {
        if (quiz.getQuestions().isEmpty()) {
            throw new BusinessException("Impossible de publier un quiz sans question.");
        }
        quiz.getQuestions().forEach(QuestionValidator::verifier);
    }

    /** Un quiz (brouillon, publié ou archivé) est modifiable tant qu'aucun élève ne l'a passé. */
    public static void verifierModifiable(Quiz quiz) {
        int nb = quiz.getTentatives().size();
        if (nb > 0) {
            throw new BusinessException("Ce quiz a déjà été passé " + nb + " fois : il ne peut plus être modifié "
                    + "(les notes déjà données seraient faussées). Archivez-le et créez-en un nouveau.");
        }
    }

    /** Après une modification : un quiz publié doit rester cohérent (sinon la transaction est annulée). */
    public static void revaliderSiPublie(Quiz quiz) {
        if (quiz.getStatut() == StatutQuiz.PUBLIE) {
            verifierPublication(quiz);
        }
    }
}
