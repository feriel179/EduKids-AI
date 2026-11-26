package tn.edukids.ms4_gamification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.edukids.ms4_gamification.entity.ProfilGamification;
import java.util.List;

public interface ProfilRepository extends JpaRepository<ProfilGamification, Long> {
    boolean existsByEleveId(Long eleveId);
    boolean existsByEleveIdAndIdNot(Long eleveId, Long id);
    List<ProfilGamification> findDistinctByBadges_Id(Long badgeId);
}
