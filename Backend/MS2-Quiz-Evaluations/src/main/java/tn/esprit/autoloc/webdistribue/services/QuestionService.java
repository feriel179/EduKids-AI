package tn.esprit.autoloc.webdistribue.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.webdistribue.dto.ChoixRequest;
import tn.esprit.autoloc.webdistribue.dto.ChoixResponse;
import tn.esprit.autoloc.webdistribue.dto.QuestionRequest;
import tn.esprit.autoloc.webdistribue.dto.QuestionResponse;
import tn.esprit.autoloc.webdistribue.entities.Choix;
import tn.esprit.autoloc.webdistribue.entities.Question;
import tn.esprit.autoloc.webdistribue.entities.Quiz;
import tn.esprit.autoloc.webdistribue.exceptions.ResourceNotFoundException;
import tn.esprit.autoloc.webdistribue.repositories.ChoixRepository;
import tn.esprit.autoloc.webdistribue.repositories.QuestionRepository;

import java.util.List;

/** Gestion des questions et de leurs choix (quiz modifiable tant qu'aucun élève ne l'a passé). */
@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuizService quizService;
    private final QuestionRepository questionRepository;
    private final ChoixRepository choixRepository;

    @Transactional(readOnly = true)
    public List<QuestionResponse> listerParQuiz(Long quizId) {
        return QuizMapper.questionsTriees(quizService.trouver(quizId)).stream()
                .map(QuizMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public QuestionResponse getById(Long id) {
        return QuizMapper.toResponse(trouver(id));
    }

    public QuestionResponse ajouter(Long quizId, QuestionRequest req) {
        Quiz quiz = quizService.trouver(quizId);
        QuestionValidator.verifierModifiable(quiz);
        Question q = QuizMapper.toEntity(req, quiz.getQuestions().size() + 1);
        if (!q.getChoix().isEmpty()) QuestionValidator.verifier(q);
        quiz.addQuestion(q);
        QuestionValidator.revaliderSiPublie(quiz);
        return QuizMapper.toResponse(questionRepository.save(q));
    }

    public QuestionResponse modifier(Long id, QuestionRequest req) {
        Question q = trouver(id);
        QuestionValidator.verifierModifiable(q.getQuiz());
        q.setEnonce(req.enonce());
        q.setType(req.type());
        q.setPoints(req.points());
        if (req.ordre() != null) q.setOrdre(req.ordre());
        if (req.choix() != null) {               // null = on garde les choix existants
            q.getChoix().clear();
            req.choix().forEach(c -> q.addChoix(QuizMapper.toEntity(c)));
        }
        if (!q.getChoix().isEmpty()) QuestionValidator.verifier(q);
        QuestionValidator.revaliderSiPublie(q.getQuiz());
        return QuizMapper.toResponse(q);
    }

    public void supprimer(Long id) {
        Question q = trouver(id);
        QuestionValidator.verifierModifiable(q.getQuiz());
        Quiz quiz = q.getQuiz();
        quiz.getQuestions().remove(q);   // orphanRemoval
        QuestionValidator.revaliderSiPublie(quiz);
    }

    // ---------------- Choix ----------------

    public ChoixResponse ajouterChoix(Long questionId, ChoixRequest req) {
        Question q = trouver(questionId);
        QuestionValidator.verifierModifiable(q.getQuiz());
        Choix c = QuizMapper.toEntity(req);
        q.addChoix(c);
        QuestionValidator.revaliderSiPublie(q.getQuiz());
        return QuizMapper.toResponse(choixRepository.save(c));
    }

    public ChoixResponse modifierChoix(Long choixId, ChoixRequest req) {
        Choix c = trouverChoix(choixId);
        QuestionValidator.verifierModifiable(c.getQuestion().getQuiz());
        c.setTexte(req.texte());
        c.setEstCorrect(req.estCorrect());
        QuestionValidator.revaliderSiPublie(c.getQuestion().getQuiz());
        return QuizMapper.toResponse(c);
    }

    public void supprimerChoix(Long choixId) {
        Choix c = trouverChoix(choixId);
        QuestionValidator.verifierModifiable(c.getQuestion().getQuiz());
        Question q = c.getQuestion();
        q.getChoix().remove(c);
        QuestionValidator.revaliderSiPublie(q.getQuiz());
    }

    private Question trouver(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question", id));
    }

    private Choix trouverChoix(Long id) {
        return choixRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Choix", id));
    }
}
