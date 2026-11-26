package tn.edukids.ms4_gamification.dto;

import jakarta.validation.constraints.*;
import java.util.Set;

public record ProfilRequest(@NotNull @Positive Long eleveId, Set<@Positive Long> badgeIds) {}
