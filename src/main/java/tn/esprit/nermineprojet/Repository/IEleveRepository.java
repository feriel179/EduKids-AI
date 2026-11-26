package tn.esprit.nermineprojet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.nermineprojet.Entities.Eleve;

public interface IEleveRepository extends JpaRepository<Eleve, Long> {
}