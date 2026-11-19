package tn.esprit.autoloc.webdistribue.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.webdistribue.dto.QuestionRequest;
import tn.esprit.autoloc.webdistribue.dto.QuizEleveResponse;
import tn.esprit.autoloc.webdistribue.dto.QuizRequest;
import tn.esprit.autoloc.webdistribue.dto.QuizResponse;
import tn.esprit.autoloc.webdistribue.entities.Question;
import tn.esprit.autoloc.webdistribue.entities.Quiz;
import tn.esprit.autoloc.webdistribue.entities.StatutQuiz;
import tn.esprit.autoloc.webdistribue.exceptions.BusinessException;
import tn.esprit.autoloc.webdistribue.exceptions.ResourceNotFoundException;
import tn.esprit.autoloc.webdistribue.repositories.QuizRepository;
import tn.esprit.autoloc.webdistribue.repositories.TentativeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;
    private final TentativeRepository tentativeRepository;

    public QuizResponse creer(QuizRequest req) {
        Quiz quiz = Quiz.builder()
                .titre(req.titre())
                .chapitreId(req.chapitreId())
                .dureeMin(req.dureeMin())
                .statut(StatutQuiz.BROUILLON)
                .build();
        ajouterQuestions(quiz, req.questions());
        return QuizMapper.toResponse(quizRepository.save(quiz));
    }

    @Transactional(readOnly = true)
    public List<QuizResponse> lister(Long chapitreId, StatutQuiz statut) {
        List<Quiz> quiz;
        if (chapitreId != null && statut != null) quiz = quizRepository.findByChapitreIdAndStatut(chapitreId, statut);
        else if (chapitreId != null) quiz = quizRepository.findByChapitreId(chapitreId);
        else if (statut != null) quiz = quizRepository.findByStatut(statut);
        else quiz = quizRepository.findAll();
        return quiz.stream().map(QuizMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public QuizResponse getById(Long id) {
        return QuizMapper.toResponse(trouver(id));
    }

    /** Version "élève" : uniquement un quiz publié, sans les bonnes réponses. */
    @Transactional(readOnly = true)
    public QuizEleveResponse getPourEleve(Long id) {
        Quiz quiz = trouver(id);
        if (quiz.getStatut() != StatutQuiz.PUBLIE) {
            throw new BusinessException("Ce quiz n'est pas disponible (statut : " + quiz.getStatut() + ").");
        }
        return QuizMapper.toEleveResponse(quiz);
    }

    public QuizResponse modifier(Long id, QuizRequest req) {
        Quiz quiz = trouver(id);
        QuestionValidator.verifierModifiable(quiz);
        quiz.setTitre(req.titre());
        quiz.setChapitreId(req.chapitreId());
        quiz.setDureeMin(req.dureeMin());
        if (req.questions() != null) {          // null = on ne touche pas aux questions
            quiz.getQuestions().clear();
            ajouterQuestions(quiz, req.questions());
        }
        QuestionValidator.revaliderSiPublie(quiz);
        return QuizMapper.toResponse(quiz);
    }

    public QuizResponse changerStatut(Long id, StatutQuiz nouveau) {
        Quiz quiz = trouver(id);
        StatutQuiz actuel = quiz.getStatut();
        if (actuel == nouveau) return QuizMapper.toResponse(quiz);

        switch (nouveau) {
            case PUBLIE -> QuestionValidator.verifierPublication(quiz);
            case BROUILLON -> {
                if (tentativeRepository.existsByQuizId(id)) {
                    throw new BusinessException("Ce quiz a déjà été passé par des élèves : "
                            + "il ne peut plus repasser en BROUILLON (archivez-le puis créez-en un nouveau).");
                }
            }
            case ARCHIVE -> { /* toujours autorisé */ }
        }
        quiz.setStatut(nouveau);
        return QuizMapper.toResponse(quiz);
    }

    public void supprimer(Long id) {
        quizRepository.delete(trouver(id));
    }

    Quiz trouver(Long id) {
        return quizRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quiz", id));
    }

    private void ajouterQuestions(Quiz quiz, List<QuestionRequest> questions) {
        if (questions == null) return;
        int ordre = quiz.getQuestions().size() + 1;
        for (QuestionRequest qr : questions) {
            Question q = QuizMapper.toEntity(qr, ordre++);
            QuestionValidator.verifier(q);
            quiz.addQuestion(q);
        }
    }
}
