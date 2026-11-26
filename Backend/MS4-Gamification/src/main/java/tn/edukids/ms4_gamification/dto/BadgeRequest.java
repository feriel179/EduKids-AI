package tn.edukids.ms4_gamification.dto;

import jakarta.validation.constraints.*;

public record BadgeRequest(
        @NotBlank @Size(max = 100) String nom,
        @Size(max = 500) String description,
        @Size(max = 255) String icone,
        @Min(0) int pointsRequis) {}
