
package com.edukids.cours.repository;

import com.edukids.cours.entity.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface NiveauRepository extends JpaRepository<Niveau, UUID> {
}
