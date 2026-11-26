package tn.esprit.autoloc.webdistribue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.webdistribue.entities.Question;


public interface QuestionRepository extends JpaRepository<Question, Long> {
}
