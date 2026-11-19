package tn.esprit.autoloc.webdistribue.services;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.webdistribue.dto.ResultatResponse.CorrectionQuestion;
import tn.esprit.autoloc.webdistribue.entities.Choix;
import tn.esprit.autoloc.webdistribue.entities.Question;
import tn.esprit.autoloc.webdistribue.entities.Quiz;
import tn.esprit.autoloc.webdistribue.entities.ReponseEleve;

import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Correction automatique : tout ou rien par question.
 * - QCM_UNIQUE / VRAI_FAUX : le seul choix coché doit être le bon.
 * - QCM_MULTIPLE : l'ensemble des choix cochés doit être exactement l'ensemble des bons choix.
 * - TEXTE_LIBRE : la réponse écrite doit correspondre (sans tenir compte des majuscules,
 *   des espaces en trop et de la ponctuation finale) à l'une des réponses acceptées.
 */
@Service
public class CorrectionService {

    public record Bilan(float pointsObtenus, float pointsTotal, float noteSur20, int nbBonnesReponses,
                        List<CorrectionQuestion> corrections) {}

    public Bilan corriger(Quiz quiz, List<ReponseEleve> reponses) {
        Map<Long, List<ReponseEleve>> parQuestion = reponses.stream()
                .collect(Collectors.groupingBy(ReponseEleve::getQuestionId));

        float obtenus = 0f;
        float total = 0f;
        int bonnes = 0;
        List<CorrectionQuestion> corrections = new ArrayList<>();

        for (Question q : QuizMapper.questionsTriees(quiz)) {
            float max = q.getPoints() == null ? 0f : q.getPoints();
            total += max;
            List<ReponseEleve> rq = parQuestion.getOrDefault(q.getId(), List.of());
            boolean correct = estCorrecte(q, rq);
            float pts = correct ? max : 0f;
            obtenus += pts;
            if (correct) bonnes++;
            corrections.add(new CorrectionQuestion(q.getId(), q.getEnonce(), correct, pts, max,
                    reponseAffichee(q, rq), bonnesReponses(q)));
        }
        float note = total > 0 ? arrondir(obtenus / total * 20f) : 0f;
        return new Bilan(arrondir(obtenus), arrondir(total), note, bonnes, corrections);
    }

    boolean estCorrecte(Question q, List<ReponseEleve> rq) {
        if (rq.isEmpty()) return false;
        Set<Long> coches = rq.stream().map(ReponseEleve::getChoixId).filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Set<Long> bons = q.getChoix().stream().filter(c -> Boolean.TRUE.equals(c.getEstCorrect()))
                .map(Choix::getId).collect(Collectors.toSet());

        return switch (q.getType()) {
            case QCM_UNIQUE, VRAI_FAUX -> coches.size() == 1 && bons.containsAll(coches);
            case QCM_MULTIPLE -> !coches.isEmpty() && coches.equals(bons);
            case TEXTE_LIBRE -> {
                String ecrit = rq.stream().map(ReponseEleve::getTexteLibre).filter(Objects::nonNull)
                        .findFirst().orElse(null);
                if (ecrit == null || ecrit.isBlank()) yield false;
                String n = normaliser(ecrit);
                yield q.getChoix().stream()
                        .filter(c -> Boolean.TRUE.equals(c.getEstCorrect()))
                        .anyMatch(c -> normaliser(c.getTexte()).equals(n));
            }
        };
    }

    private List<String> reponseAffichee(Question q, List<ReponseEleve> rq) {
        if (q.getType() == tn.esprit.autoloc.webdistribue.entities.TypeQuestion.TEXTE_LIBRE) {
            return rq.stream().map(ReponseEleve::getTexteLibre).filter(Objects::nonNull).toList();
        }
        Map<Long, String> textes = q.getChoix().stream().collect(Collectors.toMap(Choix::getId, Choix::getTexte));
        return rq.stream().map(ReponseEleve::getChoixId).filter(Objects::nonNull)
                .map(id -> textes.getOrDefault(id, "?")).toList();
    }

    private List<String> bonnesReponses(Question q) {
        return q.getChoix().stream().filter(c -> Boolean.TRUE.equals(c.getEstCorrect()))
                .map(Choix::getTexte).toList();
    }

    static String normaliser(String s) {
        String t = Normalizer.normalize(s, Normalizer.Form.NFC)
                .trim()
                .toLowerCase(Locale.FRENCH)
                .replaceAll("\\s+", " ")
                .replaceAll("[\\s.!?;,]+$", "");
        return t;
    }

    static float arrondir(float x) {
        return Math.round(x * 100f) / 100f;
    }
}
