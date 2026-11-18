
package com.edukids.cours.repository;

import com.edukids.cours.entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MatiereRepository extends JpaRepository<Matiere, UUID> {
}
