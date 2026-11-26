package tn.esprit.nermineprojet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.nermineprojet.Entities.Participation;

public interface IParticipationRepository
        extends JpaRepository<Participation, Long> {
}