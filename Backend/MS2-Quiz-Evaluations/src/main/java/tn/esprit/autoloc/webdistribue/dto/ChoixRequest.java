package tn.esprit.autoloc.webdistribue.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(example = """
        { "texte": "chevaux", "estCorrect": true }
        """)
public record ChoixRequest(
        @Schema(description = "Texte du choix (ou réponse acceptée pour TEXTE_LIBRE)")
        @NotBlank String texte,
        @Schema(description = "true si ce choix est une bonne réponse")
        @NotNull Boolean estCorrect
) {}
