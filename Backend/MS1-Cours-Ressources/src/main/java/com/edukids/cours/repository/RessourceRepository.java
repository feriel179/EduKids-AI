
package com.edukids.cours.repository;

import com.edukids.cours.entity.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RessourceRepository extends JpaRepository<Ressource, UUID> {
}
