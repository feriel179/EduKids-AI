package tn.esprit.nermineprojet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.nermineprojet.Entities.Evenement;

public interface IEvenementRepository extends JpaRepository<Evenement, Long> {
}