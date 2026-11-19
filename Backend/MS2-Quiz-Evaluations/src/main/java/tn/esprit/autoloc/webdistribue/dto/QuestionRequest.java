package tn.esprit.autoloc.webdistribue.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import tn.esprit.autoloc.webdistribue.entities.TypeQuestion;

import java.util.List;

@Schema(example = """
        {
          "enonce": "Quel est le pluriel de « journal » ?",
          "type": "QCM_UNIQUE",
          "points": 5,
          "ordre": 1,
          "choix": [
            { "texte": "journals", "estCorrect": false },
            { "texte": "journaux", "estCorrect": true },
            { "texte": "journaus", "estCorrect": false }
          ]
        }
        """)
public record QuestionRequest(
        @NotBlank String enonce,
        @Schema(description = "QCM_UNIQUE (1 bon choix, ≥2 choix), QCM_MULTIPLE (≥1 bon choix, ≥2 choix), "
                + "VRAI_FAUX (exactement 2 choix dont 1 bon), TEXTE_LIBRE (choix corrects = réponses acceptées)")
        @NotNull TypeQuestion type,
        @NotNull @Positive Float points,
        @Schema(description = "Position dans le quiz (optionnel : ajoutée à la fin si absent)")
        @PositiveOrZero Integer ordre,
        @Schema(description = "Si fourni en modification, remplace tous les choix existants")
        @Valid List<ChoixRequest> choix
) {}
