package tn.edukids.ms4_gamification.dto;

import tn.edukids.ms4_gamification.entity.StatutDefi;
import tn.edukids.ms4_gamification.entity.TypeDefi;
import java.time.LocalDate;

public record DefiResponse(Long id, String titre, String description, TypeDefi type,
                           int pointsRecompense, int niveauScolaire, LocalDate dateDebut,
                           LocalDate dateFin, StatutDefi statut, boolean actif) {}
