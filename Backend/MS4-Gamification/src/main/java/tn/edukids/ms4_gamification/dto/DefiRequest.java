package tn.edukids.ms4_gamification.dto;

import jakarta.validation.constraints.*;
import tn.edukids.ms4_gamification.entity.StatutDefi;
import tn.edukids.ms4_gamification.entity.TypeDefi;
import java.time.LocalDate;

public record DefiRequest(
        @NotBlank @Size(max = 150) String titre,
        @Size(max = 1000) String description,
        @NotNull TypeDefi type,
        @Min(0) int pointsRecompense,
        @Min(1) @Max(6) int niveauScolaire,
        @NotNull LocalDate dateDebut,
        @NotNull LocalDate dateFin,
        @NotNull StatutDefi statut) {}
