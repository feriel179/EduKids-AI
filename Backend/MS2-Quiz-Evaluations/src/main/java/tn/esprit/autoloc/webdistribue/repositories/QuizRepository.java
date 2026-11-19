package tn.esprit.autoloc.webdistribue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.webdistribue.entities.Quiz;
import tn.esprit.autoloc.webdistribue.entities.StatutQuiz;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByChapitreId(Long chapitreId);
    List<Quiz> findByStatut(StatutQuiz statut);
    List<Quiz> findByChapitreIdAndStatut(Long chapitreId, StatutQuiz statut);
}
