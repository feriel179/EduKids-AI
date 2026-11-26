package tn.esprit.autoloc.webdistribue.services;

import tn.esprit.autoloc.webdistribue.dto.*;
import tn.esprit.autoloc.webdistribue.entities.Choix;
import tn.esprit.autoloc.webdistribue.entities.Question;
import tn.esprit.autoloc.webdistribue.entities.Quiz;
import tn.esprit.autoloc.webdistribue.entities.Tentative;

import java.util.Comparator;
import java.util.List;

/** Conversion entités -> DTO. */
public final class QuizMapper {

    private QuizMapper() {}

    static final Comparator<Question> PAR_ORDRE =
            Comparator.comparing(Question::getOrdre, Comparator.nullsLast(Comparator.naturalOrder()));

    public static List<Question> questionsTriees(Quiz quiz) {
        return quiz.getQuestions().stream().sorted(PAR_ORDRE).toList();
    }

    public static float pointsTotal(Quiz quiz) {
        float total = 0f;
        for (Question q : quiz.getQuestions()) {
            total += q.getPoints() == null ? 0f : q.getPoints();
        }
        return total;
    }

    public static ChoixResponse toResponse(Choix c) {
        return new ChoixResponse(c.getId(), c.getTexte(), c.getEstCorrect());
    }

    public static QuestionResponse toResponse(Question q) {
        return new QuestionResponse(q.getId(), q.getEnonce(), q.getType(), q.getPoints(), q.getOrdre(),
                q.getChoix().stream().map(QuizMapper::toResponse).toList());
    }

    public static QuizResponse toResponse(Quiz quiz) {
        List<QuestionResponse> questions = questionsTriees(quiz).stream().map(QuizMapper::toResponse).toList();
        return new QuizResponse(quiz.getId(), quiz.getTitre(), quiz.getChapitreId(), quiz.getDureeMin(),
                quiz.getStatut(), questions.size(), pointsTotal(quiz), questions);
    }

    public static QuizEleveResponse toEleveResponse(Quiz quiz) {
        List<QuizEleveResponse.QuestionEleve> questions = questionsTriees(quiz).stream()
                .map(q -> new QuizEleveResponse.QuestionEleve(q.getId(), q.getEnonce(), q.getType(),
                        q.getPoints(), q.getOrdre(),
                        // Pour TEXTE_LIBRE on n'envoie pas les choix : ce sont les réponses attendues !
                        q.getType() == tn.esprit.autoloc.webdistribue.entities.TypeQuestion.TEXTE_LIBRE
                                ? List.of()
                                : q.getChoix().stream()
                                    .map(c -> new QuizEleveResponse.ChoixEleve(c.getId(), c.getTexte()))
                                    .toList()))
                .toList();
        return new QuizEleveResponse(quiz.getId(), quiz.getTitre(), quiz.getDureeMin(), questions.size(),
                pointsTotal(quiz), questions);
    }

    public static TentativeResponse toResponse(Tentative t) {
        return new TentativeResponse(t.getId(), t.getQuiz().getId(), t.getQuiz().getTitre(), t.getEleveId(),
                t.getScore(), t.getDateSoumission());
    }

    public static Question toEntity(QuestionRequest req, int ordreParDefaut) {
        Question q = Question.builder()
                .enonce(req.enonce())
                .type(req.type())
                .points(req.points())
                .ordre(req.ordre() != null ? req.ordre() : ordreParDefaut)
                .build();
        if (req.choix() != null) {
            req.choix().forEach(c -> q.addChoix(toEntity(c)));
        }
        return q;
    }

    public static Choix toEntity(ChoixRequest req) {
        return Choix.builder().texte(req.texte()).estCorrect(req.estCorrect()).build();
    }
}
