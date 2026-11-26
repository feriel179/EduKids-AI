package tn.esprit.autoloc.webdistribue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.webdistribue.entities.Choix;


public interface ChoixRepository extends JpaRepository<Choix, Long> {
}
