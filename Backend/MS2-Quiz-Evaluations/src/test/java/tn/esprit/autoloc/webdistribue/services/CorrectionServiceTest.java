package tn.esprit.autoloc.webdistribue.services;

import org.junit.jupiter.api.Test;
import tn.esprit.autoloc.webdistribue.entities.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Test unitaire pur (sans base de données) de la correction automatique. */
class CorrectionServiceTest {

    private final CorrectionService service = new CorrectionService();
    private static long seq = 1;

    private static Choix choix(String texte, boolean ok) {
        return Choix.builder().id(seq++).texte(texte).estCorrect(ok).build();
    }

    private static Question question(TypeQuestion type, float pts, int ordre, Choix... choix) {
        Question q = Question.builder().id(seq++).enonce("Q" + ordre).type(type).points(pts).ordre(ordre).build();
        for (Choix c : choix) q.addChoix(c);
        return q;
    }

    private static ReponseEleve rep(Question q, Long choixId, String texte) {
        return ReponseEleve.builder().questionId(q.getId()).choixId(choixId).texteLibre(texte).build();
    }

    @Test
    void calculeLaNoteSur20() {
        Choix chevals = choix("chevals", false), chevaux = choix("chevaux", true);
        Question q1 = question(TypeQuestion.QCM_UNIQUE, 5, 1, chevals, chevaux);

        Choix manger = choix("manger", true), table = choix("table", false), courir = choix("courir", true);
        Question q2 = question(TypeQuestion.QCM_MULTIPLE, 5, 2, manger, table, courir);

        Question q3 = question(TypeQuestion.TEXTE_LIBRE, 5, 3, choix("sommes", true));

        Choix vrai = choix("Vrai", true), faux = choix("Faux", false);
        Question q4 = question(TypeQuestion.VRAI_FAUX, 5, 4, vrai, faux);

        Quiz quiz = Quiz.builder().titre("Grammaire").build();
        List.of(q1, q2, q3, q4).forEach(quiz::addQuestion);

        List<ReponseEleve> reponses = List.of(
                rep(q1, chevaux.getId(), null),                 // juste
                rep(q2, manger.getId(), null),                  // incomplet -> faux
                rep(q3, null, "  Sommes. "),                    // juste (normalisé)
                rep(q4, faux.getId(), null));                   // faux

        CorrectionService.Bilan b = service.corriger(quiz, reponses);

        assertEquals(10f, b.pointsObtenus());
        assertEquals(20f, b.pointsTotal());
        assertEquals(10f, b.noteSur20());
        assertEquals(2, b.nbBonnesReponses());
        assertFalse(b.corrections().get(1).correct());
    }

    @Test
    void qcmMultipleToutesLesBonnesReponses() {
        Choix a = choix("a", true), b = choix("b", true), c = choix("c", false);
        Question q = question(TypeQuestion.QCM_MULTIPLE, 2, 1, a, b, c);
        assertTrue(service.estCorrecte(q, List.of(rep(q, a.getId(), null), rep(q, b.getId(), null))));
        assertFalse(service.estCorrecte(q, List.of(rep(q, a.getId(), null), rep(q, b.getId(), null), rep(q, c.getId(), null))));
    }

    @Test
    void questionSansReponseVautZero() {
        Question q = question(TypeQuestion.QCM_UNIQUE, 4, 1, choix("x", true), choix("y", false));
        Quiz quiz = Quiz.builder().titre("t").build();
        quiz.addQuestion(q);
        assertEquals(0f, service.corriger(quiz, List.of()).noteSur20());
    }
}
