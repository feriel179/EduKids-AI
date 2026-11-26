package tn.edukids.ms4_gamification.dto;

import jakarta.validation.constraints.*;

public record ParticipationRequest(@NotNull @Positive Long profilId,
                                   @NotNull @Positive Long defiId,
                                   @Min(0) int score) {}
