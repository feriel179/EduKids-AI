package tn.esprit.autoloc.webdistribue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.webdistribue.entities.Tentative;

import java.util.List;

public interface TentativeRepository extends JpaRepository<Tentative, Long> {
    List<Tentative> findByEleveIdOrderByDateSoumissionDesc(Long eleveId);
    List<Tentative> findByQuizIdOrderByDateSoumissionDesc(Long quizId);
    List<Tentative> findByEleveIdAndQuizIdOrderByDateSoumissionDesc(Long eleveId, Long quizId);
    boolean existsByQuizId(Long quizId);
}
