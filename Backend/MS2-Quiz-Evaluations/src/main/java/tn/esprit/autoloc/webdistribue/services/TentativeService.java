package tn.esprit.autoloc.webdistribue.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.webdistribue.dto.ResultatResponse;
import tn.esprit.autoloc.webdistribue.dto.SoumissionRequest;
import tn.esprit.autoloc.webdistribue.dto.StatistiquesQuizResponse;
import tn.esprit.autoloc.webdistribue.dto.TentativeResponse;
import tn.esprit.autoloc.webdistribue.entities.*;
import tn.esprit.autoloc.webdistribue.exceptions.BusinessException;
import tn.esprit.autoloc.webdistribue.exceptions.ResourceNotFoundException;
import tn.esprit.autoloc.webdistribue.repositories.TentativeRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Passage d'un quiz, correction, notes et historique. */
@Service
@RequiredArgsConstructor
@Transactional
public class TentativeService {

    /** Note minimale (sur 20) pour considérer un quiz réussi. */
    private static final float SEUIL_REUSSITE = 10f;

    private final QuizService quizService;
    private final TentativeRepository tentativeRepository;
    private final CorrectionService correctionService;

    public ResultatResponse soumettre(Long quizId, SoumissionRequest req) {
        Quiz quiz = quizService.trouver(quizId);
        if (quiz.getStatut() != StatutQuiz.PUBLIE) {
            throw new BusinessException("On ne peut passer qu'un quiz publié (statut actuel : " + quiz.getStatut() + ").");
        }
        Map<Long, Question> questions = quiz.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));

        Tentative tentative = Tentative.builder()
                .eleveId(req.eleveId())
                .dateSoumission(LocalDateTime.now())
                .build();
        quiz.getTentatives().add(tentative);
        tentative.setQuiz(quiz);

        Set<Long> dejaRepondues = new HashSet<>();
        for (SoumissionRequest.ReponseRequest r : req.reponses()) {
            Question q = questions.get(r.questionId());
            if (q == null) {
                throw new BusinessException("La question " + r.questionId() + " n'appartient pas à ce quiz.");
            }
            if (!dejaRepondues.add(q.getId())) {
                throw new BusinessException("La question " + q.getId() + " a reçu plusieurs réponses.");
            }
            enregistrerReponse(tentative, q, r);
        }

        CorrectionService.Bilan bilan = correctionService.corriger(quiz, tentative.getReponses());
        tentative.setScore(bilan.noteSur20());
        Tentative saved = tentativeRepository.save(tentative);
        return toResultat(saved, bilan);
    }

    @Transactional(readOnly = true)
    public ResultatResponse getResultat(Long tentativeId) {
        Tentative t = tentativeRepository.findById(tentativeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tentative", tentativeId));
        return toResultat(t, correctionService.corriger(t.getQuiz(), t.getReponses()));
    }

    @Transactional(readOnly = true)
    public List<TentativeResponse> historiqueEleve(Long eleveId, Long quizId) {
        List<Tentative> liste = quizId == null
                ? tentativeRepository.findByEleveIdOrderByDateSoumissionDesc(eleveId)
                : tentativeRepository.findByEleveIdAndQuizIdOrderByDateSoumissionDesc(eleveId, quizId);
        return liste.stream().map(QuizMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<TentativeResponse> tentativesDuQuiz(Long quizId) {
        quizService.trouver(quizId);
        return tentativeRepository.findByQuizIdOrderByDateSoumissionDesc(quizId).stream()
                .map(QuizMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public StatistiquesQuizResponse statistiques(Long quizId) {
        Quiz quiz = quizService.trouver(quizId);
        List<Tentative> liste = tentativeRepository.findByQuizIdOrderByDateSoumissionDesc(quizId);
        if (liste.isEmpty()) {
            return new StatistiquesQuizResponse(quizId, quiz.getTitre(), 0, 0, null, null, null, 0);
        }
        DoubleSummaryStatistics s = liste.stream().mapToDouble(Tentative::getScore).summaryStatistics();
        long nbEleves = liste.stream().map(Tentative::getEleveId).distinct().count();
        long reussies = liste.stream().filter(t -> t.getScore() >= SEUIL_REUSSITE).count();
        return new StatistiquesQuizResponse(quizId, quiz.getTitre(), liste.size(), nbEleves,
                CorrectionService.arrondir((float) s.getAverage()), (float) s.getMax(), (float) s.getMin(),
                Math.round(reussies * 10000.0 / liste.size()) / 100.0);
    }

    public void supprimer(Long tentativeId) {
        Tentative t = tentativeRepository.findById(tentativeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tentative", tentativeId));
        t.getQuiz().getTentatives().remove(t);
    }

    // ------------------------------------------------------------------

    private void enregistrerReponse(Tentative tentative, Question q, SoumissionRequest.ReponseRequest r) {
        List<Long> choixIds = r.choixIds() == null ? List.of() : r.choixIds().stream().distinct().toList();

        if (q.getType() == TypeQuestion.TEXTE_LIBRE) {
            if (!choixIds.isEmpty()) {
                throw new BusinessException("La question \"" + q.getEnonce() + "\" attend une réponse écrite (texteLibre).");
            }
            if (r.texteLibre() != null && !r.texteLibre().isBlank()) {
                tentative.addReponse(ReponseEleve.builder()
                        .questionId(q.getId()).texteLibre(r.texteLibre().trim()).build());
            }
            return;
        }

        if ((q.getType() == TypeQuestion.QCM_UNIQUE || q.getType() == TypeQuestion.VRAI_FAUX) && choixIds.size() > 1) {
            throw new BusinessException("La question \"" + q.getEnonce() + "\" n'accepte qu'un seul choix.");
        }
        Set<Long> choixValides = q.getChoix().stream().map(Choix::getId).collect(Collectors.toSet());
        for (Long choixId : choixIds) {
            if (!choixValides.contains(choixId)) {
                throw new BusinessException("Le choix " + choixId + " n'appartient pas à la question " + q.getId() + ".");
            }
            tentative.addReponse(ReponseEleve.builder().questionId(q.getId()).choixId(choixId).build());
        }
    }

    private ResultatResponse toResultat(Tentative t, CorrectionService.Bilan b) {
        return new ResultatResponse(t.getId(), t.getQuiz().getId(), t.getQuiz().getTitre(), t.getEleveId(),
                t.getDateSoumission(), b.pointsObtenus(), b.pointsTotal(), b.noteSur20(),
                b.nbBonnesReponses(), b.corrections().size(), b.corrections());
    }
}
