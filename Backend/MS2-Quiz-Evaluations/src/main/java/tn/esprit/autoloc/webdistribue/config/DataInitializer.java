package tn.esprit.autoloc.webdistribue.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.webdistribue.entities.*;
import tn.esprit.autoloc.webdistribue.repositories.QuizRepository;

import java.util.List;

/**
 * Données de démonstration (plusieurs matières du primaire).
 * Exécuté uniquement si la table quiz est vide. Désactivable avec app.seed.enabled=false.
 *
 * Chapitres (références MS1) :
 *  1 Français - Grammaire     2 Maths - Additions        3 Maths - Multiplications
 *  4 Sciences - Les animaux   5 Géographie - La Tunisie  6 Anglais - Colors & numbers
 *  7 Français - Conjugaison   8 Sciences - Le corps humain
 */
@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
public class DataInitializer implements CommandLineRunner {

    public static final Long CHAPITRE_GRAMMAIRE = 1L;

    private final QuizRepository quizRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (quizRepository.count() > 0) return;

        // ⚠ Le quiz 1 doit rester le premier : l'exemple Swagger de soumission utilise ses identifiants.
        List<Quiz> quiz = List.of(
                quiz("Français — Grammaire (4ème primaire)", 1L, 15, StatutQuiz.PUBLIE,
                        q("Quel est le pluriel de « cheval » ?", TypeQuestion.QCM_UNIQUE, 5,
                                c("chevals", false), c("chevaux", true), c("chevaus", false)),
                        q("« Rapidement » est un adverbe.", TypeQuestion.VRAI_FAUX, 5,
                                c("Vrai", true), c("Faux", false)),
                        q("Quels mots sont des verbes ?", TypeQuestion.QCM_MULTIPLE, 5,
                                c("manger", true), c("table", false), c("courir", true), c("joli", false)),
                        q("Complète avec le verbe être au présent : « Nous ... en classe. »", TypeQuestion.TEXTE_LIBRE, 5,
                                c("sommes", true))),

                quiz("Maths — Additions (2ème primaire)", 2L, 10, StatutQuiz.PUBLIE,
                        q("7 + 5 = ?", TypeQuestion.QCM_UNIQUE, 4,
                                c("11", false), c("12", true), c("13", false)),
                        q("15 + 10 = 25", TypeQuestion.VRAI_FAUX, 4,
                                c("Vrai", true), c("Faux", false)),
                        q("Quelles additions font 10 ?", TypeQuestion.QCM_MULTIPLE, 4,
                                c("6 + 4", true), c("5 + 4", false), c("7 + 3", true), c("8 + 1", false)),
                        q("Écris le résultat en chiffres : 20 + 30 = ?", TypeQuestion.TEXTE_LIBRE, 4,
                                c("50", true)),
                        q("Écris en lettres le nombre 8.", TypeQuestion.TEXTE_LIBRE, 4,
                                c("huit", true))),

                quiz("Maths — Tables de multiplication (3ème primaire)", 3L, 10, StatutQuiz.PUBLIE,
                        q("3 × 4 = ?", TypeQuestion.QCM_UNIQUE, 5,
                                c("7", false), c("12", true), c("14", false), c("9", false)),
                        q("6 × 7 = 42", TypeQuestion.VRAI_FAUX, 5,
                                c("Vrai", true), c("Faux", false)),
                        q("Quels résultats sont dans la table de 5 ?", TypeQuestion.QCM_MULTIPLE, 5,
                                c("15", true), c("22", false), c("40", true), c("33", false)),
                        q("9 × 9 = ?", TypeQuestion.TEXTE_LIBRE, 5,
                                c("81", true))),

                quiz("Sciences — Les animaux (3ème primaire)", 4L, 12, StatutQuiz.PUBLIE,
                        q("Quel animal est un mammifère ?", TypeQuestion.QCM_UNIQUE, 5,
                                c("Le poisson", false), c("Le dauphin", true), c("La poule", false)),
                        q("La grenouille est un reptile.", TypeQuestion.VRAI_FAUX, 5,
                                c("Vrai", false), c("Faux", true)),
                        q("Quels animaux sont herbivores ?", TypeQuestion.QCM_MULTIPLE, 5,
                                c("La vache", true), c("Le lion", false), c("Le lapin", true), c("Le mouton", true)),
                        q("Comment appelle-t-on le petit de la vache ?", TypeQuestion.TEXTE_LIBRE, 5,
                                c("veau", true), c("le veau", true))),

                quiz("Géographie — La Tunisie (5ème primaire)", 5L, 15, StatutQuiz.PUBLIE,
                        q("Quelle est la capitale de la Tunisie ?", TypeQuestion.QCM_UNIQUE, 5,
                                c("Sfax", false), c("Tunis", true), c("Sousse", false), c("Bizerte", false)),
                        q("La Tunisie est bordée par la mer Méditerranée.", TypeQuestion.VRAI_FAUX, 5,
                                c("Vrai", true), c("Faux", false)),
                        q("Quels pays ont une frontière avec la Tunisie ?", TypeQuestion.QCM_MULTIPLE, 5,
                                c("L'Algérie", true), c("Le Maroc", false), c("La Libye", true), c("L'Égypte", false)),
                        q("Quel est le plus long oued de Tunisie ?", TypeQuestion.TEXTE_LIBRE, 5,
                                c("Medjerda", true), c("la Medjerda", true), c("oued Medjerda", true))),

                quiz("Anglais — Colors & numbers (4ème primaire)", 6L, 8, StatutQuiz.PUBLIE,
                        q("« Rouge » en anglais se dit :", TypeQuestion.QCM_UNIQUE, 5,
                                c("Blue", false), c("Red", true), c("Green", false)),
                        q("« Seven » veut dire 7.", TypeQuestion.VRAI_FAUX, 5,
                                c("Vrai", true), c("Faux", false)),
                        q("Quels mots sont des couleurs ?", TypeQuestion.QCM_MULTIPLE, 5,
                                c("Yellow", true), c("Dog", false), c("Black", true), c("Ten", false)),
                        q("Écris en anglais le nombre 3.", TypeQuestion.TEXTE_LIBRE, 5,
                                c("three", true))),

                quiz("Français — Conjugaison du présent (4ème primaire)", 7L, 12, StatutQuiz.PUBLIE,
                        q("Tu (avoir) ... un chat.", TypeQuestion.QCM_UNIQUE, 5,
                                c("as", true), c("a", false), c("avez", false)),
                        q("« Ils finissent » est conjugué au présent.", TypeQuestion.VRAI_FAUX, 5,
                                c("Vrai", true), c("Faux", false)),
                        q("Quelles formes sont correctes au présent ?", TypeQuestion.QCM_MULTIPLE, 5,
                                c("je chante", true), c("nous chantons", true), c("vous chantez", true), c("ils chantent-ent", false)),
                        q("Complète : « Vous ... (faire) vos devoirs. »", TypeQuestion.TEXTE_LIBRE, 5,
                                c("faites", true))),

                // Un quiz en BROUILLON pour tester la modification / publication
                quiz("Sciences — Le corps humain (brouillon)", 8L, 10, StatutQuiz.BROUILLON,
                        q("Quel organe pompe le sang ?", TypeQuestion.QCM_UNIQUE, 5,
                                c("Les poumons", false), c("Le cœur", true), c("L'estomac", false)),
                        q("Nous avons 5 sens.", TypeQuestion.VRAI_FAUX, 5,
                                c("Vrai", true), c("Faux", false)))
        );

        quizRepository.saveAll(quiz);
        log.info("{} quiz de démonstration créés", quiz.size());
    }

    // ------------------------------------------------------------------ helpers

    private static Quiz quiz(String titre, Long chapitreId, int dureeMin, StatutQuiz statut, Question... questions) {
        Quiz quiz = Quiz.builder().titre(titre).chapitreId(chapitreId).dureeMin(dureeMin).statut(statut).build();
        int ordre = 1;
        for (Question q : questions) {
            q.setOrdre(ordre++);
            quiz.addQuestion(q);
        }
        return quiz;
    }

    private static Question q(String enonce, TypeQuestion type, float points, Choix... choix) {
        Question q = Question.builder().enonce(enonce).type(type).points(points).build();
        for (Choix c : choix) q.addChoix(c);
        return q;
    }

    private static Choix c(String texte, boolean correct) {
        return Choix.builder().texte(texte).estCorrect(correct).build();
    }
}
