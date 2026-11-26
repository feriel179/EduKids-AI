package tn.edukids.ms4_gamification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.edukids.ms4_gamification.entity.ParticipationDefi;

public interface ParticipationRepository extends JpaRepository<ParticipationDefi, Long> {
    boolean existsByProfilId(Long profilId);
    boolean existsByDefiId(Long defiId);
}
