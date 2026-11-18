package com.edukids.cours.repository;

import com.edukids.cours.entity.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CoursRepository extends JpaRepository<Cours, UUID> {
}