package tn.edukids.ms4_gamification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.edukids.ms4_gamification.entity.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {}
